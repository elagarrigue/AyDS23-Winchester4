package ayds.winchester.songinfo.moredetails.dependencyinjector

import android.content.Context
import ayds.winchester.songinfo.moredetails.data.external.WikipediaService
import ayds.winchester.songinfo.moredetails.data.local.ArtistLocalStorage
import ayds.winchester.songinfo.moredetails.data.local.sqldb.ArtistLocalStorageImpl
import ayds.winchester.songinfo.moredetails.data.local.sqldb.CursorToArtistMapperImpl
import ayds.winchester.songinfo.moredetails.data.repository.ArtistRepositoryImpl
import ayds.winchester.songinfo.moredetails.domain.repository.ArtistRepository
import ayds.winchester.songinfo.moredetails.presentation.presenter.MoreDetailsPresenter
import ayds.winchester.songinfo.moredetails.presentation.presenter.MoreDetailsPresenterImpl
import ayds.winchester.songinfo.moredetails.presentation.view.MoreDetailsUiState
import ayds.winchester.songinfo.moredetails.presentation.view.MoreDetailsView

object MoreDetailsPresenterInjector {


    private lateinit var  moreDetailsPresenter: MoreDetailsPresenter

    fun getMoreDetailsPresenter(): MoreDetailsPresenter = moreDetailsPresenter

    fun onViewStarted(moreDetailsView: MoreDetailsView) {

        val artistLocalStorage: ArtistLocalStorage = ArtistLocalStorageImpl(
            moreDetailsView as Context, CursorToArtistMapperImpl()
        )
        val wikipediaService: WikipediaService = WikipediaInjector.wikipediaService

        val repository: ArtistRepository =
            ArtistRepositoryImpl(artistLocalStorage, wikipediaService)

        moreDetailsPresenter = MoreDetailsPresenterImpl(repository,MoreDetailsUiState()
        ).apply {
            initObservers()
        }
    }
}