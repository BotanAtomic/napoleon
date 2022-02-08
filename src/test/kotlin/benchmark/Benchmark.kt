package benchmark

import io.deepn.script.DeepScriptEnvironment
import kotlin.math.pow
import kotlin.system.measureTimeMillis

fun main() {
    `simple loop`()
}

fun `simple loop`() {
    DeepScriptEnvironment(
        """
            j = 4
            for i=0, 1000000 ,1 do
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
        val result = it.execute()
        println("DeepScript time: ${result.time} ms")
    }


    val time = measureTimeMillis {
        var j : Any = 4
        for (i in 0 until(1000000) + 1) {
            j = i
            j *= 8
            j -= 4
            j += 999
            j /= 1000
            j = j.toDouble().pow(2.0)
            j = "str$j"
        }
    }

    println("Native time: $time ms")

}

