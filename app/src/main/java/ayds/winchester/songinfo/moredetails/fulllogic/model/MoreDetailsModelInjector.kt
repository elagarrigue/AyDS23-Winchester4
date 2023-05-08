package ayds.winchester.songinfo.moredetails.fulllogic.model


import android.content.Context
import ayds.winchester.songinfo.moredetails.fulllogic.model.repository.external.WikipediaService
import ayds.winchester.songinfo.moredetails.fulllogic.model.repository.local.ArtistLocalStorage
import ayds.winchester.songinfo.moredetails.fulllogic.model.repository.local.sqldb.ArtistLocalStorageImpl
import ayds.winchester.songinfo.moredetails.fulllogic.model.repository.local.sqldb.CursorToArtistMapperImpl
import ayds.winchester.songinfo.moredetails.fulllogic.model.repository.external.WikipediaInjector
import ayds.winchester.songinfo.moredetails.fulllogic.view.MoreDetailsView
import ayds.winchester.songinfo.moredetails.fulllogic.model.repository.ArtistRepository
import ayds.winchester.songinfo.moredetails.fulllogic.model.repository.ArtistRepositoryImpl

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