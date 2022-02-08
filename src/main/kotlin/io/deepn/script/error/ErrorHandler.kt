package io.deepn.script.error

import org.antlr.v4.runtime.*

data class ScriptToken(
    val line: Int,
    val startIndex: Int,
    val index: Int,
    val type: Int,
    val text: String,
    val charPositionInLine: Int
)


enum class SyntaxErrorEnum {
    NO_ALTERNATIVE,
    INPUT_MISMATCH,
    EXTRANEOUS_INPUT,
    UNKNOWN
}

data class GrammarSyntaxError(
    val start: ScriptToken?,
    val type: SyntaxErrorEnum
)

data class DeepScriptExecutionError(
    val type: String,
    val message: String,
    val start: ScriptToken,
    val end: ScriptToken,
    val highlight: String,
)

fun Token.toScriptToken() = ScriptToken(
    line, startIndex,
    tokenIndex, type,
    text, charPositionInLine
)

fun Int.toScriptToken() = ScriptToken(this, 0, 0, 0, "", 0)

class DeepScriptErrorHandler : BaseErrorListener() {

    val exceptions = ArrayList<GrammarSyntaxError>()

    override fun syntaxError(
        recognizer: Recognizer<*, *>, offendingSymbol: Any?,
        line: Int, charPositionInLine: Int, msg: String, e: RecognitionException?
    ) {
        exceptions.add(
            when {
                e is LexerNoViableAltException || e is NoViableAltException -> GrammarSyntaxError(
                    e.offendingToken?.toScriptToken(),
                    SyntaxErrorEnum.NO_ALTERNATIVE
                )
                e is InputMismatchException -> GrammarSyntaxError(
                    e.offendingToken?.toScriptToken(),
                    SyntaxErrorEnum.INPUT_MISMATCH
                )
                offendingSymbol is CommonToken -> GrammarSyntaxError(
                    offendingSymbol.toScriptToken(),
                    SyntaxErrorEnum.EXTRANEOUS_INPUT
                )
                else -> GrammarSyntaxError(line.toScriptToken(), SyntaxErrorEnum.UNKNOWN)
            }
        )
    }
}
