package ayds.winchester.songinfo.moredetails.fulllogic.model.repository

import ayds.winchester.songinfo.moredetails.fulllogic.model.entities.Artist
import ayds.winchester.songinfo.moredetails.fulllogic.model.entities.Artist.ArtistInfo
import ayds.winchester.songinfo.moredetails.fulllogic.model.entities.Artist.EmptyArtist
import ayds.winchester.songinfo.moredetails.fulllogic.model.repository.external.WikipediaService
import ayds.winchester.songinfo.moredetails.fulllogic.model.repository.local.ArtistLocalStorage

interface ArtistRepository {
    fun getArtistByName(artistName: String): Artist
}

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

        return artist ?: EmptyArtist
    }

    private fun saveArtist(artist : ArtistInfo){

        artist.let {
            artistLocalStorage.saveArtist(artist)
        }
    }

    private fun markArtistAsLocal(artist: ArtistInfo) {
        artist.isLocallyStored = true
    }
}