package ayds.winchester.songinfo.moredetails.data.repository.external

import ayds.winchester.artistinfo.external.BASE_WIKI_URL
import ayds.winchester.artistinfo.external.DEFAULT_IMAGE
import ayds.winchester.artistinfo.external.WikipediaArtistInfo
import ayds.winchester.artistinfo.external.WikipediaService
import ayds.winchester.songinfo.moredetails.domain.entities.Card

internal class WikipediaProxy(private val wikipediaService: WikipediaService) : ProxyInterface{
    override fun getCard(artistName: String): Card {
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

    private fun notFoundCard(): Card {
        return Card(
            "",
            BASE_WIKI_URL,
            "",
            DEFAULT_IMAGE,
        )
    }

}