package abolfazli.mahdi.ginitask.home.data

import abolfazli.mahdi.ginitask.core.db.GiniDatabase
import abolfazli.mahdi.ginitask.home.data.entities.InvoiceEntity
import androidx.room.withTransaction
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeLocalDataSource @Inject constructor(
    private val homeDao: HomeDao,
    private val giniDatabase: GiniDatabase
) {

    suspend fun insertInvoice(invoice: InvoiceEntity) {
        giniDatabase.withTransaction {
            homeDao.insertInvoice(invoice)
        }
    }

    fun getInvoices() = homeDao.getInvoices()

    fun getInvoiceById(id: Long) = homeDao.getInvoiceById(id)
}