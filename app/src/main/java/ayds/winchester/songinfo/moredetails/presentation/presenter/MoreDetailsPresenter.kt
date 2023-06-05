package ayds.winchester.songinfo.moredetails.presentation.presenter

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.winchester.songinfo.moredetails.domain.entities.Card
import ayds.winchester.songinfo.moredetails.domain.repository.ArtistRepository
import ayds.winchester.songinfo.moredetails.presentation.MoreDetailsUiState

interface MoreDetailsPresenter {
    val cardObservable : Observable<MoreDetailsUiState>
    fun searchArtist(artistName: String)
}

internal class MoreDetailsPresenterImpl(
    private val repository: ArtistRepository,
    private val cardDescriptionHelper: CardDescriptionHelper
): MoreDetailsPresenter {

    override val cardObservable = Subject<MoreDetailsUiState>()
        override fun searchArtist(artistName: String) {
        Thread {
            val artist=repository.getCards(artistName)
            updateCardDescription(artist,artistName)
            setArtistUiState(artist)
        }.start()
    }

    private fun setArtistUiState(cards: List<Card>?) {
        if (cards != null) {
            when (cards) {
                cards -> updateArtistUiState(cards).let {
                    cardObservable.notify(it)
                }
            }
        }
    }

    private fun updateArtistUiState(cards: List<Card>) : MoreDetailsUiState {
        val uiStateList= MoreDetailsUiState(cards)
        return uiStateList
    }

    private fun updateCardDescription(cards: List<Card>?,artistName: String) {
        cards?.forEach{ card ->
           card.description = cardDescriptionHelper.getArtistDescriptionText(card,artistName)
        }
    }

}