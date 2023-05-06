package ayds.winchester.songinfo.moredetails.fulllogic.view


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ayds.observer.Observable
import ayds.observer.Subject
import ayds.winchester.songinfo.R
import ayds.winchester.songinfo.home.view.HomeUiState.Companion.DEFAULT_IMAGE
import ayds.winchester.songinfo.moredetails.fulllogic.OtherInfoWindow
import ayds.winchester.songinfo.moredetails.fulllogic.model.MoreDetailsModel
import ayds.winchester.songinfo.moredetails.fulllogic.model.MoreDetailsModelInjector
import ayds.winchester.songinfo.utils.UtilsInjector
import ayds.winchester.songinfo.utils.navigation.NavigationUtils
import ayds.winchester.songinfo.utils.view.ImageLoader
import ayds.winchester.songinfo.moredetails.fulllogic.model.entities.Artist.ArtistInfo
import ayds.winchester.songinfo.moredetails.fulllogic.model.entities.Artist.EmptyArtist
import ayds.winchester.songinfo.moredetails.fulllogic.model.entities.Artist
import ayds.winchester.songinfo.moredetails.fulllogic.view.MoreDetailsUiState.Companion.NO_RESULTS

interface MoreDetailsView {
    val uiEventObservable: Observable<MoreDetailsUiEvent>
    val uiState: MoreDetailsUiState

    fun showMoreDetails(artistName: String)
    fun openExternalLink(url: String)
}


class MoreDetailsViewActivity : AppCompatActivity(), MoreDetailsView {

    private val onActionSubject = Subject<MoreDetailsUiEvent>()
    private lateinit var moreDetailsModel: MoreDetailsModel
    private val artistDescriptionHelper: ArtistDescriptionHelper = MoreDetailsViewInjector.artistDescriptionHelper
    private val imageLoader: ImageLoader = UtilsInjector.imageLoader
    private val navigationUtils: NavigationUtils = UtilsInjector.navigationUtils

    private lateinit var openArtistButton: Button
    private lateinit var artistDescriptionTextView: TextView
    private lateinit var wikipediaPosterImageView: ImageView

    override val uiEventObservable: Observable<MoreDetailsUiEvent> = onActionSubject
    override var uiState: MoreDetailsUiState = MoreDetailsUiState()

    override fun showMoreDetails(artistName: String) {
        val intent = Intent(this, OtherInfoWindow::class.java)
        intent.putExtra(ARTIST_NAME_EXTRA, artistName)
        startActivity(intent)
    }

    override fun openExternalLink(url: String) {
        navigationUtils.openExternalUrl(this, url)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initModule()
        initProperties()
        initListeners()
        initObservers()
        updateSongImage()
    }

    private fun initModule() {
        MoreDetailsViewInjector.init(this)
        moreDetailsModel = MoreDetailsModelInjector.getMoreDetailsModel()
    }

    private fun initProperties() {
        openArtistButton = findViewById(R.id.openArtistButton)
        artistDescriptionTextView = findViewById(R.id.artistDescriptionTextView)
        wikipediaPosterImageView = findViewById(R.id.wikipediaPosterImageView)
    }

    private fun initListeners() {
        openArtistButton.setOnClickListener { notifyOpenSongAction() }
    }

    private fun notifyOpenSongAction() {
        onActionSubject.notify(MoreDetailsUiEvent.OpenArtistUrl)
    }

    private fun initObservers() {
        moreDetailsModel.artistObservable
            .subscribe { value -> updateArtistInfo(value) }
    }


    private fun updateArtistInfo(artist: Artist) {
        updateUiState(artist)
        updateSongDescription()
        updateSongImage()
        updateMoreDetailsState()
    }

    private fun updateUiState(artist: Artist) {
        when (artist) {
            is ArtistInfo -> updateArtistUiState(artist)
            EmptyArtist -> updateNoResultsUiState()
        }
    }

    private fun updateArtistUiState(artist: ArtistInfo) {
        uiState = uiState.copy(
            artistImageUrl = DEFAULT_IMAGE,
            artistInfo = artistDescriptionHelper.getArtistDescriptionText(artist),
            artistUrl = artist.wikipediaUrl,
            actionsEnabled = true
        )
    }

    private fun updateNoResultsUiState() {
        uiState = uiState.copy(
            artistImageUrl = DEFAULT_IMAGE,
            artistInfo = NO_RESULTS,
            artistUrl = "",
            actionsEnabled = false
        )
    }

    private fun updateSongDescription() {
        runOnUiThread {
            artistDescriptionTextView.text = uiState.artistInfo
        }
    }

    private fun updateSongImage() {
        runOnUiThread {
            imageLoader.loadImageIntoView(uiState.artistImageUrl, wikipediaPosterImageView)
        }
    }

    private fun updateMoreDetailsState() {
        enableActions(uiState.actionsEnabled)
    }

    private fun enableActions(enable: Boolean) {
        runOnUiThread {
            openArtistButton.isEnabled = enable
        }
    }
    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }
}