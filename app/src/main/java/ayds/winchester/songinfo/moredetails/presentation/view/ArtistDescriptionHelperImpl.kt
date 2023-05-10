package ayds.winchester.songinfo.moredetails.presentation.view

import android.text.Html
import ayds.winchester.songinfo.moredetails.domain.entities.Artist
import ayds.winchester.songinfo.moredetails.domain.entities.Artist.ArtistInfo


interface ArtistDescriptionHelper {
    fun getArtistDescriptionText(artist: Artist): String
}

 class ArtistDescriptionHelperImpl : ArtistDescriptionHelper {
    override fun getArtistDescriptionText(artist: Artist): String {
        return when (artist) {
            is ArtistInfo ->
                "${
                    if (artist.isLocallyStored) "[*]" else ""
                }\n" +
                    " ${Html.fromHtml(artist.artistInfo)}\n"
            else -> "No results"
        }
    }
}