package ayds.winchester.songinfo.moredetails.dependencyinjector

import ayds.winchester.songinfo.moredetails.presentation.view.ArtistDescriptionHelper
import ayds.winchester.songinfo.moredetails.presentation.view.ArtistDescriptionHelperImpl
import ayds.winchester.songinfo.moredetails.presentation.view.MoreDetailsView

object MoreDetailsViewInjector {

    val artistDescriptionHelper: ArtistDescriptionHelper = ArtistDescriptionHelperImpl()

    fun init(moreDetailsView: MoreDetailsView) {
        MoreDetailsModelInjector.initMoreDetailsModel(moreDetailsView)
        MoreDetailsPresenterInjector.onViewStarted(moreDetailsView)
    }
}