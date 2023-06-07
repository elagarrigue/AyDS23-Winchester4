package ayds.winchester.songinfo.moredetails.presentation

import ayds.winchester.songinfo.moredetails.domain.entities.Card


data class MoreDetailsUiState(
    var cardList: List<Card>
)