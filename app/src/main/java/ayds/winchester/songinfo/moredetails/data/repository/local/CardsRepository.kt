package ayds.winchester.songinfo.moredetails.data.repository.local

import ayds.winchester.songinfo.moredetails.domain.entities.Card

interface CardsRepository {
    fun getCards(artist: String): List<Card>
    fun saveCards(card: Collection<Card>, artistName: String)
}