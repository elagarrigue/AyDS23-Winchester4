

sealed class Artist {

    data class ArtistInfo(
        var id: String?,
        var artistName: String?,
        var artistInfo: String?,
        var wikipediaUrl: String?,
        var isLocallyStored: Boolean = false
    )
    object EmptyArtist : Artist()
}