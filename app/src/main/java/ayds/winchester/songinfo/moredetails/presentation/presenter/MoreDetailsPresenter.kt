package ayds.winchester.songinfo.moredetails.presentation.presenter

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.winchester.songinfo.moredetails.domain.entities.Card
import ayds.winchester.songinfo.moredetails.domain.repository.ArtistRepository
import ayds.winchester.songinfo.moredetails.presentation.MoreDetailsUiState

interface MoreDetailsPresenter {
    val artistObservable : Observable<Collection<MoreDetailsUiState>>
    fun searchArtist(artistName: String)
}

class MoreDetailsPresenterImpl(
    private val repository: ArtistRepository,
    private val artistDescriptionHelper: ArtistDescriptionHelper
): MoreDetailsPresenter {

    override val artistObservable = Subject<Collection<MoreDetailsUiState>>()
        override fun searchArtist(artistName: String) {
        Thread {
            val artist=repository.getArtistByName(artistName)
            if (artist != null) {
                setArtistUiState(artist,artistName)
            }
        }.start()
    }

    private fun setArtistUiState(cards: Collection<Card>,artistName: String) {
        when (cards) {
                cards -> updateArtistUiState(cards, artistName).let {
                        artistObservable.notify(it)
                    }
        }
    }

    private fun updateArtistUiState(cards: Collection<Card>,artistName: String) : Collection<MoreDetailsUiState> {
        val uiStateCollection: MutableCollection<MoreDetailsUiState> = mutableListOf()
        var uiState = MoreDetailsUiState()
        cards.forEach { card ->
            uiState = uiState.copy(
                artistInfo = artistDescriptionHelper.getArtistDescriptionText(card, artistName),
                artistUrl = card.infoURL,
                source = card.source,
                sourceLogoURL = card.sourceLogoURL,
            )
            uiStateCollection.add(uiState)
        }
        return uiStateCollection
    }

}