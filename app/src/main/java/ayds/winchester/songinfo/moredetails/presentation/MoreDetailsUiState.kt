package ayds.winchester.songinfo.moredetails.presentation

import ayds.winchester.songinfo.moredetails.domain.entities.DEFAULT_IMAGE

data class MoreDetailsUiState(
    val artistImageUrl: String = DEFAULT_IMAGE,
    val artistInfo: String = "",
    val artistUrl: String = "",
)