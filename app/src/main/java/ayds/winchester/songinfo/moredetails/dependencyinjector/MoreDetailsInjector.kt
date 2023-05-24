package ayds.winchester.songinfo.moredetails.dependencyinjector

import android.content.Context
import ayds.winchester.artistinfo.external.WikipediaService
import ayds.winchester.artistinfo.external.WikipediaInjector
import ayds.winchester.songinfo.moredetails.data.repository.local.ArtistLocalStorage
import ayds.winchester.songinfo.moredetails.data.repository.local.sqldb.ArtistLocalStorageImpl
import ayds.winchester.songinfo.moredetails.data.repository.local.sqldb.CursorToArtistMapperImpl
import ayds.winchester.songinfo.moredetails.data.repository.ArtistRepositoryImpl
import ayds.winchester.songinfo.moredetails.domain.repository.ArtistRepository
import ayds.winchester.songinfo.moredetails.presentation.presenter.MoreDetailsPresenter
import ayds.winchester.songinfo.moredetails.presentation.presenter.MoreDetailsPresenterImpl
import ayds.winchester.songinfo.moredetails.presentation.presenter.ArtistDescriptionHelper
import ayds.winchester.songinfo.moredetails.presentation.presenter.ArtistDescriptionHelperImpl
import ayds.winchester.songinfo.moredetails.presentation.view.MoreDetailsView

object MoreDetailsInjector {



    private val artistDescriptionHelper: ArtistDescriptionHelper = ArtistDescriptionHelperImpl()

    private lateinit var  moreDetailsPresenter: MoreDetailsPresenter

    fun getMoreDetailsPresenter(): MoreDetailsPresenter = moreDetailsPresenter

    fun initOnViewStarted(moreDetailsView: MoreDetailsView) {

        val artistLocalStorage: ArtistLocalStorage = ArtistLocalStorageImpl(
            moreDetailsView as Context, CursorToArtistMapperImpl()
        )
        val wikipediaService: WikipediaService = WikipediaInjector.getWikipediaService()

        val repository: ArtistRepository =
            ArtistRepositoryImpl(artistLocalStorage, wikipediaService)

        moreDetailsPresenter = MoreDetailsPresenterImpl(repository,artistDescriptionHelper)
    }


}