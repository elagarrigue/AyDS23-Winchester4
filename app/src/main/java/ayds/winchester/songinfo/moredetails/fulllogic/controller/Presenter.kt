package ayds.winchester.songinfo.moredetails.fulllogic.controller

import ayds.observer.Observer
import ayds.winchester.songinfo.moredetails.fulllogic.model.MoreDetailsModel
import ayds.winchester.songinfo.moredetails.fulllogic.view.MoreDetailsUiEvent
import ayds.winchester.songinfo.moredetails.fulllogic.view.MoreDetailsView


interface Presenter {

    fun setMoreDetailsView(moreDetailsView: MoreDetailsView)
}

internal class PresenterImpl(
    private val moreDetailsModel: MoreDetailsModel
) : Presenter {

    private lateinit var moreDetailsView: MoreDetailsView

    override fun setMoreDetailsView(moreDetailsView: MoreDetailsView) {
        this.moreDetailsView = moreDetailsView
        moreDetailsView.uiEventObservable.subscribe(observer)
    }

    private val observer: Observer<MoreDetailsUiEvent> =
        Observer { value ->
            when (value) {
                MoreDetailsUiEvent.OpenArtistUrl -> MoreDetailsUiEvent.OpenArtistUrl()
            }
        }



    private fun searchArtist() {
        //busca en el modelo

        //usa private fun updateArtistInfo(artist: Artist)
    }

    private fun openSongUrl() {
        moreDetailsView.openExternalLink(moreDetailsView.uiState.artistUrl)
    }
}