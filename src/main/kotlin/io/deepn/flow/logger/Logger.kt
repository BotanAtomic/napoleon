package io.deepn.flow.logger

val SYSTEM_LOGGER: Logger = object : Logger {
    override fun log(message: String) {
        print(message)
    }
}

interface Logger {

    fun log(message: String)

}

