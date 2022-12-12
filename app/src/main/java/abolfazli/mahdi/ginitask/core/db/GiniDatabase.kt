package abolfazli.mahdi.ginitask.core.db

import abolfazli.mahdi.ginitask.home.data.HomeDao
import abolfazli.mahdi.ginitask.home.data.entities.InvoiceEntity
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [InvoiceEntity::class], version = 1, exportSchema = false)
abstract class GiniDatabase : RoomDatabase() {

    abstract fun homeDao(): HomeDao
}