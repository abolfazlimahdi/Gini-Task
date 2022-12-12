package abolfazli.mahdi.ginitask.home.data

import abolfazli.mahdi.ginitask.core.db.GiniDatabase
import abolfazli.mahdi.ginitask.home.ui.entities.Invoice
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(
    private val localDataSource: HomeLocalDataSource
) {

    suspend fun insertInvoice(invoice: Invoice) {
        localDataSource.insertInvoice(invoice.toInvoiceEntity())
    }

    fun getInvoicesLocal() = localDataSource.getInvoices().map { invoices -> invoices.map { invoice -> invoice.toInvoice() } }

    fun getInvoiceByIdLocal(id: Long) = localDataSource.getInvoiceById(id)


}