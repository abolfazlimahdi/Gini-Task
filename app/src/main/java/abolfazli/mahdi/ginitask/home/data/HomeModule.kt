package abolfazli.mahdi.ginitask.home.data

import abolfazli.mahdi.ginitask.core.db.GiniDatabase
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class HomeModule {

    @Singleton
    @Provides
    fun provideHomeDao(giniDb: GiniDatabase): HomeDao {
        return giniDb.homeDao()
    }
}