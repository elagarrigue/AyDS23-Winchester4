package ayds.winchester.songinfo.moredetails.data.repository

import ayds.winchester.songinfo.moredetails.data.repository.local.ArtistLocalStorage
import ayds.winchester.songinfo.moredetails.domain.repository.ArtistRepository
import ayds.winchester.songinfo.moredetails.data.repository.local.Broker
import ayds.winchester.songinfo.moredetails.domain.entities.Card

class ArtistRepositoryImpl(
    private val artistCard: ArtistLocalStorage,
    private val CardBroker: Broker
) : ArtistRepository {

    override fun getCards(artistName: String): List<Card> {
        var artist = artistCard.getArtistInfoFromDataBase(artistName)
        if (artist != null) {
            when {
                !artist.isEmpty() -> markArtistAsLocal(artist)
                else -> {
                    val cards = CardBroker.getCards(artistName)
                    if (cards.isNotEmpty()) {
                        saveCards(cards, artistName)
                    }
                    artist = cards
                }
            }
        }
        return artist
    }

    private fun saveCards(card: Collection<Card>, artistName: String){
            artistCard.saveArtist(card, artistName)
    }

    private fun markArtistAsLocal(cards: Collection<Card>) {
        cards.forEach{ card ->
            card.isLocallyStored = true
        }
    }
}