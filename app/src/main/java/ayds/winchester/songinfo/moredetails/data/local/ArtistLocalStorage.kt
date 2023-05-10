package ayds.winchester.songinfo.moredetails.data.local

import ayds.winchester.songinfo.moredetails.domain.entities.Artist.ArtistInfo

interface ArtistLocalStorage {
    fun getArtistInfoFromDataBase( artist: String): ArtistInfo?
    fun saveArtist( artist: ArtistInfo)
}