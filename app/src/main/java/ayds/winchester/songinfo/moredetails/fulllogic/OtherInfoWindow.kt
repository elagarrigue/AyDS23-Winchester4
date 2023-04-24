package ayds.winchester.songinfo.moredetails.fulllogic

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ayds.winchester.songinfo.R
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*

class OtherInfoWindow : AppCompatActivity() {
    private var textPane: TextView? = null
    private var dataBase: DataBase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)
        textPane = findViewById(R.id.textPane2)
        dataBase = DataBase(this)
        open(intent.getStringExtra(ARTIST_NAME_EXTRA))
    }

    private fun getCallResponseFromWikipediaAPI(artistName: String?): Response<String> {
            val retrofit = Retrofit.Builder()
                                   .baseUrl(BASE_URL)
                                   .addConverterFactory(ScalarsConverterFactory.create())
                                   .build()
            val wikipediaAPI = retrofit.create(WikipediaAPI::class.java)
            return wikipediaAPI.getArtistInfo(artistName).execute()
    }

    private fun open(artistName: String?){
        Thread {
            getArtistInfo(artistName)
        }.start()
    }

    private fun getTextModify(descriptionArtist: String?, artistName: String?) {
        val artistInfoModify: String
        if (descriptionArtist != null) {
            artistInfoModify = textToHtml(descriptionArtist, artistName)
            DataBase.saveArtist(dataBase, artistName, artistInfoModify)
        } else {
            artistInfoModify = "No Results"
        }
        createMoreDetailsView(artistInfoModify)
    }

    private fun getArtistInfo(artistName: String?) {
        var infoArtistToModify = DataBase.getInfo(dataBase,artistName)
        infoArtistToModify = if(infoArtistToModify != null){
            "[*]$infoArtistToModify"
        } else {
            getInfoArtistFromDataBase(artistName)
        }
        getTextModify(infoArtistToModify, artistName)
    }

    private fun getInfoArtistFromDataBase(artistName: String?): String {
            val callResponse = getCallResponseFromWikipediaAPI(artistName)
            val gsonObject = Gson()
            val jsonObjectFromGson = gsonObject.fromJson(callResponse.body(), JsonObject::class.java)
            val artistInformation = jsonObjectFromGson["query"].asJsonObject
            val artistInformationBlokeSearch = artistInformation["search"].asJsonArray[0].asJsonObject
            val informationSearchBlokeDescriptionArtist = artistInformationBlokeSearch["snippet"]
            val informationSearchBlokePageIdOfArtist = artistInformationBlokeSearch["pageid"]
            val infoArtistToModify = informationSearchBlokeDescriptionArtist.asString.replace("\\n", "\n")
            openWikiUrlFromArtist(informationSearchBlokePageIdOfArtist.asString)
            return infoArtistToModify
    }

    private fun openWikiUrlFromArtist(PageIdOfArtist: String) {
        val urlString = BASE_WIKI_URL + PageIdOfArtist
        findViewById<View>(R.id.openUrlButton).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(urlString)
            startActivity(intent)
        }
    }

    private fun createMoreDetailsView(artistInfoModify: String) {
        runOnUiThread {
            Picasso.get().load(IMAGE_URL).into(findViewById<View>(R.id.imageView) as ImageView)
            textPane!!.text =Html.fromHtml(artistInfoModify)
        }
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
        const val BASE_URL = "https://en.wikipedia.org/w/"
        const val IMAGE_URL = "https://upload.wikimedia.org/wikipedia/commons/8/8c/Wikipedia-logo-v2-es.png"
        const val BASE_WIKI_URL = "https://en.wikipedia.org/?curid="
        fun textToHtml(artistInfo: String, artistName: String?): String {
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
}