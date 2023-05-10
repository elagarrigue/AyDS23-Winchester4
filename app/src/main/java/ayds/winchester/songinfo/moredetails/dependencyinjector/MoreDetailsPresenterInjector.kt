package ayds.winchester.songinfo.moredetails.dependencyinjector

import ayds.winchester.songinfo.moredetails.presentation.presenter.MoreDetailsPresenterImpl
import ayds.winchester.songinfo.moredetails.presentation.view.MoreDetailsView

object MoreDetailsPresenterInjector {

    fun onViewStarted(moreDetailsView: MoreDetailsView) {
        MoreDetailsPresenterImpl(MoreDetailsModelInjector.getMoreDetailsModel()).apply {
            setMoreDetailsView(moreDetailsView)
        }
    }
}