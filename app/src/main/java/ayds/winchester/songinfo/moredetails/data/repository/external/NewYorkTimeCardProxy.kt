package ayds.winchester.songinfo.moredetails.data.repository.external

import ayds.newYork4.artist.external.NYTimesArtistService
import ayds.newYork4.artist.external.entities.Artist
import ayds.newYork4.artist.external.entities.NY_TIMES_LOGO_URL
import ayds.winchester.songinfo.moredetails.domain.entities.Card
import ayds.winchester.songinfo.moredetails.domain.entities.Source

internal class NewYorkTimeCardProxy(private val nYTimesArtist: NYTimesArtistService) : CardProxy{
    override fun getCard(artistName: String): Card? {
        val newYorkTime = nYTimesArtist.getArtist(artistName)
        return adaptNewYorkTimeToCard(newYorkTime)
    }

    private fun adaptNewYorkTimeToCard(newYorkTime: Artist.NYTimesArtist?): Card? {
        return if (newYorkTime != null) {
            newYorkTime.url?.let {
                Card(
                    description = newYorkTime.info,
                    infoURL = it,
                    source = Source.NewYorkTimes,
                    sourceLogoURL = NY_TIMES_LOGO_URL
                )
            }
        }
        else{
            null
        }
    }

}