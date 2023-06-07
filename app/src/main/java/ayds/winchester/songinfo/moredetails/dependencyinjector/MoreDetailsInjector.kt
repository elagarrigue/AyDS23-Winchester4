package ayds.winchester.songinfo.moredetails.dependencyinjector

import android.content.Context
import ayds.lastfmservice.ArtistService
import ayds.lastfmservice.LastFMInjector
import ayds.newYork4.artist.external.NYTimesArtistService
import ayds.newYork4.artist.external.artists.NYTimesArtistInjector
import ayds.winchester.artistinfo.external.WikipediaService
import ayds.winchester.artistinfo.external.WikipediaInjector
import ayds.winchester.songinfo.moredetails.data.repository.local.CardsRepository
import ayds.winchester.songinfo.moredetails.data.repository.local.sqldb.ArtistLocalStorageImpl
import ayds.winchester.songinfo.moredetails.data.repository.local.sqldb.CursorToArtistMapperImpl
import ayds.winchester.songinfo.moredetails.data.repository.ArtistCard
import ayds.winchester.songinfo.moredetails.data.repository.external.*
import ayds.winchester.songinfo.moredetails.data.repository.local.BrokerImpl
import ayds.winchester.songinfo.moredetails.data.repository.external.LastFMCardProxy
import ayds.winchester.songinfo.moredetails.data.repository.external.WikipediaCardProxy
import ayds.winchester.songinfo.moredetails.data.repository.local.Broker
import ayds.winchester.songinfo.moredetails.domain.repository.ArtistRepository
import ayds.winchester.songinfo.moredetails.presentation.presenter.MoreDetailsPresenter
import ayds.winchester.songinfo.moredetails.presentation.presenter.MoreDetailsPresenterImpl
import ayds.winchester.songinfo.moredetails.presentation.presenter.CardDescriptionHelper
import ayds.winchester.songinfo.moredetails.presentation.presenter.CardDescriptionHelperImpl
import ayds.winchester.songinfo.moredetails.presentation.view.MoreDetailsView

object MoreDetailsInjector {

    private lateinit var wikipediaService: WikipediaService
    private lateinit var lastFMService: ArtistService
    private lateinit var newYorkTimeService: NYTimesArtistService
    private lateinit var proxyWikipedia: CardProxy
    private lateinit var proxyLastFM: CardProxy
    private lateinit var proxyNewYorkTime: CardProxy
    private lateinit var broker: Broker

    private fun initExternalServices() {
        wikipediaService = WikipediaInjector.getWikipediaService()
        lastFMService = LastFMInjector.getService()
        newYorkTimeService = NYTimesArtistInjector.nyTimesArtistService
    }
    private fun initBroker(){
        proxyWikipedia = WikipediaCardProxy(wikipediaService)
        proxyLastFM = LastFMCardProxy(lastFMService)
        proxyNewYorkTime = NewYorkTimeCardProxy(newYorkTimeService)
        broker = BrokerImpl(listOf(proxyWikipedia, proxyLastFM,proxyNewYorkTime))
    }

    private val cardDescriptionHelper: CardDescriptionHelper = CardDescriptionHelperImpl()

    private lateinit var  moreDetailsPresenter: MoreDetailsPresenter

    fun getMoreDetailsPresenter(): MoreDetailsPresenter = moreDetailsPresenter

    fun initOnViewStarted(moreDetailsView: MoreDetailsView) {

        initExternalServices()
        initBroker()

        val cardsRepository: CardsRepository = ArtistLocalStorageImpl(
            moreDetailsView as Context, CursorToArtistMapperImpl()
        )
        val repository: ArtistRepository =
            ArtistCard(cardsRepository, broker)

        moreDetailsPresenter = MoreDetailsPresenterImpl(repository,cardDescriptionHelper)

    }


}