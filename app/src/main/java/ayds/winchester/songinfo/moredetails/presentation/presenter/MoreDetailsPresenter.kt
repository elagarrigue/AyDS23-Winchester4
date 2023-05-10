package ayds.winchester.songinfo.moredetails.presentation.presenter

import ayds.observer.Observer
import ayds.winchester.songinfo.moredetails.domain.MoreDetailsModel
import ayds.winchester.songinfo.moredetails.presentation.view.MoreDetailsUiEvent
import ayds.winchester.songinfo.moredetails.presentation.view.MoreDetailsView

interface MoreDetailsPresenter {
    fun setMoreDetailsView(moreDetailsView: MoreDetailsView)
}

class MoreDetailsPresenterImpl(
    private val moreDetailsModel: MoreDetailsModel
): MoreDetailsPresenter {
        private lateinit var moreDetailsView: MoreDetailsView

        override fun setMoreDetailsView(moreDetailsView: MoreDetailsView) {
            this.moreDetailsView = moreDetailsView
            moreDetailsView.uiEventObservable.subscribe(observer)
        }

        private val observer: Observer<MoreDetailsUiEvent> =
            Observer { value ->
                when (value) {
                    MoreDetailsUiEvent.SearchArtist -> searchArtist()
                    MoreDetailsUiEvent.OpenArtistUrl -> openArtistUrl()
                }
            }

    private fun searchArtist() {
        // Warning: Never use Thread in android! Use coroutines
        Thread {
            moreDetailsModel.searchArtist(moreDetailsView.uiState.artistName)
        }.start()
    }


        private fun openArtistUrl() {
            moreDetailsView.openExternalLink(moreDetailsView.uiState.artistUrl)
        }
}