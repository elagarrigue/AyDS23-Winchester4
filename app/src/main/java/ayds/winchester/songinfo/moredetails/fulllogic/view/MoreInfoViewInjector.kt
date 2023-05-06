package ayds.winchester.songinfo.moredetails.fulllogic.view

import ayds.winchester.songinfo.home.controller.HomeControllerInjector
import ayds.winchester.songinfo.home.model.HomeModelInjector
import ayds.winchester.songinfo.home.view.HomeView


object MoreInfoViewInjector {

    val ArtistDescriptionHelper: ArtistDescriptionHelper = ArtistDescriptionHelperImpl()

    fun init(homeView: HomeView) {
        MoreInfoInjector.initMoreInfoModel(homeView)
        MoreInfoControllerInjector.onViewStarted(homeView)
    }
}