package ayds.winchester.songinfo.moredetails.fulllogic.model.repository

import ayds.winchester.songinfo.moredetails.fulllogic.model.entities.Artist
import ayds.winchester.songinfo.moredetails.fulllogic.model.entities.Artist.ArtistInfo
import ayds.winchester.songinfo.moredetails.fulllogic.model.entities.Artist.EmptyArtist
import ayds.winchester.songinfo.moredetails.fulllogic.model.repository.external.WikipediaService
import ayds.winchester.songinfo.moredetails.fulllogic.model.repository.local.ArtistLocalStorage


 interface ArtistRepository {
    fun getArtistByName(artistName: String): Artist
    fun getArtistById(id: String): Artist
}

internal class ArtistRepositoryImpl(
    private val artistLocalStorage: ArtistLocalStorage,
    private val artistTrackService: WikipediaService
) : ArtistRepository {

    override fun getArtistByName(artistName: String): Artist {
        var artist = artistLocalStorage.getArtistInfoFromDataBase(artistName)

        when {
            artist != null -> markArtistAsLocal(artist)
            else -> {
                try {
                    artist = artistTrackService.getArtist(artistName)

                    artist?.let {
                        artistLocalStorage.saveArtist(artist!!)
                    }
                } catch (e: Exception) {
                    artist = null
                }
            }
        }

        return artist ?: EmptyArtist
    }

    override fun getArtistById(id: String): Artist {
        return artistLocalStorage.getArtistById(id) ?: EmptyArtist
    }

    private fun ArtistInfo.isSavedSong() = artistLocalStorage.getArtistById(id) != null

    private fun markArtistAsLocal(artist: ArtistInfo) {
        artist.isLocallyStored = true
    }
}