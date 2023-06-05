package ayds.winchester.songinfo.moredetails.presentation.presenter

import ayds.winchester.songinfo.moredetails.domain.entities.Card
import java.util.*


private const val PREFIX = "[*]"
private const val NO_RESULTS = "No results"

interface CardDescriptionHelper {
    fun getArtistDescriptionText(artist: Card, artistName: String): String
}

 class CardDescriptionHelperImpl : CardDescriptionHelper {
    override fun getArtistDescriptionText(card: Card, artistName: String): String {
        val cardDescription =
            if (card.description != "")
                "${
                    (if (card.isLocallyStored) PREFIX else "") +
                            (card.source)
                }\n" +
                        " ${textToHtml(card.description, artistName)}\n"
            else {
             NO_RESULTS
            }
        return cardDescription
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