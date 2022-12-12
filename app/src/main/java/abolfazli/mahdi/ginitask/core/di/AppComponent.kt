package abolfazli.mahdi.ginitask.core.di

import abolfazli.mahdi.ginitask.GiniApplication
import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuildersModule::class,
        ViewModelFactoryModule::class,
        AppModule::class
    ]
)
interface AppComponent : AndroidInjector<GiniApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: GiniApplication): AppComponent
    }
}