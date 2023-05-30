package ayds.winchester.songinfo.moredetails.domain.entities

enum class Source {
    Wikipedia,
    NewYorkTimes,
    Null,
    LastFM
}

data class Card(
    var description: String,
    var infoURL: String,
    var source: Source,
    var sourceLogoURL: String,
    var isLocallyStored: Boolean = false
)