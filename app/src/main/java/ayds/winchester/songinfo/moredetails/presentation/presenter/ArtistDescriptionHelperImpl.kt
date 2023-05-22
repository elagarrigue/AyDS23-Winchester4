package ayds.winchester.songinfo.moredetails.presentation.presenter

import ayds.winchester.songinfo.moredetails.domain.entities.Artist
import ayds.winchester.songinfo.moredetails.domain.entities.Artist.ArtistInfo
import java.util.*


private const val PREFIX = "[*]"
private const val NO_RESULTS = "No results"

interface ArtistDescriptionHelper {
    fun getArtistDescriptionText(artist: Artist): String
}

 class ArtistDescriptionHelperImpl : ArtistDescriptionHelper {
    override fun getArtistDescriptionText(artist: Artist): String {
        return when (artist) {
            is ArtistInfo ->
                "${
                    if (artist.isLocallyStored) PREFIX else ""
                }\n" +
                    " ${textToHtml(artist.artistInfo, artist.artistName)}\n"
            else -> NO_RESULTS
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