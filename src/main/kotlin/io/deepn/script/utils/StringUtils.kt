package io.deepn.script.utils


fun String.substringBetween(first: String, second: String = first): String {
    return this.substringAfter(first).substringBeforeLast(second)
}

