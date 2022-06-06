package esgi.napoleon.utils


fun String.substringBetween(first: String, second: String = first): String {
    return this.substringAfter(first).substringBeforeLast(second)
}

