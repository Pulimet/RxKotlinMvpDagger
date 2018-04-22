package net.alexandroid.utils.rxkotlinmvpdagger.dagger

import dagger.Component
import net.alexandroid.utils.rxkotlinmvpdagger.ui.main.MainActivity
import net.alexandroid.utils.rxkotlinmvpdagger.MyApplication

@MyApplicationScope
@Component(modules = [PicassoModule::class])
interface MyApplicationComponent {
    fun inject(myApplication: MyApplication)

    fun inject(mainActivity: MainActivity)
}