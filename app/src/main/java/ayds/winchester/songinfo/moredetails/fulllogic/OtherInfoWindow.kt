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
import com.squareup.picasso.Picasso

class OtherInfoWindow : AppCompatActivity() {
    private var textPaneWithArtistInformation: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAll()
    }

    private fun initAll() {
        initContentView()
        initTextPaneArtist()
        initInfoDataClass()
        startMoreInfoArtist()
        setWikiUrlFromArtist()
    }

    private fun initContentView() {
        setContentView(R.layout.activity_other_info)
    }

    private fun initTextPaneArtist() {
        textPaneWithArtistInformation = findViewById(R.id.textPane2)
    }

    private fun initInfoDataClass() {
        setArtistName()
        setInfoAboutArtist()
    }

    private fun setArtistName() {
        infoAboutArtist.artistName = intent.getStringExtra(ARTIST_NAME_EXTRA)
    }

    private fun setArtistGeneralInformation() {
        infoAboutArtist.generalInformation = infoAboutArtist.artistName?.let { dataBase!!.getArtistInfoFromDataBase(it) }
    }

    private fun startMoreInfoArtist(){
        Thread {
            getArtistInfo()
        }.start()
    }

    private fun setWikiUrlFromArtist() {
        findViewById<View>(R.id.openUrlButton).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(infoAboutArtist.url))
            startActivity(intent)
        }
    }

    private fun getArtistInfo() {
        setTextArtistInfo()
    }

    private fun setTextArtistInfo() {
        createMoreDetailsAboutArtistView()
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
        textPaneWithArtistInformation!!.text = Html.fromHtml(infoAboutArtist.generalInformation)
    }


}