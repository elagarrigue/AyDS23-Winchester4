package ayds.winchester.songinfo.moredetails.fulllogic.view

data class MoreDetailsUiState(
    val artistImageUrl: String = DEFAULT_IMAGE,
    val artistInfo: String = NO_RESULTS,
    val artistUrl: String = "",
    val actionsEnabled: Boolean = false,
) {

    companion object {
        const val DEFAULT_IMAGE =
            "https://upload.wikimedia.org/wikipedia/commons/8/8c/Wikipedia-logo-v2-es.png"
        const val NO_RESULTS = "No Results"
    }
}