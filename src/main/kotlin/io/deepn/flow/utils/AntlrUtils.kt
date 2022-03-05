package io.deepn.flow.utils

import org.antlr.v4.runtime.Recognizer


fun Recognizer<*, *>.toText(token: Int): String {
    return vocabulary.getLiteralName(token)
        ?: vocabulary.getSymbolicName(token)
        ?: vocabulary.getDisplayName(token)
}
