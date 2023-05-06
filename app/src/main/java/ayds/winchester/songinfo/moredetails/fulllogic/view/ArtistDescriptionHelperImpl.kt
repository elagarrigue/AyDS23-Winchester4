package ayds.winchester.songinfo.moredetails.fulllogic.view

import Artist
import ayds.winchester.songinfo.home.model.entities.Song

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
                    "Artist: ${artist.artistInfo}\n"

            else -> ""
        }
    }
}