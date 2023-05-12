package ayds.winchester.songinfo.moredetails.presentation.presenter

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.winchester.songinfo.moredetails.domain.entities.Artist
import ayds.winchester.songinfo.moredetails.domain.entities.Artist.EmptyArtist
import ayds.winchester.songinfo.moredetails.domain.entities.Artist.ArtistInfo
import ayds.winchester.songinfo.moredetails.domain.repository.ArtistRepository
import ayds.winchester.songinfo.moredetails.presentation.MoreDetailsUiState

interface MoreDetailsPresenter {
    val artistObservable : Observable<MoreDetailsUiState>
    fun searchArtist(artistName: String)
}

class MoreDetailsPresenterImpl(
    private val repository: ArtistRepository,
    private val artistDescriptionHelper: ArtistDescriptionHelper
): MoreDetailsPresenter {

    override val artistObservable = Subject<MoreDetailsUiState>()
        override fun searchArtist(artistName: String) {
        Thread {
            val artist=repository.getArtistByName(artistName)
            setArtistUiState(artist)
        }.start()
    }

    private fun setArtistUiState(artist: Artist) {
        when (artist) {
                artist -> updateArtistUiState(artist).let {
                    artistObservable.notify(it)
                }
        }
    }

    private fun updateArtistUiState(artist: Artist) : MoreDetailsUiState {
        var uiState = MoreDetailsUiState()
        uiState = uiState.copy(
            artistInfo = artistDescriptionHelper.getArtistDescriptionText(artist),
            artistUrl = when (artist) {
                            is ArtistInfo -> artist.wikipediaUrl
                            is EmptyArtist -> artist.wikipediaUrl
                        }
        )
        return uiState
    }

}