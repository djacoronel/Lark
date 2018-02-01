package com.djacoronel.lark

import android.app.Activity
import android.app.Application
import com.djacoronel.lark.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasBroadcastReceiverInjector
import javax.inject.Inject
import android.content.BroadcastReceiver
import dagger.android.AndroidInjector



/**
 * Created by djacoronel on 12/12/17.
 */
class App : Application(), HasActivityInjector, HasBroadcastReceiverInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var broadcastReceiverInjector: DispatchingAndroidInjector<BroadcastReceiver>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this)

    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return activityDispatchingAndroidInjector
    }

    override fun broadcastReceiverInjector(): AndroidInjector<BroadcastReceiver> {
        return broadcastReceiverInjector
    }
}