package ayds.winchester.songinfo.moredetails.presentation.presenter

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.winchester.songinfo.moredetails.dependencyinjector.MoreDetailsInjector
import ayds.winchester.songinfo.moredetails.domain.entities.Artist
import ayds.winchester.songinfo.moredetails.domain.entities.Artist.EmptyArtist
import ayds.winchester.songinfo.moredetails.domain.entities.Artist.ArtistInfo
import ayds.winchester.songinfo.moredetails.domain.repository.ArtistRepository
import ayds.winchester.songinfo.moredetails.presentation.view.ArtistDescriptionHelper
import ayds.winchester.songinfo.moredetails.presentation.view.MoreDetailsUiState

interface MoreDetailsPresenter {
    val artistObservable: Observable<MoreDetailsUiState>
    fun searchArtist(artistName: String)
}

class MoreDetailsPresenterImpl(
    private val repository: ArtistRepository
): MoreDetailsPresenter {

    override val artistObservable = Subject<MoreDetailsUiState>()
    private val artistDescriptionHelper: ArtistDescriptionHelper =
        MoreDetailsInjector.artistDescriptionHelper

    override fun searchArtist(artistName: String) {
        Thread {
            val artist=repository.getArtistByName(artistName)
            setArtistUiState(artist)
        }.start()
    }

    private fun setArtistUiState(artist: Artist) {
        when (artist) {
            is ArtistInfo -> updateArtistUiState(artist).let {
                artistObservable.notify(it)
            }
            EmptyArtist -> noUpdateArtistUiState().let {
                artistObservable.notify(it)
            }
        }
    }

    private fun updateArtistUiState(artist: ArtistInfo) : MoreDetailsUiState {
        var uiState = MoreDetailsUiState()
        uiState = uiState.copy(
            artistImageUrl = MoreDetailsUiState.DEFAULT_IMAGE,
            artistInfo = artistDescriptionHelper.getArtistDescriptionText(artist),
            artistUrl = artist.wikipediaUrl,
        )
        return uiState
    }
    private fun noUpdateArtistUiState() : MoreDetailsUiState {
        var uiState = MoreDetailsUiState()
        uiState = uiState.copy(
            artistImageUrl = MoreDetailsUiState.DEFAULT_IMAGE,
            artistInfo = artistDescriptionHelper.getArtistDescriptionText(EmptyArtist),
            artistUrl = MoreDetailsUiState.BASE_WIKI_URL,
        )
        return uiState
    }

}