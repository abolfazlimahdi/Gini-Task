package abolfazli.mahdi.ginitask.home.data

import abolfazli.mahdi.ginitask.home.data.entities.InvoiceEntity
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInvoice(invoice: InvoiceEntity)

    @Transaction
    @Query("SELECT * FROM InvoiceEntity")
    fun getInvoices(): Flow<List<InvoiceEntity>>

    @Transaction
    @Query("SELECT * FROM InvoiceEntity WHERE id = :id")
    fun getInvoiceById(id: Long): Flow<InvoiceEntity>
}