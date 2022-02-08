package server

import NumberTest
import com.sun.net.httpserver.HttpServer
import io.deepn.script.DeepScriptEnvironment
import io.deepn.script.logger.Logger
import java.io.File
import java.net.InetSocketAddress

fun main() {
    val server = HttpServer.create()

    server.createContext("/") {
        val file = File(NumberTest::class.java.classLoader.getResource("editor/index.html").file)

        val response = file.readBytes()
        it.sendResponseHeaders(200, response.size.toLong())
        it.responseBody.write(response)
        it.close()
    }

    val resultBuilder = StringBuilder()

    server.createContext("/execute") {
        try {
            it.responseHeaders.add("Access-Control-Allow-Origin", "*")

            if (it.requestMethod.equals("OPTIONS")) {
                it.responseHeaders.add("Access-Control-Allow-Methods", "GET, OPTIONS");
                it.responseHeaders.add("Access-Control-Allow-Headers", "Content-Type,Authorization");
                it.sendResponseHeaders(204, -1);
                return@createContext;
            }

            val interpreter = DeepScriptEnvironment(
                String(it.requestBody.readAllBytes()),
                logger = object : Logger {
                    override fun log(message: String) {
                        resultBuilder.append(message)
                    }
                }
            )
            val compilationResult = interpreter.compile()

            resultBuilder.append(IntRange(0, 20).joinToString(" ") { "#" }).append("\n")
            resultBuilder.append(IntRange(0, 5).joinToString(" ") { "" })
            resultBuilder.append("Compilation [${compilationResult.time} ms] : ${if (compilationResult.success) "success" else "failed"}")
                .append("\n")
            resultBuilder.append(IntRange(0, 20).joinToString(" ") { "#" }).append("\n\n")

            if (compilationResult.success.not()) {
                resultBuilder.append("Compilation failed.").append("\n")
                compilationResult.errors.forEach { grammarError ->
                    resultBuilder.append("\tline ${grammarError.start?.line}: ${grammarError.type}").append("\n")
                }
            } else {
                val executionResult = interpreter.execute()

                resultBuilder.append("\n").append(IntRange(0, 20).joinToString(" ") { "#" }).append("\n")
                resultBuilder.append(IntRange(0, 5).joinToString(" ") { "" })
                resultBuilder.append("Execution [${executionResult.time} ms] : ${if (executionResult.success) "success" else "failed"}")
                    .append("\n")
                if (executionResult.success.not()) {
                    resultBuilder.append(IntRange(0, 5).joinToString(" ") { "" })
                        .append(executionResult.error?.type).append(" -> ").append(executionResult.error?.message).append(":")
                        .append("\n")

                    executionResult.error?.lines?.forEach {line ->
                        resultBuilder.append("\t").append("line ${line.start.line}: ${line.highlight}").append("\n")
                    }
                }
                resultBuilder.append(IntRange(0, 5).joinToString(" ") { "" })
                resultBuilder.append("Result : ").append(executionResult.result?.valueToString() ?: "none").append("\n")
                resultBuilder.append(IntRange(0, 20).joinToString(" ") { "#" }).append("\n\n")


            }

            val finalResponse = resultBuilder.toString().toByteArray()
            it.sendResponseHeaders(200, finalResponse.size.toLong())
            it.responseBody.write(finalResponse)
            it.close()
            resultBuilder.clear()
        } catch (e: Exception) {
            e.printStackTrace()
            it.sendResponseHeaders(500, -1)
            it.close()
        }
    }


    server.bind(InetSocketAddress(4999), 1)

    println("http://localhost:4999/")

    server.start()
}
