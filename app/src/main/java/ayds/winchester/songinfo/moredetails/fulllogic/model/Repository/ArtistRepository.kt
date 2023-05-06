package ayds.winchester.songinfo.moredetails.fulllogic.model.repository

import ayds.winchester.songinfo.moredetails.fulllogic.model.entities.Artist
import ayds.winchester.songinfo.moredetails.fulllogic.model.entities.Artist.ArtistInfo
import ayds.winchester.songinfo.moredetails.fulllogic.model.entities.Artist.EmptyArtist
import ayds.winchester.songinfo.home.model.repository.external.spotify.WikipediaService
import ayds.winchester.songinfo.moredetails.fulllogic.model.Repository.Local.ArtistLocalStorage


 interface ArtistRepository {
    fun getArtistByName( artist : ArtistInfo): Artist
    fun getArtistById(id: String): Artist
}

internal class ArtistRepositoryImpl(
    private val artistLocalStorage: ArtistLocalStorage,
    private val artistTrackService: WikipediaService
) : ArtistRepository {

    override fun getArtistByName(artist: ArtistInfo): Artist {
        var artistInfo = artistLocalStorage.getArtistInfoFromDataBase(artist.artistName)

        when {
            artistInfo != null -> markArtistAsLocal(artistInfo)
            else -> {
                try {
                    artistInfo = artistTrackService.getArtist(artist.artistName)

                    artistInfo?.let {
                        artistLocalStorage.saveArtist(artist)
                    }
                } catch (e: Exception) {
                    artistInfo = null
                }
            }
        }

        return artistInfo ?: EmptyArtist
    }

    override fun getArtistById(id: String): Artist {
        return artistLocalStorage.getArtistById(id) ?: EmptyArtist
    }

    private fun ArtistInfo.isSavedSong() = artistLocalStorage.getArtistById(id) != null

    private fun markArtistAsLocal(artist: ArtistInfo) {
        artist.isLocallyStored = true
    }
}