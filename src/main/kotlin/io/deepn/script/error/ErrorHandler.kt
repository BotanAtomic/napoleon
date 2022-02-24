package io.deepn.script.error

import io.deepn.script.utils.toText
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
    MISSING_TOKEN,
    EXTRANEOUS_INPUT,
    UNKNOWN
}

data class GrammarSyntaxError(
    val start: ScriptToken?,
    val type: SyntaxErrorEnum,
    val expectedTokens: List<String>? = null
)

data class DeepScriptExecutionError(
    val type: String,
    val message: String,
    val lines: List<ErrorLine>
)

data class ErrorLine(
    val start: ScriptToken,
    val end: ScriptToken,
    val highlight: String,
)

fun Token.toScriptToken() = ScriptToken(
    line, startIndex,
    tokenIndex, type,
    text, charPositionInLine
)

class MissingRecognitionException(
    recognizer: Recognizer<*, *>,
    input: IntStream,
    context: ParserRuleContext
) : RecognitionException(recognizer, input, context)

fun Int.toScriptToken(position: Int) = ScriptToken(this, 0, 0, 0, "", position)

class DeepScriptErrorHandler : BaseErrorListener() {

    val exceptions = ArrayList<GrammarSyntaxError>()

    override fun syntaxError(
        recognizer: Recognizer<*, *>, offendingSymbol: Any?,
        line: Int, charPositionInLine: Int, msg: String, e: RecognitionException?
    ) {
        exceptions.add(
            when {
                e is LexerNoViableAltException || e is NoViableAltException -> GrammarSyntaxError(
                    e.offendingToken?.toScriptToken() ?: line.toScriptToken(charPositionInLine),
                    SyntaxErrorEnum.NO_ALTERNATIVE
                )
                e is InputMismatchException -> GrammarSyntaxError(
                    e.offendingToken?.toScriptToken() ?: line.toScriptToken(charPositionInLine),
                    SyntaxErrorEnum.INPUT_MISMATCH,
                    e.expectedTokens.toArray().map { recognizer.toText(it) }
                )
                e is MissingRecognitionException -> GrammarSyntaxError(
                    e.offendingToken?.toScriptToken() ?: line.toScriptToken(charPositionInLine),
                    SyntaxErrorEnum.MISSING_TOKEN,
                    e.expectedTokens.toArray().map { recognizer.toText(it) }
                )
                offendingSymbol is CommonToken -> GrammarSyntaxError(
                    offendingSymbol.toScriptToken(),
                    SyntaxErrorEnum.EXTRANEOUS_INPUT,
                    e?.expectedTokens?.toArray()?.map {
                        recognizer.vocabulary.getLiteralName(it) ?: recognizer.vocabulary.getSymbolicName(it)
                        ?: recognizer.vocabulary.getDisplayName(it)
                    }
                )
                else -> GrammarSyntaxError(line.toScriptToken(charPositionInLine), SyntaxErrorEnum.UNKNOWN)
            }
        )
    }
}

class DeepScriptErrorStrategyHandler : DefaultErrorStrategy() {

    override fun reportMissingToken(recognizer: Parser) {
        if (inErrorRecoveryMode(recognizer)) return

        beginErrorCondition(recognizer)

        val token = recognizer.currentToken
        val expecting = getExpectedTokens(recognizer)
        val message = "missing " + expecting.toString(recognizer.vocabulary) +
                " at " + getTokenErrorDisplay(token)

        recognizer.notifyErrorListeners(
            token,
            message,
            MissingRecognitionException(recognizer, recognizer.inputStream, recognizer.context)
        )
    }

    override fun reportUnwantedToken(recognizer: Parser) {
        if (inErrorRecoveryMode(recognizer))
            return

        beginErrorCondition(recognizer)
        val token = recognizer.currentToken
        val tokenName = getTokenErrorDisplay(token)
        val expecting = getExpectedTokens(recognizer)
        val message = "extraneous input " + tokenName + " expecting " +
                expecting.toString(recognizer.vocabulary)
        recognizer.notifyErrorListeners(token, message, InputMismatchException(recognizer))
    }

}
