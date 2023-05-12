package ayds.winchester.songinfo.moredetails.presentation.view

import android.text.Html
import ayds.winchester.songinfo.moredetails.domain.entities.Artist
import ayds.winchester.songinfo.moredetails.domain.entities.Artist.ArtistInfo
import java.util.*


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
                    " ${Html.fromHtml(textToHtml(artist.artistInfo, artist.artistName))}\n"
            else -> "No results"
        }
    }

     private fun textToHtml(artistInfo: String, artistName: String?): String {
         val builder = StringBuilder()
         val textWithBold = artistInfo.replace("'", " ")
                                      .replace("\n", "<br>")
                                      .replace("(?i)$artistName".toRegex(),"<b>"
                                             + artistName!!.uppercase(Locale.getDefault())
                                             + "</b>")
         builder.append("<html><div width=400>")
             .append("<font face=\"arial\">")
             .append(textWithBold)
             .append("</font></div></html>")
         return builder.toString()
     }

 }