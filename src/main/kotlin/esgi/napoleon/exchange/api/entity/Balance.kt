package esgi.napoleon.exchange.api.entity

import org.knowm.xchange.dto.account.Balance as XchangeBalance

import java.math.BigDecimal


data class Balance(
    val currency: Currency,
    val total: BigDecimal,
    val available: BigDecimal,
    val frozen: BigDecimal,
    val loaned: BigDecimal,
    val borrowed: BigDecimal,
    val withdrawing: BigDecimal,
    val depositing: BigDecimal
)


typealias WalletBalances = Map<Currency, Balance>

fun XchangeBalance.toBalance(): Balance = Balance(
    currency.symbol,
    total,
    available,
    frozen,
    loaned,
    borrowed,
    withdrawing,
    depositing
)
