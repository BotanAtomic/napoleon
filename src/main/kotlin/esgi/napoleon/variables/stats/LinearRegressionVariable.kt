package esgi.napoleon.variables.stats

import esgi.napoleon.variables.Variable
import org.apache.commons.math3.stat.regression.SimpleRegression

class LinearRegressionVariable(regression: SimpleRegression) : Variable<SimpleRegression>(regression) {

    override fun type() = "linear_regression"

    override fun valueToString(): String {
        return "(slope = ${value.slope},intercept = ${value.intercept})"
    }

}
