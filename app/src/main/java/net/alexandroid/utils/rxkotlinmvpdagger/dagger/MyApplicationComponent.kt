package net.alexandroid.utils.rxkotlinmvpdagger.dagger

import dagger.Component
import net.alexandroid.utils.rxkotlinmvpdagger.MyApplication
import net.alexandroid.utils.rxkotlinmvpdagger.dagger.main.MainModule
import net.alexandroid.utils.rxkotlinmvpdagger.ui.main.MainActivity

@MyApplicationScope
@Component(modules = [PicassoModule::class, MainModule::class, PhotosApiModule::class])
interface MyApplicationComponent {
    fun inject(myApplication: MyApplication)

    fun inject(mainActivity: MainActivity)
}