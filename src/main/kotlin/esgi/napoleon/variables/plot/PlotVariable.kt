package esgi.napoleon.variables.plot

import esgi.napoleon.variables.Variable
import org.knowm.xchart.internal.chartpart.Chart

class PlotVariable(chart: Chart<*,*>) : Variable<Chart<*,*>>(chart) {

    override fun type() = "plot"

    override fun valueToString(): String {
        return value.title
    }
}
