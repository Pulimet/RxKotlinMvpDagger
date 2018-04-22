package net.alexandroid.utils.rxkotlinmvpdagger

import android.app.Application
import net.alexandroid.utils.rxkotlinmvpdagger.dagger.ContextModule
import net.alexandroid.utils.rxkotlinmvpdagger.dagger.DaggerMyApplicationComponent
import net.alexandroid.utils.rxkotlinmvpdagger.dagger.MyApplicationComponent

class MyApplication : Application() {
    companion object {
        //platformStatic allow access it from java code
        @JvmStatic lateinit var component: MyApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerMyApplicationComponent
                .builder()
                .contextModule(ContextModule(this))
                .build()
        component.inject(this)
    }

}