package net.alexandroid.utils.rxkotlinmvpdagger.ui.main

interface MainMvp {
    /**
     * View mandatory methods. Available to Presenter
     * Presenter -> View
     */
    interface RequiredViewOps

    /**
     * Operations offered from Presenter to View
     * View -> Presenter
     */
    interface PresenterOps {
        fun onDestroy()

        fun attachView(view: RequiredViewOps)

        fun onNewSearchQuery(text: String?)
    }

    /**
     * Operations offered from Presenter to Model
     * Model -> Presenter
     */
    interface RequiredPresenterOps

    /**
     * Model operations offered to Presenter
     * Presenter -> Model
     */
    interface ModelOps {
        fun onDestroy()
    }

}