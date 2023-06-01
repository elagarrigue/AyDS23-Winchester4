package ayds.winchester.songinfo.moredetails.presentation.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ayds.winchester.songinfo.R
import ayds.winchester.songinfo.moredetails.dependencyinjector.MoreDetailsInjector
import ayds.winchester.songinfo.moredetails.domain.entities.Source
import ayds.winchester.songinfo.moredetails.presentation.MoreDetailsUiState
import ayds.winchester.songinfo.moredetails.presentation.presenter.MoreDetailsPresenter
import ayds.winchester.songinfo.utils.UtilsInjector.imageLoader

interface MoreDetailsView

class MoreDetailsViewActivity : AppCompatActivity(), MoreDetailsView {
    private lateinit var artistInfoTextViewWikipedia : TextView
    private lateinit var urlButtonWikipedia : Button
    private lateinit var imageViewWikipedia : ImageView

    private lateinit var artistInfoTextViewLastFM : TextView
    private lateinit var urlButtonLastFM : Button
    private lateinit var imageViewLastFM : ImageView

    private lateinit var artistInfoTextViewNewYorkTime : TextView
    private lateinit var urlButtonNewYorkTime : Button
    private lateinit var imageViewNewYorkTime : ImageView

    private lateinit var moreDetailsPresenter: MoreDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)
        initAll()
    }

    private fun initAll() {
        initModule()
        initProperties()
        initMoreDetailsPresenter()
        initObservers()
    }

    private fun initModule() {
        MoreDetailsInjector.initOnViewStarted(this)
    }

    private fun initProperties() {
        artistInfoTextViewWikipedia  = findViewById(R.id.artistInfoTextPanel)
        imageViewWikipedia = findViewById(R.id.imageView)
        urlButtonWikipedia = findViewById(R.id.openUrlButton)

        artistInfoTextViewLastFM  = findViewById(R.id.artistInfoTextPanel2)
        imageViewLastFM = findViewById(R.id.imageView2)
        urlButtonLastFM = findViewById(R.id.openUrlButton2)

        artistInfoTextViewNewYorkTime  = findViewById(R.id.artistInfoTextPanel3)
        imageViewNewYorkTime = findViewById(R.id.imageView3)
        urlButtonNewYorkTime = findViewById(R.id.openUrlButton3)
    }

    private fun initMoreDetailsPresenter() {
        moreDetailsPresenter = MoreDetailsInjector.getMoreDetailsPresenter()
        moreDetailsPresenter.searchArtist(initArtistName())
    }

    private fun initObservers() {
        moreDetailsPresenter.artistObservable
            .subscribe { value -> setArtistInfo(value) }
    }

    private fun initArtistName() = intent.getStringExtra(ARTIST_NAME_EXTRA).toString()


    private fun setArtistInfo(artistUiStateCollection: Collection<MoreDetailsUiState>){
        artistUiStateCollection.forEach { artistUiState ->
            if(artistUiState.source == "Wikipedia") {
            updateArtistInfoWikipedia(artistUiState)
            }
            if(artistUiState.source == "LastFM") {
                updateArtistInfoLastFM(artistUiState)
            }
            if(artistUiState.source == "NewYorkTimes") {
                updateArtistInfoNewYorkTime(artistUiState)
            }
        }
    }

    private fun updateArtistInfoWikipedia(artistUiState: MoreDetailsUiState){
        updateTextWikipedia(artistUiState)
        updateImageWikipedia(artistUiState)
        setWikipediaButton(artistUiState)

    }

    private fun updateTextWikipedia(artistUiState: MoreDetailsUiState) {
        runOnUiThread {
                artistInfoTextViewWikipedia.text = Html.fromHtml(artistUiState.artistInfo)
        }
    }

    private fun updateImageWikipedia(artistUiState: MoreDetailsUiState) {
        runOnUiThread {
                imageLoader.loadImageIntoView(artistUiState.sourceLogoURL, imageViewWikipedia)
        }
    }

    private fun setWikipediaButton(artistUiState: MoreDetailsUiState) {
        urlButtonWikipedia.setOnClickListener {
            setIntent(artistUiState)
        }
    }

    private fun updateArtistInfoLastFM(artistUiState: MoreDetailsUiState){
        updateTextLastFM(artistUiState)
        updateImageLastFM(artistUiState)
        setLastFMButton(artistUiState)

    }

    private fun updateTextLastFM(artistUiState: MoreDetailsUiState) {
        runOnUiThread {
            artistInfoTextViewLastFM.text = Html.fromHtml(artistUiState.artistInfo)
        }
    }

    private fun updateImageLastFM(artistUiState: MoreDetailsUiState) {
        runOnUiThread {
            imageLoader.loadImageIntoView(artistUiState.sourceLogoURL, imageViewLastFM)
        }
    }

    private fun setLastFMButton(artistUiState: MoreDetailsUiState) {
        urlButtonLastFM.setOnClickListener {
            setIntent(artistUiState)
        }
    }

    private fun updateArtistInfoNewYorkTime(artistUiState: MoreDetailsUiState){
        updateTextNewYorkTime(artistUiState)
        updateImageNewYorkTime(artistUiState)
        setNewYorkTimeButton(artistUiState)

    }

    private fun updateTextNewYorkTime(artistUiState: MoreDetailsUiState) {
        runOnUiThread {
            artistInfoTextViewNewYorkTime.text = Html.fromHtml(artistUiState.artistInfo)
        }
    }

    private fun updateImageNewYorkTime(artistUiState: MoreDetailsUiState) {
        runOnUiThread {
            imageLoader.loadImageIntoView(artistUiState.sourceLogoURL, imageViewNewYorkTime)
        }
    }

    private fun setNewYorkTimeButton(artistUiState: MoreDetailsUiState) {
        urlButtonNewYorkTime.setOnClickListener {
            setIntent(artistUiState)
        }
    }

    private fun setIntent(artistUiState: MoreDetailsUiState){
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(artistUiState.artistUrl)
        startActivity(intent)
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }
}