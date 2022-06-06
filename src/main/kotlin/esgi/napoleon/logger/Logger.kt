package esgi.napoleon.logger

val SYSTEM_LOGGER: Logger = object : Logger {
    override fun log(message: String) {
        print(message)
    }
}

interface Logger {

    fun log(message: String)

}

class CachedLogger : Logger {

    private val buffer = StringBuilder()
    override fun log(message: String) {
        buffer.append(message)
    }

    fun reset() {
        buffer.clear()
    }

    override fun toString(): String {
        return buffer.toString()
    }

}

