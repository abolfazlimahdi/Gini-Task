package abolfazli.mahdi.ginitask.home.ui.entities

import abolfazli.mahdi.ginitask.home.data.entities.InvoiceEntity

data class Invoice(
    val id: Long,
    val amount: String,
    val bankAccount: String,
    val bic: String
) {
    fun toInvoiceEntity() = InvoiceEntity(
        id = id,
        amount = amount,
        bankAccount = bankAccount,
        bic = bic
    )
}