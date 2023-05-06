package ayds.winchester.songinfo.moredetails.fulllogic.view

import ayds.winchester.songinfo.moredetails.fulllogic.model.MoreDetailsModelInjector

object MoreDetailsViewInjector {

    val artistDescriptionHelper: ArtistDescriptionHelper = ArtistDescriptionHelperImpl()

    fun init(moreDetailsView: MoreDetailsView) {
        MoreDetailsModelInjector.initMoreDetailsModel(moreDetailsView)
        MoreDetailsControllerInjector.onViewStarted(moreDetailsView)
    }
}