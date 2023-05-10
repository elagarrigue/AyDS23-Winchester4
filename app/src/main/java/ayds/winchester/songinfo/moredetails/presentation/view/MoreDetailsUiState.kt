package ayds.winchester.songinfo.moredetails.presentation.view

data class MoreDetailsUiState(
    val artistName: String = "",
    val artistImageUrl: String = DEFAULT_IMAGE,
    val artistInfo: String = "",
    val artistUrl: String = "",
) {

    companion object {
        const val DEFAULT_IMAGE =
            "https://upload.wikimedia.org/wikipedia/commons/8/8c/Wikipedia-logo-v2-es.png"
    }
}