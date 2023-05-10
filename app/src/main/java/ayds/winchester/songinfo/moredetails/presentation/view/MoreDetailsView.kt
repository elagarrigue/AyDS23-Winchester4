package ayds.winchester.songinfo.moredetails.presentation.view

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ayds.observer.Observable
import ayds.observer.Observer
import ayds.observer.Subject
import ayds.winchester.songinfo.R
import ayds.winchester.songinfo.moredetails.dependencyinjector.MoreDetailsInjector
import ayds.winchester.songinfo.moredetails.presentation.presenter.MoreDetailsPresenter
import ayds.winchester.songinfo.utils.UtilsInjector
import ayds.winchester.songinfo.utils.UtilsInjector.imageLoader
import ayds.winchester.songinfo.utils.navigation.NavigationUtils

interface MoreDetailsView {
    val uiEventObservable: Observable<MoreDetailsUiEvent>
    val uiState: MoreDetailsUiState
    fun openExternalLink(url: String)

}

class MoreDetailsViewActivity : AppCompatActivity(), MoreDetailsView {
    private lateinit var artistInfoTextView: TextView
    private lateinit var wikipediaUrlButton: Button
    private lateinit var wikipediaImageView: ImageView

    override var uiState: MoreDetailsUiState = MoreDetailsUiState()
    private val onActionSubject = Subject<MoreDetailsUiEvent>()
    override val uiEventObservable: Observable<MoreDetailsUiEvent> = onActionSubject
    private val navigationUtils: NavigationUtils = UtilsInjector.navigationUtils
    private lateinit var moreDetailsPresenter: MoreDetailsPresenter
    private lateinit var artistName: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAll()
    }

    private fun initAll() {
        initArtistName()
        initModule()
        initmoreDetailsPresenter()
        initProperties()
        initListeners()
        searchArtist()
        setEventObservable()
    }

    private fun initArtistName() {
        artistName = intent.getStringExtra(ARTIST_NAME_EXTRA).toString()
    }

    private fun initModule() {
        MoreDetailsInjector.initOnViewStarted(this)
    }

    private fun initmoreDetailsPresenter() {
        moreDetailsPresenter = MoreDetailsInjector.getMoreDetailsPresenter()

    }

    private fun initProperties() {
        setContentView(R.layout.activity_other_info)
        artistInfoTextView = findViewById(R.id.textPane2)
        wikipediaImageView = findViewById(R.id.imageView)
        wikipediaUrlButton = findViewById(R.id.openUrlButton)
    }

    private fun initListeners() {
        wikipediaUrlButton.setOnClickListener { notifyOpenArtistAction() }
    }

    private fun notifyOpenArtistAction() {
        onActionSubject.notify(MoreDetailsUiEvent.OpenArtistUrl)
    }

    private fun searchArtist() {
        // Warning: Never use Thread in android! Use coroutines
        Thread {
            setArtistInfo()
        }.start()
    }

    private fun setArtistInfo(){
        moreDetailsPresenter.searchArtist(artistName)
        getUiState()
        updateArtistInfo()
        updateImage()
    }

    private fun getUiState(){
        uiState = moreDetailsPresenter.getUiState()
    }

    private fun updateArtistInfo() {
        runOnUiThread {
            artistInfoTextView.text = uiState.artistInfo
        }
    }

    private fun updateImage() {
        runOnUiThread {
            imageLoader.loadImageIntoView(uiState.artistImageUrl, wikipediaImageView)
        }
    }

    private fun setEventObservable() {
        uiEventObservable.subscribe(observer)
    }

    private val observer: Observer<MoreDetailsUiEvent> =
        Observer { value ->
            when (value) {
                MoreDetailsUiEvent.OpenArtistUrl -> openArtistUrl()
            }
        }

    private fun openArtistUrl() {
        openExternalLink(uiState.artistUrl)
    }

    override fun openExternalLink(url: String) {
        navigationUtils.openExternalUrl(this, url)
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }
}