package ayds.winchester.songinfo.moredetails.presentation.presenter

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.winchester.songinfo.moredetails.dependencyinjector.MoreDetailsViewInjector
import ayds.winchester.songinfo.moredetails.domain.entities.Artist
import ayds.winchester.songinfo.moredetails.domain.repository.ArtistRepository
import ayds.winchester.songinfo.moredetails.presentation.view.ArtistDescriptionHelper
import ayds.winchester.songinfo.moredetails.presentation.view.MoreDetailsUiState

interface MoreDetailsPresenter {
    val artistObservable: Observable<Artist>

    fun searchArtist(artistName: String)
    fun initObservers()
}

class MoreDetailsPresenterImpl(
    private val repository: ArtistRepository,
    private var uiState: MoreDetailsUiState
): MoreDetailsPresenter {

    private val artistDescriptionHelper: ArtistDescriptionHelper =
        MoreDetailsViewInjector.artistDescriptionHelper

    override val artistObservable = Subject<Artist>()
    override fun searchArtist(artistName: String) {
        repository.getArtistByName(artistName).let {
            artistObservable.notify(it)
        }
    }

    override fun initObservers() {
        artistObservable
            .subscribe { value -> updateArtistInfo(value) }
    }

    private fun updateArtistInfo(artist: Artist) {
        updateUiState(artist)
    }

    private fun updateUiState(artist: Artist) {
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


}