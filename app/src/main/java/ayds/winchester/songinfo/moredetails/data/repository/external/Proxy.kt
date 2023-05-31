package ayds.winchester.songinfo.moredetails.data.repository.external

import ayds.lastfmservice.Artist.LastFMArtist
import ayds.winchester.songinfo.moredetails.domain.entities.Card
import ayds.winchester.songinfo.moredetails.domain.entities.Source
import ayds.winchester.artistinfo.external.WikipediaArtistInfo
import ayds.winchester.artistinfo.external.WikipediaService
import ayds.lastfmservice.ArtistService
import ayds.lastfmservice.URL_IMAGE_LASTFM
import ayds.newYork4.artist.external.NYTimesArtistService
import ayds.newYork4.artist.external.entities.Artist.NYTimesArtist
import ayds.newYork4.artist.external.entities.NY_TIMES_LOGO_URL
import ayds.winchester.artistinfo.external.BASE_WIKI_URL
import ayds.winchester.artistinfo.external.DEFAULT_IMAGE

interface ProxyInterface {
    fun getCard(artistName: String): Card?
}

internal class WikipediaProxy(private val wikipediaService: WikipediaService) : ProxyInterface{
    override fun getCard(artistName: String): Card? {
        val wikipedia = wikipediaService.getArtist(artistName)
        return adaptWikipediaToCard(wikipedia)
    }


    private fun adaptWikipediaToCard(wikipedia: WikipediaArtistInfo?): Card {
        return if (wikipedia != null) {
                    Card(
                        description = wikipedia.artistInfo,
                        infoURL = wikipedia.wikipediaUrl,
                        source = "Wikipedia",
                        sourceLogoURL = wikipedia.logoUrl
                    )
                }
                else{
                    notFoundCard()
                }
    }

    private fun notFoundCard():Card {
        return Card(
            "",
            BASE_WIKI_URL,
            "",
            DEFAULT_IMAGE,
        )
    }

}

internal class LastFMProxy(private val lastFMService: ArtistService) : ProxyInterface{
    override fun getCard(artistName: String): Card {
        val lastFM = lastFMService.getArtist(artistName)
        return adaptLastFMProxyToCard(lastFM)
    }


    private fun adaptLastFMProxyToCard(lastFM: LastFMArtist?): Card {
        return if (lastFM != null) {
                    Card(
                        description = lastFM.name,
                        infoURL = lastFM.url,
                        source = "LastFM",
                        sourceLogoURL = lastFM.urlImageLastFM
                    )
                }
                else{
                notFoundCard()
                }
    }

    private fun notFoundCard():Card {
        return Card(
            "",
            "",
            "",
            URL_IMAGE_LASTFM,
        )
    }

}

internal class NewYorkTimeProxy(private val nYTimesArtist: NYTimesArtistService) : ProxyInterface{
    override fun getCard(artistName: String): Card? {
        val newYorkTime = nYTimesArtist.getArtist(artistName)
        return adaptNewYorkTimeToCard(newYorkTime)
    }

    private fun adaptNewYorkTimeToCard(newYorkTime: NYTimesArtist?): Card? {
        return if (newYorkTime != null) {
                    newYorkTime.url?.let {
                        Card(
                            description = newYorkTime.info,
                            infoURL = it,
                            source = "NewYorkTimes",
                            sourceLogoURL = NY_TIMES_LOGO_URL
                        )
                    }
                }
                else{
                    notFoundCard()
                }
    }

    private fun notFoundCard():Card {
        return Card(
            "",
            "",
            "",
            NY_TIMES_LOGO_URL,
        )
    }

}