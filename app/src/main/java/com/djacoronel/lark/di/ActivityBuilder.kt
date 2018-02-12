package com.djacoronel.lark.di

import com.djacoronel.lark.addeditcategory.AddEditCategoryActivity
import com.djacoronel.lark.addeditidea.AddEditIdeaActivity
import com.djacoronel.lark.openidea.OpenIdeaActivity
import com.djacoronel.lark.categories.MainActivity
import com.djacoronel.lark.category.CategoryActivity
import com.djacoronel.lark.di.scope.ActivityScope
import com.djacoronel.lark.util.AlarmReceiver
import com.djacoronel.lark.util.DailyScheduleReceiver
import com.djacoronel.lark.util.DeviceBootReceiver
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by djacoronel on 12/12/17.
 */
@Module
abstract class ActivityBuilder {
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindAddEditCategoryActivity(): AddEditCategoryActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindCategoryActivity(): CategoryActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindOpenIdeaActivity(): OpenIdeaActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindAddEditIdeaActivity(): AddEditIdeaActivity

    @ContributesAndroidInjector
    abstract fun bindAlarmReceiver(): AlarmReceiver

    @ContributesAndroidInjector
    abstract fun bindDeviceBootReceiver(): DeviceBootReceiver

    @ContributesAndroidInjector
    abstract fun bindDailyScheduleReceiver(): DailyScheduleReceiver
}