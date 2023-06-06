package ayds.winchester.songinfo.moredetails.data.repository.external

import ayds.lastfmservice.Artist
import ayds.lastfmservice.ArtistService
import ayds.winchester.songinfo.moredetails.domain.entities.Card
import ayds.winchester.songinfo.moredetails.domain.entities.Source

internal class LastFMCardProxy(private val lastFMService: ArtistService) : CardProxy{
    override fun getCard(artistName: String): Card? {
        val lastFM = lastFMService.getArtist(artistName)
        return adaptLastFMProxyToCard(lastFM)
    }


    private fun adaptLastFMProxyToCard(lastFM: Artist.LastFMArtist?): Card? {
        return try {
            if (lastFM != null) {
                Card(
                    description = lastFM.name,
                    infoURL = lastFM.url,
                    source = Source.LastFM,
                    sourceLogoURL = lastFM.urlImageLastFM
                )
            } else {
                null
            }
            } catch (e: Exception) {
                null
            }
        }
    }