package ayds.winchester.songinfo.moredetails.dependencyinjector

import android.content.Context
import ayds.lastfmservice.ArtistService
import ayds.lastfmservice.LastFMInjector
import ayds.newYork4.artist.external.NYTimesArtistService
import ayds.newYork4.artist.external.artists.NYTimesArtistInjector
import ayds.winchester.artistinfo.external.WikipediaService
import ayds.winchester.artistinfo.external.WikipediaInjector
import ayds.winchester.songinfo.moredetails.data.repository.local.ArtistLocalStorage
import ayds.winchester.songinfo.moredetails.data.repository.local.sqldb.ArtistLocalStorageImpl
import ayds.winchester.songinfo.moredetails.data.repository.local.sqldb.CursorToArtistMapperImpl
import ayds.winchester.songinfo.moredetails.data.repository.ArtistRepositoryImpl
import ayds.winchester.songinfo.moredetails.data.repository.external.*
import ayds.winchester.songinfo.moredetails.data.repository.external.BrokerImpl
import ayds.winchester.songinfo.moredetails.data.repository.external.LastFMProxy
import ayds.winchester.songinfo.moredetails.data.repository.external.WikipediaProxy
import ayds.winchester.songinfo.moredetails.domain.repository.ArtistRepository
import ayds.winchester.songinfo.moredetails.presentation.presenter.MoreDetailsPresenter
import ayds.winchester.songinfo.moredetails.presentation.presenter.MoreDetailsPresenterImpl
import ayds.winchester.songinfo.moredetails.presentation.presenter.ArtistDescriptionHelper
import ayds.winchester.songinfo.moredetails.presentation.presenter.ArtistDescriptionHelperImpl
import ayds.winchester.songinfo.moredetails.presentation.view.MoreDetailsView

object MoreDetailsInjector {

    private lateinit var wikipediaService: WikipediaService
    private lateinit var lastFMService: ArtistService
    private lateinit var newYorkTimeService: NYTimesArtistService
    private lateinit var proxyWikipedia: ProxyInterface
    private lateinit var proxyLastFM: ProxyInterface
    private lateinit var proxyNewYorkTime: ProxyInterface
    private lateinit var broker: Broker

    private fun initExternalServices() {
        wikipediaService = WikipediaInjector.getWikipediaService()
        lastFMService = LastFMInjector.getService()
        newYorkTimeService = NYTimesArtistInjector.nyTimesArtistService
    }
    private fun initBroker(){
        proxyWikipedia = WikipediaProxy(wikipediaService)
        proxyLastFM = LastFMProxy(lastFMService)
        proxyNewYorkTime = NewYorkTimeProxy(newYorkTimeService)
        broker = BrokerImpl(listOf(proxyWikipedia, proxyLastFM,proxyNewYorkTime))
    }

    private val artistDescriptionHelper: ArtistDescriptionHelper = ArtistDescriptionHelperImpl()

    private lateinit var  moreDetailsPresenter: MoreDetailsPresenter

    fun getMoreDetailsPresenter(): MoreDetailsPresenter = moreDetailsPresenter

    fun initOnViewStarted(moreDetailsView: MoreDetailsView) {

        initExternalServices()
        initBroker()

        val artistLocalStorage: ArtistLocalStorage = ArtistLocalStorageImpl(
            moreDetailsView as Context, CursorToArtistMapperImpl()
        )
        val repository: ArtistRepository =
            ArtistRepositoryImpl(artistLocalStorage, broker)

        moreDetailsPresenter = MoreDetailsPresenterImpl(repository,artistDescriptionHelper)

    }


}