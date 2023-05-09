package ayds.winchester.songinfo.moredetails.fulllogic.view

import ayds.winchester.songinfo.moredetails.fulllogic.model.MoreDetailsModelInjector
import ayds.winchester.songinfo.moredetails.fulllogic.presenter.MoreDetailsPresenterInjector

object MoreDetailsViewInjector {

    val artistDescriptionHelper: ArtistDescriptionHelper = ArtistDescriptionHelperImpl()

    fun init(moreDetailsView: MoreDetailsView) {
        MoreDetailsModelInjector.initMoreDetailsModel(moreDetailsView)
        MoreDetailsPresenterInjector.onViewStarted(moreDetailsView)
    }
}