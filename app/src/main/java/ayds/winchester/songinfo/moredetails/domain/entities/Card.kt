package ayds.winchester.songinfo.moredetails.domain.entities
data class Card(
    var description: String,
    var infoURL: String,
    var source: String,
    var sourceLogoURL: String,
    var isLocallyStored: Boolean = false
)