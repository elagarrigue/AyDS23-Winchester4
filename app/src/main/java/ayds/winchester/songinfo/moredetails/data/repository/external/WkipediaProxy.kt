package ayds.winchester.songinfo.moredetails.data.repository.external

import ayds.winchester.artistinfo.external.WikipediaArtistInfo
import ayds.winchester.artistinfo.external.WikipediaService
import ayds.winchester.songinfo.moredetails.domain.entities.Card
import ayds.winchester.songinfo.moredetails.domain.entities.Source

internal class WikipediaCardProxy(private val wikipediaService: WikipediaService) : CardProxy{
    override fun getCard(artistName: String): Card? {
        val wikipedia = wikipediaService.getArtist(artistName)
        return adaptWikipediaToCard(wikipedia)
    }


    private fun adaptWikipediaToCard(wikipedia: WikipediaArtistInfo?): Card? {
        return try {
            if (wikipedia != null) {
                Card(
                    description = wikipedia.artistInfo,
                    infoURL = wikipedia.wikipediaUrl,
                    source = Source.Wikipedia,
                    sourceLogoURL = wikipedia.logoUrl
                )
            }
            else{
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}