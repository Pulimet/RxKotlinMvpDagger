package net.alexandroid.utils.rxkotlinmvpdagger.dagger.main

import dagger.Module
import dagger.Provides
import net.alexandroid.utils.rxkotlinmvpdagger.dagger.MyApplicationScope
import net.alexandroid.utils.rxkotlinmvpdagger.ui.main.MainMvp
import net.alexandroid.utils.rxkotlinmvpdagger.ui.main.MainPresenter

@Module
class MainModule {

    @MyApplicationScope
    @Provides
    fun provideMainPresenter() : MainMvp.PresenterOps {
        return MainPresenter()
    }
}