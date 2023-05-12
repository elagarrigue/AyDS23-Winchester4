package ayds.winchester.songinfo.moredetails.dependencyinjector

import android.content.Context
import ayds.winchester.songinfo.moredetails.data.repository.external.WikipediaService
import ayds.winchester.songinfo.moredetails.data.repository.external.wikipedia.JsonToArtistResolver
import ayds.winchester.songinfo.moredetails.data.repository.external.wikipedia.WikipediaAPI
import ayds.winchester.songinfo.moredetails.data.repository.external.wikipedia.WikipediaServiceImpl
import ayds.winchester.songinfo.moredetails.data.repository.external.wikipedia.WikipediaToArtistResolver
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
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val BASE_URL = "https://en.wikipedia.org/w/"

object MoreDetailsInjector {

    private val wikipediaAPIRetrofit = Retrofit.Builder()
                                               .baseUrl(BASE_URL)
                                               .addConverterFactory(ScalarsConverterFactory.create())
                                               .build()
    private val wikipediaAPI = wikipediaAPIRetrofit.create(WikipediaAPI::class.java)
    private val wikipediaToArtistResolver: WikipediaToArtistResolver = JsonToArtistResolver()

    private val wikipediaService: WikipediaService =  WikipediaServiceImpl(
        wikipediaAPI,
        wikipediaToArtistResolver
    )

    val artistDescriptionHelper: ArtistDescriptionHelper = ArtistDescriptionHelperImpl()

    private lateinit var  moreDetailsPresenter: MoreDetailsPresenter

    fun getMoreDetailsPresenter(): MoreDetailsPresenter = moreDetailsPresenter

    fun initOnViewStarted(moreDetailsView: MoreDetailsView) {

        val artistLocalStorage: ArtistLocalStorage = ArtistLocalStorageImpl(
            moreDetailsView as Context, CursorToArtistMapperImpl()
        )
        val wikipediaService: WikipediaService = wikipediaService

        val repository: ArtistRepository =
            ArtistRepositoryImpl(artistLocalStorage, wikipediaService)

        moreDetailsPresenter = MoreDetailsPresenterImpl(repository)
    }


}