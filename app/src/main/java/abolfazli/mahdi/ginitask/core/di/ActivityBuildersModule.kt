package abolfazli.mahdi.ginitask.core.di

import abolfazli.mahdi.ginitask.home.ui.DetailsActivity
import abolfazli.mahdi.ginitask.home.ui.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector()
    abstract fun contributeHomeActivity(): HomeActivity

    @ContributesAndroidInjector()
    abstract fun contributeDetailsActivity(): DetailsActivity
}