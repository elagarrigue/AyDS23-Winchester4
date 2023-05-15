package ayds.winchester.songinfo.moredetails.domain.entities

const val BASE_WIKI_URL = "https://en.wikipedia.org/?curid="
const val DEFAULT_IMAGE = "https://upload.wikimedia.org/wikipedia/commons/8/8c/Wikipedia-logo-v2-es.png"

sealed class Artist {

    data class ArtistInfo(
        var artistName: String,
        var artistInfo: String,
        var wikipediaUrl: String,
        var isLocallyStored: Boolean = false
    ): Artist()
    data class EmptyArtist(
        var wikipediaUrl: String = BASE_WIKI_URL
    ) : Artist()
}