package server

import NumberTest
import com.sun.net.httpserver.HttpServer
import io.deepn.script.DefaultExecutionEnvironment
import io.deepn.script.logger.Logger
import io.deepn.script.scope.impl.BufferedScope
import io.deepn.script.stdlib.Json.stringify
import io.deepn.script.variables.function.LibraryVariable
import io.deepn.script.variables.function.NativeFunctionVariable
import io.deepn.script.variables.primitive.ObjectVariable
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
                it.responseHeaders.add("Access-Control-Allow-Methods", "GET, OPTIONS")
                it.responseHeaders.add("Access-Control-Allow-Headers", "Content-Type,Authorization")
                it.sendResponseHeaders(204, -1)
                return@createContext
            }
            val scope = BufferedScope()
            val interpreter = DefaultExecutionEnvironment(
                String(it.requestBody.readAllBytes()),
                scope,
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
                    println(grammarError)
                    resultBuilder.append("\tline ${grammarError.start?.line}:${grammarError.start?.charPositionInLine} -> ${grammarError.type}")

                    if (grammarError.expectedTokens != null)
                        resultBuilder.append(", expecting ${grammarError.expectedTokens?.joinToString()}")
                    resultBuilder.append("\n")
                }
            } else {
                val executionResult = interpreter.execute()

                resultBuilder.append("\n").append(IntRange(0, 20).joinToString(" ") { "#" }).append("\n")
                resultBuilder.append(IntRange(0, 5).joinToString(" ") { "" })
                resultBuilder.append("Execution [${executionResult.time} ms] : ${if (executionResult.success) "success" else "failed"}")
                    .append("\n")
                if (executionResult.success.not()) {
                    resultBuilder.append(IntRange(0, 5).joinToString(" ") { "" })
                        .append(executionResult.error?.type).append(" -> ").append(executionResult.error?.message)
                        .append(":")
                        .append("\n")

                    executionResult.error?.lines?.forEach { line ->
                        resultBuilder.append("\t")
                            .append("line ${line.start.line}:${line.start.charPositionInLine} -> ${line.highlight}")
                            .append("\n")
                    }
                }
                resultBuilder.append(IntRange(0, 5).joinToString(" ") { "" })
                resultBuilder.append("Result : ").append(executionResult.result?.valueToString() ?: "none").append("\n")
                resultBuilder.append(IntRange(0, 20).joinToString(" ") { "#" }).append("\n\n")


                resultBuilder.append("\n").append(IntRange(0, 20).joinToString(" ") { "#" }).append("\n")

                scope.getVariables(1).forEach { (key, value) ->
                    if (value !is LibraryVariable && value !is NativeFunctionVariable) {
                        resultBuilder.append("\t")
                        if (value is ObjectVariable)
                            resultBuilder.append("$key -> ${value.stringify().value} (${value.type()})").append("\n")
                        else
                            resultBuilder.append("$key -> ${value.valueToString()} (${value.type()})").append("\n")
                    }
                }
                resultBuilder.append(IntRange(0, 20).joinToString(" ") { "#" }).append("\n")
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


    server.bind(InetSocketAddress("0.0.0.0",4999), 1)

    println("http://localhost:4999/")

    server.start()
}
