package ayds.winchester.songinfo.moredetails.data.repository

import ayds.winchester.songinfo.moredetails.data.repository.local.ArtistLocalStorage
import ayds.winchester.songinfo.moredetails.domain.repository.ArtistRepository
import ayds.winchester.songinfo.moredetails.data.repository.external.Broker
import ayds.winchester.songinfo.moredetails.domain.entities.Card

class ArtistRepositoryImpl(
    private val artistLocalStorage: ArtistLocalStorage,
    private val broker: Broker
) : ArtistRepository {

    override fun getArtistByName(artistName: String): Collection<Card>? {
        var artist = artistLocalStorage.getArtistInfoFromDataBase(artistName)
        if (artist != null) {
            when {
                !artist.isEmpty() -> markArtistAsLocal(artist)
                else -> {
                    artist = try {
                        val cards = broker.getCards(artistName)
                        if (cards.isEmpty()) {
                            saveArtist(cards, artistName)
                        }
                        cards
                    } catch (e: Exception) {
                        null
                    }
                }
            }
        }

        return artist
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