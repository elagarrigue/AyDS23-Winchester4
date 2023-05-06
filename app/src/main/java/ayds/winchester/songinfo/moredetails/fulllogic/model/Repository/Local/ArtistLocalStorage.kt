package ayds.winchester.songinfo.moredetails.fulllogic.model.Repository.Local

import ayds.winchester.songinfo.home.model.entities.Song

interface ArtistLocalStorage {
    fun getArtistInfoFromDataBase( artist: String): Artist.ArtistInfo?
    fun saveArtist( artist:Artist.ArtistInfo)
    fun getArtistById(id: String): Artist.ArtistInfo?
}