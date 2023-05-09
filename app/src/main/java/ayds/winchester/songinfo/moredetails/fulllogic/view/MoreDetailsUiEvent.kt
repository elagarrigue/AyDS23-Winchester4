package ayds.winchester.songinfo.moredetails.fulllogic.view

sealed class MoreDetailsUiEvent {
    object OpenArtistUrl : MoreDetailsUiEvent()
    object SearchArtist : MoreDetailsUiEvent()
}