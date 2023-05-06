package ayds.winchester.songinfo.moredetails.fulllogic.view




interface ArtistDescriptionHelper {
    fun getArtistDescriptionText(artist: Artist.ArtistInfo ): String
}

internal class ArtistDescriptionHelperImpl () : ArtistDescriptionHelper {
    override fun getArtistDescriptionText(artist: Artist.ArtistInfo): String {
        return when (artist) {
            is Artist.ArtistInfo ->
                "${
                    if (artist.isLocallyStored) "[*]" else ""
                }\n" +
                    " ${artist.artistInfo}\n"

            else -> ""
        }
    }
}