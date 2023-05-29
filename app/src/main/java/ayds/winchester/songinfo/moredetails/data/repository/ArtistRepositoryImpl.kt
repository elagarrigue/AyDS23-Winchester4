package ayds.winchester.songinfo.moredetails.data.repository

import ayds.winchester.artistinfo.external.BASE_WIKI_URL
import ayds.winchester.artistinfo.external.DEFAULT_IMAGE
import ayds.winchester.songinfo.moredetails.data.repository.local.ArtistLocalStorage
import ayds.winchester.songinfo.moredetails.domain.repository.ArtistRepository
import ayds.winchester.artistinfo.external.WikipediaService
import ayds.winchester.artistinfo.external.WikipediaArtistInfo
import ayds.winchester.songinfo.moredetails.domain.entities.Card

class ArtistRepositoryImpl(
    private val artistLocalStorage: ArtistLocalStorage,
    private val artistWikipediaService: WikipediaService
) : ArtistRepository {

    override fun getArtistByName(artistName: String): Collection<Card>? {
        var artist = artistLocalStorage.getArtistInfoFromDataBase(artistName)
        if (artist != null) {
            when {
                !artist.isEmpty() -> markArtistAsLocal(artist)
                else -> {
                    try {
                        val artistWikipedia = artistWikipediaService.getArtist(artistName)
                        val cards : MutableCollection<Card> = mutableListOf()
                        if (artistWikipedia != null) {
                            cards.add(updateCard(artistWikipedia))
                            saveArtist(cards, artistName)
                        }
                        else{
                            cards.add(notFoundCard())
                        }
                        artist = cards
                    } catch (e: Exception) {
                        artist = null
                    }
                }
            }
        }

        return artist
    }

    private fun updateCard(artistInfoExternalService: WikipediaArtistInfo):Card {
        return Card(
            artistInfoExternalService.artistInfo,
            artistInfoExternalService.wikipediaUrl,
            artistInfoExternalService.source,
            artistInfoExternalService.logoUrl,
        )
    }

    private fun notFoundCard():Card {
        return Card(
            "",
            BASE_WIKI_URL,
            "wikipedia",
            DEFAULT_IMAGE,
        )
    }

    private fun saveArtist(card: Collection<Card>, artistName: String){
            artistLocalStorage.saveArtist(card, artistName)
    }

    private fun markArtistAsLocal(cards: Collection<Card>) {
        cards.forEach{ card ->
            card.isLocallyStored = true
        }
    }
}