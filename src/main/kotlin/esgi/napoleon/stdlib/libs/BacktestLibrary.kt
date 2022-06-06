package esgi.napoleon.stdlib.libs

import esgi.napoleon.ExecutionEnvironment
import esgi.napoleon.stdlib.Environment
import esgi.napoleon.stdlib.Package
import esgi.napoleon.variables.date.DateTimeVariable
import esgi.napoleon.variables.function.LocalFunctionVariable
import esgi.napoleon.variables.primitive.*
import esgi.napoleon.variables.ta.BarSeriesVariable
import org.knowm.xchart.XYChart
import org.knowm.xchart.XYSeries
import org.knowm.xchart.style.markers.SeriesMarkers
import org.ta4j.core.*
import org.ta4j.core.analysis.cost.CostModel
import org.ta4j.core.analysis.cost.ZeroCostModel
import org.ta4j.core.criteria.*
import org.ta4j.core.criteria.pnl.*
import org.ta4j.core.num.DecimalNum
import org.ta4j.core.num.Num
import java.awt.Color
import java.util.*

fun BaseTradingRecord.buildChart(series: BarSeries, environment: ExecutionEnvironment) {
    val buyIndexes = positions.map { it.entry.index }
    val sellIndexes = positions.map { it.exit.index }

    val chart = XYChart(400, 300)
    val close = mutableListOf<Number>()
    val buys = mutableListOf<Pair<Int, Number>>()
    val sells = mutableListOf<Pair<Int, Number>>()

    series.barData.forEachIndexed { index, bar ->
        close.add(bar.closePrice.doubleValue())
        if (buyIndexes.contains(index)) {
            buys.add(index + 1 to bar.closePrice.doubleValue())
        } else if (sellIndexes.contains(index)) {
            sells.add(index + 1 to bar.closePrice.doubleValue())
        }
    }

    chart.styler.isLegendVisible = false
    chart.addSeries("Close", close).apply { this.marker = SeriesMarkers.NONE }

    if (sells.isNotEmpty()) {
        val sellSeries = chart.addSeries("Sell", sells.map { it.first }, sells.map { it.second })
        sellSeries.marker = SeriesMarkers.CROSS
        sellSeries.markerColor = Color.RED
        sellSeries.xySeriesRenderStyle = XYSeries.XYSeriesRenderStyle.Scatter
    }

    if (buyIndexes.isNotEmpty()) {
        val buySeries = chart.addSeries("Buy", buys.map { it.first }, buys.map { it.second })
        buySeries.marker = SeriesMarkers.CROSS
        buySeries.markerColor = Color.GREEN
        buySeries.xySeriesRenderStyle = XYSeries.XYSeriesRenderStyle.Scatter
    }

    environment.getPlotManager().addPlot(chart);
}


fun functionToRule(data: BarSeries, function: LocalFunctionVariable): Rule {
    return Rule { index, tradingRecord ->
        function.call(
            LinkedList(
                listOf(
                    "date" to DateTimeVariable(data.barData[index].beginTime.toLocalDateTime()),
                    "index" to IntegerVariable(index),
                )
            )
        ).eq(BooleanVariable(true)).value
    }
}

fun feeCostModel(fee: FloatVariable): CostModel {
    return object : CostModel {
        override fun equals(model: CostModel?) = false

        override fun calculate(position: Position?, finalIndex: Int) = DecimalNum.valueOf(0)

        override fun calculate(position: Position?) = DecimalNum.valueOf(0)

        override fun calculate(price: Num, amount: Num): Num {
            return (price.multipliedBy(amount)).multipliedBy(DecimalNum.valueOf(fee.value))
        }

    }
}

fun Trade.toObject(): ObjectVariable {
    return ObjectVariable(
        hashMapOf(
            "type" to StringVariable(type.name),
            "amount" to FloatVariable(this.amount),
            "cost" to FloatVariable(this.cost),
            "price" to FloatVariable(this.netPrice),
            "index" to IntegerVariable(this.index),
        )
    )
}

fun Num.norm() = FloatVariable(this)

@Package("backtest")
object BacktestLibrary {

    fun run(
        series: BarSeriesVariable,
        entryRule: LocalFunctionVariable,
        exitRule: LocalFunctionVariable,
        fee: FloatVariable,
        @Environment environment: ExecutionEnvironment,
    ): ObjectVariable {
        val seriesManager = BarSeriesManager(series.value, feeCostModel(fee), ZeroCostModel())

        val strategy = BaseStrategy(functionToRule(series.value, entryRule), functionToRule(series.value, exitRule))

        val record = seriesManager.run(strategy) as BaseTradingRecord

        record.buildChart(series.value, environment)


        return ObjectVariable(
            hashMapOf(
                "trades" to ListVariable(record.positions.map { listOf(it.entry, it.exit) }.flatten()
                    .map { it.toObject() }), "stats" to ObjectVariable(
                    hashMapOf(
                        "average_loss" to AverageLossCriterion().calculate(series.value, record).norm(),
                        "average_profit" to AverageProfitCriterion().calculate(series.value, record).norm(),
                        "gross_loss" to GrossLossCriterion().calculate(series.value, record).norm(),
                        "gross_profit" to GrossProfitCriterion().calculate(series.value, record).norm(),
                        "gross_return" to GrossReturnCriterion().calculate(series.value, record).norm(),
                        "net_loss" to NetLossCriterion().calculate(series.value, record).norm(),
                        "net_profit" to NetProfitCriterion().calculate(series.value, record).norm(),
                        "pnl" to ProfitLossCriterion().calculate(series.value, record).norm(),
                        "pnl_percent" to ProfitLossPercentageCriterion().calculate(series.value, record).norm(),
                        "pnl_ratio" to ProfitLossRatioCriterion().calculate(series.value, record).norm(),
                        "avg_return_per_bar" to AverageReturnPerBarCriterion().calculate(series.value, record).norm(),
                        "buy_hold" to EnterAndHoldReturnCriterion().calculate(series.value, record).norm(),
                        "sqn" to SqnCriterion().calculate(series.value, record).norm(),
                        "value_at_risk" to ValueAtRiskCriterion(0.95).calculate(series.value, record).norm(),
                        "max_drawdown" to MaximumDrawdownCriterion().calculate(series.value, record).norm(),
                    )
                )
            )
        )
    }

}
