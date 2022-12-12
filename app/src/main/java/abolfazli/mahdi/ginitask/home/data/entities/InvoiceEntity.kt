package abolfazli.mahdi.ginitask.home.data.entities

import abolfazli.mahdi.ginitask.home.ui.entities.Invoice
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class InvoiceEntity(
    @PrimaryKey
    val id: Long,
    val amount: String,
    val bankAccount: String,
    val bic: String
) {
    fun toInvoice() = Invoice(
        id = id,
        amount = amount,
        bankAccount = bankAccount,
        bic = bic
    )
}