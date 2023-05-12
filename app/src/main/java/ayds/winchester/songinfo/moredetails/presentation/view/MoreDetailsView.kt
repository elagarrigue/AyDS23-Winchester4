package ayds.winchester.songinfo.moredetails.presentation.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ayds.winchester.songinfo.R
import ayds.winchester.songinfo.moredetails.dependencyinjector.MoreDetailsInjector
import ayds.winchester.songinfo.moredetails.presentation.presenter.MoreDetailsPresenter
import ayds.winchester.songinfo.utils.UtilsInjector.imageLoader

interface MoreDetailsView

class MoreDetailsViewActivity : AppCompatActivity(), MoreDetailsView {
    private lateinit var textView: TextView
    private lateinit var wikipediaUrlButton: Button
    private lateinit var wikipediaImageView: ImageView

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
        textView = findViewById(R.id.textPane2)
        wikipediaImageView = findViewById(R.id.imageView)
        wikipediaUrlButton = findViewById(R.id.openUrlButton)
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


    private fun setArtistInfo(artistUiState: MoreDetailsUiState){
        updateArtistInfo(artistUiState)
        updateImage(artistUiState)
        setWikipediaButton(artistUiState)
    }

    private fun updateArtistInfo(artistUiState: MoreDetailsUiState) {
        runOnUiThread {
            textView.text = artistUiState.artistInfo
        }
    }

    private fun updateImage(artistUiState: MoreDetailsUiState) {
        runOnUiThread {
            imageLoader.loadImageIntoView(artistUiState.artistImageUrl, wikipediaImageView)
        }
    }

    private fun setWikipediaButton(artistUiState: MoreDetailsUiState) {
        setOpenUrlButton(artistUiState)
    }
    private fun setIntent(artistUiState: MoreDetailsUiState):Intent{
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(artistUiState.artistUrl)
        return intent
    }

    private fun setOpenUrlButton(artistUiState: MoreDetailsUiState) {
        wikipediaUrlButton.setOnClickListener {
            val intent = setIntent(artistUiState)
            startActivity(intent)
        }
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }
}