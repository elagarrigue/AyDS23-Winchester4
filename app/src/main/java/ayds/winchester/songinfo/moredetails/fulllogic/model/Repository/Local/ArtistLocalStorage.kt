package ayds.winchester.songinfo.moredetails.fulllogic.model.Repository.Local

import ayds.winchester.songinfo.moredetails.fulllogic.model.entities.Artist.ArtistInfo

interface ArtistLocalStorage {
    fun getArtistInfoFromDataBase( artist: String): ArtistInfo?
    fun saveArtist( artist: ArtistInfo)
    fun getArtistById(id: String): ArtistInfo?
}