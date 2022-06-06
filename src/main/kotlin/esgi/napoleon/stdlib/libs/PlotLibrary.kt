package esgi.napoleon.stdlib.libs

import esgi.napoleon.ExecutionEnvironment
import esgi.napoleon.error.ValueError
import esgi.napoleon.stdlib.Environment
import esgi.napoleon.stdlib.FunctionName
import esgi.napoleon.stdlib.Package
import esgi.napoleon.variables.Variable
import esgi.napoleon.variables.Void
import esgi.napoleon.variables.plot.PlotVariable
import esgi.napoleon.variables.primitive.BooleanVariable
import esgi.napoleon.variables.primitive.IntegerVariable
import esgi.napoleon.variables.primitive.ListVariable
import esgi.napoleon.variables.primitive.StringVariable
import esgi.napoleon.variables.primitive.api.NumberVariable
import esgi.napoleon.variables.ta.BarSeriesVariable
import esgi.napoleon.variables.ta.IndicatorVariable
import org.knowm.xchart.OHLCChart
import org.knowm.xchart.PieChart
import org.knowm.xchart.XYChart
import org.knowm.xchart.XYSeries
import org.knowm.xchart.style.Styler.LegendPosition
import org.knowm.xchart.style.markers.SeriesMarkers
import java.awt.Color
import java.util.stream.Collectors

@Package("plt")
object PlotLibrary {

    fun ohlc(
        series: BarSeriesVariable,
        width: IntegerVariable = IntegerVariable(400),
        height: IntegerVariable = IntegerVariable(300),
        @Environment environment: ExecutionEnvironment,
    ): Variable<*> {
        val chart = OHLCChart(400.coerceAtLeast(width.toInt()), 500.coerceAtLeast(height.toInt()))
        println(chart.height)
        val open = mutableListOf<Number>()
        val high = mutableListOf<Number>()
        val low = mutableListOf<Number>()
        val close = mutableListOf<Number>()

        series.value.barData.forEach {
            open.add(it.openPrice.doubleValue())
            high.add(it.highPrice.doubleValue())
            low.add(it.lowPrice.doubleValue())
            close.add(it.closePrice.doubleValue())
        }

        chart.styler.isLegendVisible = false
        chart.addSeries(series.value.name, open, high, low, close)

        environment.getPlotManager().addPlot(chart)

        return Void
    }

    @FunctionName("xy")
    fun xy(
        title: StringVariable,
        width: IntegerVariable = IntegerVariable(400),
        height: IntegerVariable = IntegerVariable(300)
    ): Variable<*> {
        val chart = XYChart(400.coerceAtLeast(width.toInt()), 300.coerceAtLeast(height.toInt()))
        chart.title = title.value
        return PlotVariable(chart)
    }

    @FunctionName("add_xy")
    fun PlotVariable.addXy(x: ListVariable, y: ListVariable, name: StringVariable): Variable<*> {
        if (x.value.count { it !is NumberVariable } > 0) throw ValueError("x must be a list of numbers")
        if (y.value.count { it !is NumberVariable } > 0) throw ValueError("y must be a list of numbers")
        if (y.value.size != x.value.size) throw ValueError("X and Y-Axis sizes are not the same")
        if (this.value !is XYChart) throw ValueError("add_xy function can only be used on XYChart")

        val seriesName = name.value.ifEmpty { "Series ${this.value.seriesMap.count() + 1}" }

        if (value.seriesMap.containsKey(seriesName))
            throw ValueError("Series name already exists")

        this.value.addSeries(seriesName,
            x.value.map { (it as NumberVariable).toDouble() }, y.value.map { (it as NumberVariable).toDouble() })
            .marker = SeriesMarkers.NONE

        return this
    }

