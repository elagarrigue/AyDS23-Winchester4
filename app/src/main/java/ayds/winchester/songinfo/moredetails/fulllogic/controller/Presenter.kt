package ayds.winchester.songinfo.moredetails.fulllogic.controller

import ayds.observer.Observer
import ayds.winchester.songinfo.moredetails.fulllogic.model.MoreDetailsModel
import ayds.winchester.songinfo.moredetails.fulllogic.model.entities.Artist
import ayds.winchester.songinfo.moredetails.fulllogic.view.MoreDetailsUiEvent
import ayds.winchester.songinfo.moredetails.fulllogic.view.MoreDetailsView


interface Presenter {

    fun setMoreDetailsView(moreDetailsView: MoreDetailsView)
}

internal class PresenterImpl(
    private val moreDetailsModel: MoreDetailsModel
) : Presenter {

    private lateinit var moreDetailsView: MoreDetailsView
    private lateinit var artist: Artist.ArtistInfo

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



    private fun showMoreDetailsInformation() {
        //busca en el modelo
        moreDetailsModel.searchArtist(artist.artistName)

        //usa private fun updateArtistInfo(artist: Artist)
        moreDetailsView.showMoreDetails(artist.artistName)
    }

    private fun openSongUrl() {
        moreDetailsView.openExternalLink(moreDetailsView.uiState.artistUrl)
    }
}