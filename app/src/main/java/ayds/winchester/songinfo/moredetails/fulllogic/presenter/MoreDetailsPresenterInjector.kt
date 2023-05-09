package ayds.winchester.songinfo.moredetails.fulllogic.presenter

import ayds.winchester.songinfo.moredetails.fulllogic.model.MoreDetailsModelInjector
import ayds.winchester.songinfo.moredetails.fulllogic.view.MoreDetailsView

object MoreDetailsPresenterInjector {

    fun onViewStarted(moreDetailsView: MoreDetailsView) {
        MoreDetailsPresenterImpl(MoreDetailsModelInjector.getMoreDetailsModel()).apply {
            setMoreDetailsView(moreDetailsView)
        }
    }
}