package esgi.napoleon.exchange.api.entity

data class ExchangeCredentials(
    val key: String,
    val secret: String,
    val passphrase: String? = null
)

