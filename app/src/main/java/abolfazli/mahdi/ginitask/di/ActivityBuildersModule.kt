package abolfazli.mahdi.ginitask.di

import abolfazli.mahdi.ginitask.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
        modules = [
            ViewModelModule::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity
}