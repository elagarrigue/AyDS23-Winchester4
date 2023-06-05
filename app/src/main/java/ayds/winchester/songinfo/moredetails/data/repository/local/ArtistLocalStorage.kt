package ayds.winchester.songinfo.moredetails.data.repository.local

import ayds.winchester.songinfo.moredetails.domain.entities.Card

interface ArtistLocalStorage {
    fun getArtistInfoFromDataBase( artist: String): List<Card>
    fun saveArtist(card: Collection<Card>, artistName: String)
}