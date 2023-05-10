package ayds.winchester.songinfo.moredetails.data.repository

import ayds.winchester.songinfo.moredetails.data.external.WikipediaService
import ayds.winchester.songinfo.moredetails.data.local.ArtistLocalStorage
import ayds.winchester.songinfo.moredetails.domain.entities.Artist
import ayds.winchester.songinfo.moredetails.domain.repository.ArtistRepository


class ArtistRepositoryImpl(
    private val artistLocalStorage: ArtistLocalStorage,
    private val artistWikipediaService: WikipediaService
) : ArtistRepository {

    override fun getArtistByName(artistName: String): Artist {
        var artist = artistLocalStorage.getArtistInfoFromDataBase(artistName)

        when {
            artist != null -> markArtistAsLocal(artist)
            else -> {
                try {
                    artist = artistWikipediaService.getArtist(artistName)
                    if (artist != null) {
                        saveArtist(artist)
                    }
                } catch (e: Exception) {
                    artist = null
                }
            }
        }

        return artist ?: Artist.EmptyArtist
    }

    private fun saveArtist(artist : Artist.ArtistInfo){

        artist.let {
            artistLocalStorage.saveArtist(artist)
        }
    }

    private fun markArtistAsLocal(artist: Artist.ArtistInfo) {
        artist.isLocallyStored = true
    }
}