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
    private var infoArtistToModify: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)

        textPane = findViewById(R.id.textPane2)

        dataBase = DataBase(this)

        infoArtistToModify = DataBase.getInfo(dataBase,intent.getStringExtra(ARTIST_NAME_EXTRA))

        open()
    }

    private fun getCallResponseFromWikipediaAPI(): Response<String> {
            val retrofit = Retrofit.Builder()
                                   .baseUrl(BASE_URL)
                                   .addConverterFactory(ScalarsConverterFactory.create())
                                   .build()
            return retrofit.create(WikipediaAPI::class.java).getArtistInfo(intent.getStringExtra(ARTIST_NAME_EXTRA)).execute()
    }

    private fun open(){
        Thread {
            getArtistInfo()
        }.start()
    }

    private fun getArtistInfo() {

        infoArtistToModify = if(infoArtistToModify != null){
            "[*]$infoArtistToModify"
        } else {
            getInfoArtistFromDataBase()
        }
        getTextModify()
    }

    private fun getTextModify() {

        if (infoArtistToModify != null) {
            DataBase.saveArtist(dataBase, intent.getStringExtra(ARTIST_NAME_EXTRA), infoArtistToModify)
        } else {
            infoArtistToModify = "No Results"
        }
        createMoreDetailsView()
    }


    private fun getInfoArtistFromDataBase(): String {


            val informationSearchBlokeDescriptionArtist = Gson().fromJson(getCallResponseFromWikipediaAPI().body(), JsonObject::class.java)["query"].asJsonObject["search"].asJsonArray[0].asJsonObject["snippet"]

            openWikiUrlFromArtist()

            return informationSearchBlokeDescriptionArtist.asString.replace("\\n", "\n")
    }

    private fun openWikiUrlFromArtist() {

        val informationSearchBlokePageIdOfArtist = Gson().fromJson(getCallResponseFromWikipediaAPI().body(), JsonObject::class.java)["query"].asJsonObject["search"].asJsonArray[0].asJsonObject["pageid"]

        findViewById<View>(R.id.openUrlButton).setOnClickListener {
            Intent(Intent.ACTION_VIEW).data = Uri.parse(BASE_WIKI_URL + informationSearchBlokePageIdOfArtist.asString)
            startActivity(intent)
        }
    }

    private fun createMoreDetailsView() {
        runOnUiThread {
            Picasso.get().load(IMAGE_URL).into(findViewById<View>(R.id.imageView) as ImageView)
            textPane!!.text = Html.fromHtml(infoArtistToModify)
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