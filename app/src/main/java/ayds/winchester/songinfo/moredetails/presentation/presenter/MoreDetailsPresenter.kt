package ayds.winchester.songinfo.moredetails.presentation.presenter

import ayds.winchester.songinfo.moredetails.dependencyinjector.MoreDetailsInjector
import ayds.winchester.songinfo.moredetails.domain.entities.Artist
import ayds.winchester.songinfo.moredetails.domain.repository.ArtistRepository
import ayds.winchester.songinfo.moredetails.presentation.view.ArtistDescriptionHelper
import ayds.winchester.songinfo.moredetails.presentation.view.MoreDetailsUiState

interface MoreDetailsPresenter {

    fun searchArtist(artistName: String)

    fun getUiState() : MoreDetailsUiState
}

class MoreDetailsPresenterImpl(
    private val repository: ArtistRepository,
    private var uiState: MoreDetailsUiState
): MoreDetailsPresenter {

    private val artistDescriptionHelper: ArtistDescriptionHelper =
        MoreDetailsInjector.artistDescriptionHelper

    override fun searchArtist(artistName: String) {
        val artist = repository.getArtistByName(artistName)
        updateArtistInfo(artist)
    }

    private fun updateArtistInfo(artist: Artist) {
        when (artist) {
            is Artist.ArtistInfo -> updateArtistUiState(artist)
            Artist.EmptyArtist -> noUpdateArtistUiState()
        }
    }

    private fun updateArtistUiState(artist: Artist.ArtistInfo) {
        uiState = uiState.copy(
            artistImageUrl = MoreDetailsUiState.DEFAULT_IMAGE,
            artistInfo = artistDescriptionHelper.getArtistDescriptionText(artist),
            artistUrl = artist.wikipediaUrl,
        )
    }
    private fun noUpdateArtistUiState() {
        uiState = uiState.copy(
            artistImageUrl = MoreDetailsUiState.DEFAULT_IMAGE,
            artistInfo = artistDescriptionHelper.getArtistDescriptionText(Artist.EmptyArtist),
            artistUrl = "",
        )
    }

    override fun getUiState(): MoreDetailsUiState {
        return uiState
    }

}