package ayds.winchester.songinfo.moredetails.presentation.view

sealed class MoreDetailsUiEvent {
    object OpenArtistUrl : MoreDetailsUiEvent()
    object SearchArtist : MoreDetailsUiEvent()
}