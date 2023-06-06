package ayds.winchester.songinfo.moredetails.data.repository

import ayds.winchester.songinfo.moredetails.data.repository.local.CardsRepository
import ayds.winchester.songinfo.moredetails.domain.repository.ArtistRepository
import ayds.winchester.songinfo.moredetails.data.repository.local.Broker
import ayds.winchester.songinfo.moredetails.domain.entities.Card

class ArtistCard(
    private val cardsRepository: CardsRepository,
    private val CardBroker: Broker
) : ArtistRepository {

    override fun getCards(artistName: String): List<Card> {
        var artist = cardsRepository.getCards(artistName)
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

    private fun saveCards(card: List<Card>, artistName: String){
            cardsRepository.saveCards(card, artistName)
    }

    private fun markArtistAsLocal(cards: List<Card>) {
        cards.forEach{ card ->
            card.isLocallyStored = true
        }
    }
}