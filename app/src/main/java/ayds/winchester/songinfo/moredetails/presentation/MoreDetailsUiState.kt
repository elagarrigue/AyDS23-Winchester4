package ayds.winchester.songinfo.moredetails.presentation

import ayds.winchester.songinfo.moredetails.domain.entities.Source


data class MoreDetailsUiState(
    val artistInfo: String = "",
    val artistUrl: String = "",
    var source: String = "",
    var sourceLogoURL: String = "",
)