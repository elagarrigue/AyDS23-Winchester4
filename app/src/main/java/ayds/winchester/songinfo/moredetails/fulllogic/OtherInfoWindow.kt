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
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class OtherInfoWindow : AppCompatActivity() {
    private var textPaneWithArtistInformation: TextView? = null
    private var dataBase: DataBase? = null
    private var infoAboutArtist: String? = null
    private var artistName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)
        textPaneWithArtistInformation = findViewById(R.id.textPane2)
        dataBase = DataBase(this)
        artistName = intent.getStringExtra(ARTIST_NAME_EXTRA)
        infoAboutArtist = DataBase.getInfo(dataBase,artistName)
        startMoreInfoArtist()
    }

    private fun getCallResponseFromWikipediaAPI(): Response<String> {
            val retrofit = Retrofit.Builder()
                                   .baseUrl(BASE_URL)
                                   .addConverterFactory(ScalarsConverterFactory.create())
                                   .build()
            return retrofit.create(WikipediaAPI::class.java).getArtistInfo(artistName).execute()
    }

    private fun startMoreInfoArtist(){
        Thread {
            getArtistInfo()
        }.start()
    }

    private fun getArtistInfo() {
        infoAboutArtist = if(infoAboutArtist != null){
            "[*]$infoAboutArtist"
        } else {
            searchInfoArtist()
        }
        setTextArtistInfo()
    }

    private fun setTextArtistInfo() {
        if (infoAboutArtist != null) {
            DataBase.saveArtist(dataBase, artistName, infoAboutArtist)
        } else {
            infoAboutArtist = "No Results"
        }
        createMoreDetailsAboutArtistView()
    }

    private fun getJsonFromAPI(): JsonElement {
        return Gson().fromJson(getCallResponseFromWikipediaAPI().body(), JsonObject::class.java).get("query")
    }

    private fun searchInfoArtist(): String {
        val informationSearchBlokeDescriptionArtist = getJsonFromAPI().asJsonObject["search"].asJsonArray[0].asJsonObject["snippet"]
        setWikiUrlFromArtist()
        return informationSearchBlokeDescriptionArtist.asString.replace("\\n", "\n")
    }

    private fun setWikiUrlFromArtist() {
        val informationSearchBlokePageIdOfArtist = getJsonFromAPI().asJsonObject["search"].asJsonArray[0].asJsonObject["pageid"]
        findViewById<View>(R.id.openUrlButton).setOnClickListener {
            Intent(Intent.ACTION_VIEW).data = Uri.parse(BASE_WIKI_URL + informationSearchBlokePageIdOfArtist.asString)
            startActivity(intent)
        }
    }

    private fun createMoreDetailsAboutArtistView() {
        runOnUiThread {
            Picasso.get().load(IMAGE_URL).into(findViewById<View>(R.id.imageView) as ImageView)
            textPaneWithArtistInformation!!.text = Html.fromHtml(infoAboutArtist)
        }
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
        const val BASE_URL = "https://en.wikipedia.org/w/"
        const val IMAGE_URL = "https://upload.wikimedia.org/wikipedia/commons/8/8c/Wikipedia-logo-v2-es.png"
        const val BASE_WIKI_URL = "https://en.wikipedia.org/?curid="
    }
}