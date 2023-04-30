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

        initTextPaneArtist()
        initDataBase()
        initArtistName()
        initInfoAboutArtist()
        startMoreInfoArtist()
    }

    private fun initTextPaneArtist() {
        textPaneWithArtistInformation = findViewById(R.id.textPane2)
    }

    private fun initDataBase() {
        dataBase = DataBase(this)
    }

    private fun initArtistName() {
        artistName = intent.getStringExtra(ARTIST_NAME_EXTRA)
    }

    private fun initInfoAboutArtist() {
        infoAboutArtist = artistName?.let { dataBase!!.getArtistInfo(it) }
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
        if(infoAboutArtist != null){
            addAsterisk()
        } else {
            searchInfoArtist()
        }
        setTextArtistInfo()
    }

    private fun addAsterisk() {
        infoAboutArtist =  "[*]$infoAboutArtist"
    }

    private fun setTextArtistInfo() {
        if (infoAboutArtist != null) {
            saveArtistInfo()
        } else {
            setNoResults()
        }
        createMoreDetailsAboutArtistView()
    }

    private fun saveArtistInfo() {
        artistName?.let { dataBase?.saveArtist(it, infoAboutArtist!!) }
    }

    private fun setNoResults() {
        infoAboutArtist = "No Results"
    }

    private fun getJsonQueryFromAPI(): JsonElement {
        return Gson().fromJson(getCallResponseFromWikipediaAPI().body(), JsonObject::class.java).get("query")
    }

    private fun getArtistSnippet() : JsonElement{
        return getJsonQueryFromAPI().asJsonObject["search"].asJsonArray[0].asJsonObject["snippet"]
    }

    private fun getArtistPageId() : JsonElement{
        return getJsonQueryFromAPI().asJsonObject["search"].asJsonArray[0].asJsonObject["pageid"]
    }

    private fun searchInfoArtist(): String {
        setWikiUrlFromArtist()
        return getArtistSnippet().asString.replace("\\n", "\n")
    }

    private fun setWikiUrlFromArtist() {
        findViewById<View>(R.id.openUrlButton).setOnClickListener {
            Intent(Intent.ACTION_VIEW).data = Uri.parse(BASE_WIKI_URL + getArtistPageId().asString)
            startActivity(intent)
        }
    }

    private fun createMoreDetailsAboutArtistView() {
        runOnUiThread {
            createImageMoreDetailsAboutArtistView()
            createTextMoreDetailsAboutArtistView()
        }
    }

    private fun createImageMoreDetailsAboutArtistView() {
            Picasso.get().load(IMAGE_URL).into(findViewById<View>(R.id.imageView) as ImageView)
    }

    private fun createTextMoreDetailsAboutArtistView() {
            textPaneWithArtistInformation!!.text = Html.fromHtml(infoAboutArtist)
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
        const val BASE_URL = "https://en.wikipedia.org/w/"
        const val IMAGE_URL = "https://upload.wikimedia.org/wikipedia/commons/8/8c/Wikipedia-logo-v2-es.png"
        const val BASE_WIKI_URL = "https://en.wikipedia.org/?curid="
    }
}