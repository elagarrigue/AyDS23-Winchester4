package ayds.winchester.songinfo.moredetails.presentation.view

import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ayds.observer.Observable
import ayds.observer.Subject
import ayds.winchester.songinfo.R
import ayds.winchester.songinfo.moredetails.dependencyinjector.MoreDetailsViewInjector
import ayds.winchester.songinfo.moredetails.domain.MoreDetailsModel
import ayds.winchester.songinfo.moredetails.dependencyinjector.MoreDetailsModelInjector
import ayds.winchester.songinfo.moredetails.domain.entities.Artist
import ayds.winchester.songinfo.moredetails.domain.entities.Artist.ArtistInfo
import ayds.winchester.songinfo.moredetails.domain.entities.Artist.EmptyArtist
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

    private val onActionSubject = Subject<MoreDetailsUiEvent>()
    override val uiEventObservable: Observable<MoreDetailsUiEvent> = onActionSubject
    private val navigationUtils: NavigationUtils = UtilsInjector.navigationUtils
    override var uiState: MoreDetailsUiState = MoreDetailsUiState()
    private lateinit var moreDetailsModel: MoreDetailsModel
    private val artistDescriptionHelper: ArtistDescriptionHelper =
        MoreDetailsViewInjector.artistDescriptionHelper


    override fun openExternalLink(url: String) {
        navigationUtils.openExternalUrl(this, url)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAll()
    }

    private fun initAll(){
        initModule()
        initProperties()
        initListeners()
        initObservers()
        setArtistName()
        notifySearchAction()
    }

    private fun setArtistName() {
        uiState = uiState.copy( artistName = intent.getStringExtra(ARTIST_NAME_EXTRA).toString())
    }

    private fun notifySearchAction() {
        onActionSubject.notify(MoreDetailsUiEvent.SearchArtist)
    }

    private fun initModule() {
        MoreDetailsViewInjector.init(this)
        moreDetailsModel = MoreDetailsModelInjector.getMoreDetailsModel()
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

    private fun initObservers() {
        moreDetailsModel.artistObservable
            .subscribe { value -> updateArtistInfo(value) }
    }


    private fun updateArtistInfo(artist: Artist) {
        updateUiState(artist)
        updateArtistInfo()
        updateImage()
    }

    private fun updateUiState(artist: Artist) {
        when (artist) {
            is ArtistInfo -> updateArtistUiState(artist)
            EmptyArtist -> noUpdateArtistUiState()
        }
    }
    private fun updateArtistUiState(artist: ArtistInfo) {
        uiState = uiState.copy(
            artistImageUrl = MoreDetailsUiState.DEFAULT_IMAGE,
            artistInfo = artistDescriptionHelper.getArtistDescriptionText(artist),
            artistUrl = artist.wikipediaUrl,
        )
    }
    private fun noUpdateArtistUiState() {
        uiState = uiState.copy(
            artistImageUrl = MoreDetailsUiState.DEFAULT_IMAGE,
            artistInfo = artistDescriptionHelper.getArtistDescriptionText(EmptyArtist),
            artistUrl = "",
        )
    }
    private fun updateArtistInfo() {
        runOnUiThread {
            artistInfoTextView.text = Html.fromHtml(uiState.artistInfo)
        }
    }

    private fun updateImage() {
        runOnUiThread {
            imageLoader.loadImageIntoView(uiState.artistImageUrl, wikipediaImageView)
        }
    }


    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }
}