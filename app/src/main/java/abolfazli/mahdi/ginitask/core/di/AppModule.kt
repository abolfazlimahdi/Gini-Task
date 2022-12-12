package abolfazli.mahdi.ginitask.core.di

import abolfazli.mahdi.ginitask.GiniApplication
import abolfazli.mahdi.ginitask.core.db.GiniDatabase
import abolfazli.mahdi.ginitask.home.data.HomeModule
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [
        ViewModelModule::class,
        HomeModule::class
    ]
)
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: GiniApplication): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideDb(context: Context): GiniDatabase {
        return Room
            .databaseBuilder(context, GiniDatabase::class.java, "gini.db")
            .fallbackToDestructiveMigration()
            .build()
    }

}
