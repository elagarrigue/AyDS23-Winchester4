package ayds.winchester.songinfo.moredetails.domain

import android.content.Context
import ayds.winchester.songinfo.moredetails.domain.repository.ArtistRepository
import ayds.winchester.songinfo.moredetails.domain.repository.ArtistRepositoryImpl
import ayds.winchester.songinfo.moredetails.data.external.WikipediaInjector
import ayds.winchester.songinfo.moredetails.data.external.WikipediaService
import ayds.winchester.songinfo.moredetails.data.local.ArtistLocalStorage
import ayds.winchester.songinfo.moredetails.data.local.sqldb.ArtistLocalStorageImpl
import ayds.winchester.songinfo.moredetails.data.local.sqldb.CursorToArtistMapperImpl
import ayds.winchester.songinfo.moredetails.presentation.view.MoreDetailsView

object MoreDetailsModelInjector {

    private lateinit var moreDetailsModel: MoreDetailsModel

    fun getMoreDetailsModel(): MoreDetailsModel = moreDetailsModel

    fun initMoreDetailsModel(moreDetailsView: MoreDetailsView) {
        val artistLocalStorage: ArtistLocalStorage = ArtistLocalStorageImpl(
            moreDetailsView as Context, CursorToArtistMapperImpl()
        )
        val wikipediaService: WikipediaService = WikipediaInjector.wikipediaService

        val repository: ArtistRepository =
            ArtistRepositoryImpl(artistLocalStorage, wikipediaService)

        moreDetailsModel = MoreDetailsModelImpl(repository)
    }
}