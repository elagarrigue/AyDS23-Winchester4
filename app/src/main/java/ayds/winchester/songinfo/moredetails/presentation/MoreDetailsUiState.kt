package ayds.winchester.songinfo.moredetails.presentation


data class MoreDetailsUiState(
    val artistInfo: String = "",
    val artistUrl: String = "",
    var source: String = "",
    var sourceLogoURL: String = "",
)