    @FunctionName("add_y")
    fun PlotVariable.addY(y: ListVariable, name: StringVariable, type: StringVariable = StringVariable("default")): Variable<*> {
        if (y.value.count { it !is NumberVariable } > 0) throw ValueError("y must be a list of numbers")
        if (this.value !is XYChart) throw ValueError("ad_y function can only be used on XYChart")

        val seriesName = name.value.ifEmpty { "Series ${this.value.seriesMap.count() + 1}" }

        if (value.seriesMap.containsKey(seriesName)) throw ValueError("Series name already exists")

        this.value.addSeries(seriesName, y.value.map { (it as NumberVariable).toDouble() }).apply {
            marker = SeriesMarkers.NONE

            if(type.value == "scatter") {
                marker = SeriesMarkers.CROSS
                markerColor = Color.RED
                xySeriesRenderStyle = XYSeries.XYSeriesRenderStyle.Scatter
            }
        }

        return this
    }

    @FunctionName("add_indicator")
    fun PlotVariable.addIndicator(y: IndicatorVariable, name: StringVariable): Variable<*> {
        if (this.value !is XYChart) throw ValueError("add_indicator function can only be used on XYChart")

        val seriesName = name.value.ifEmpty { "Series ${this.value.seriesMap.count() + 1}" }

        if (value.seriesMap.containsKey(seriesName)) throw ValueError("Series name already exists")

        this.value.addSeries(seriesName, y.value.stream().map { it.doubleValue() }.collect(Collectors.toList()))
            .marker = SeriesMarkers.NONE
        return this
    }

    @FunctionName("legend_position")
    fun PlotVariable.legendPosition(position: StringVariable): Variable<*> {
        val availableLegendPosition = LegendPosition.values().map { it.name }

        if (!availableLegendPosition.contains(position.value))
            throw ValueError("Position must be one of ${availableLegendPosition.joinToString(", ")}")

        this.value.styler.legendPosition = LegendPosition.valueOf(position.value)
        return this
    }

    @FunctionName("enable_x_axis")
    fun PlotVariable.enableXAxis(enabled: BooleanVariable): Variable<*> {
        if (this.value !is XYChart) throw ValueError("enable_x_axis function can only be used on XYChart")
        this.value.styler.isXAxisTicksVisible = enabled.value
        return this
    }

    @FunctionName("enable_y_axis")
    fun PlotVariable.enableYAxis(enabled: BooleanVariable): Variable<*> {
        if (this.value !is XYChart) throw ValueError("enable_y_axis function can only be used on XYChart")
        this.value.styler.isYAxisTicksVisible = enabled.value
        return this
    }

    @FunctionName("legend_visible")
    fun PlotVariable.legendVisible(value: BooleanVariable): Variable<*> {
        this.value.styler.isLegendVisible = value.value
        return this
    }

    fun PlotVariable.title(title: StringVariable): Variable<*> {
        this.value.title = title.value
        return this
    }

    fun PlotVariable.show(@Environment environment: ExecutionEnvironment) {
        environment.getPlotManager().addPlot(this.value)
    }

    @FunctionName("pie")
    fun pie(
        title: StringVariable? = null,
        width: IntegerVariable = IntegerVariable(400),
        height: IntegerVariable = IntegerVariable(300)
    ): Variable<*> {
        val chart = PieChart(400.coerceAtLeast(width.toInt()), 500.coerceAtLeast(height.toInt()))
        chart.title = title?.value ?: "Unnamed Pie Chart"
        return PlotVariable(chart)
    }

    @FunctionName("add_category")
    fun PlotVariable.addCategory(value: NumberVariable, name: StringVariable): Variable<*> {
        if (this.value !is PieChart) throw ValueError("add_category function can only be used on PieChart")

        val seriesName = name.value.ifEmpty { "Series ${this.value.seriesMap.count() + 1}" }

        if (this.value.seriesMap.containsKey(seriesName)) throw ValueError("Series name already exists")

        this.value.addSeries(name.value, value.toDouble())
        return this
    }

}
