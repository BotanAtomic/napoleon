package benchmark

import io.deepn.flow.DefaultExecutionEnvironment
import kotlin.math.pow
import kotlin.system.measureTimeMillis

fun main() {
    `simple loop`()
}

fun `simple loop`() {
    DefaultExecutionEnvironment(
        """
            static a = 0
            a += 1
            j = 4
            for i=0, 1000 do
                j = i
                j *= 8
                j -= 4
                j += 999
                j /= 1000
                j = j ** 2
                j = "str" + j
            end
        """.trimIndent()
    ).also {
        it.compile()
        val time = measureTimeMillis {
            for (i in 0..100000)
                it.execute()
        }
        println("Flow time: $time ms")
    }


    val time = measureTimeMillis {
        for (o in 0..10000) {
            var j: Any = 4
            for (i in 0 until (1000) + 1) {
                j = i
                j *= 8
                j -= 4
                j += 999
                j /= 1000
                j = j.toDouble().pow(2.0)
                j = "str$j"
            }
        }

    }

    println("Native time: $time ms")

}

