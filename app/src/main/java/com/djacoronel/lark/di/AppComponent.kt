package com.djacoronel.lark.di

import android.app.Application
import com.djacoronel.lark.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Created by djacoronel on 12/12/17.
 */
@Singleton
@Component(modules = [(AppModule::class), (AndroidInjectionModule::class), (ActivityBuilder::class)])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
    fun inject(app: App)
}