package ayds.winchester.songinfo.moredetails.data.repository.external

import ayds.lastfmservice.Artist
import ayds.lastfmservice.ArtistService
import ayds.lastfmservice.URL_IMAGE_LASTFM
import ayds.winchester.songinfo.moredetails.domain.entities.Card
import ayds.winchester.songinfo.moredetails.domain.entities.Source

internal class LastFMProxy(private val lastFMService: ArtistService) : ProxyInterface{
    override fun getCard(artistName: String): Card {
        val lastFM = lastFMService.getArtist(artistName)
        return adaptLastFMProxyToCard(lastFM)
    }


    private fun adaptLastFMProxyToCard(lastFM: Artist.LastFMArtist?): Card {
        return if (lastFM != null) {
            Card(
                description = lastFM.name,
                infoURL = lastFM.url,
                source = Source.LastFM,
                sourceLogoURL = lastFM.urlImageLastFM
            )
        }
        else{
            notFoundCard()
        }
    }

    private fun notFoundCard(): Card {
        return Card(
            "",
            "",
            Source.Null,
            URL_IMAGE_LASTFM,
        )
    }

}