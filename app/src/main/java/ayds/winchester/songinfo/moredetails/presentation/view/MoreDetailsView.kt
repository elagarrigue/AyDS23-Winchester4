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
import ayds.winchester.songinfo.moredetails.domain.entities.Card
import ayds.winchester.songinfo.moredetails.presentation.MoreDetailsUiState
import ayds.winchester.songinfo.moredetails.presentation.presenter.MoreDetailsPresenter
import ayds.winchester.songinfo.utils.UtilsInjector.imageLoader

interface MoreDetailsView

private val noResults="No results"
class MoreDetailsViewActivity : AppCompatActivity(), MoreDetailsView {
    private lateinit var artistInfoTextViewCard1 : TextView
    private lateinit var urlButtonCard1: Button
    private lateinit var imageViewCard1 : ImageView

    private lateinit var artistInfoTextViewCard2: TextView
    private lateinit var urlButtonCard2 : Button
    private lateinit var imageViewCard2 : ImageView

    private lateinit var artistInfoTextViewCard3 : TextView
    private lateinit var urlButtonCard3: Button
    private lateinit var imageViewCard3 : ImageView

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
        artistInfoTextViewCard1  = findViewById(R.id.artistInfoTextPanel)
        imageViewCard1 = findViewById(R.id.imageView)
        urlButtonCard1 = findViewById(R.id.openUrlButton)

        artistInfoTextViewCard2  = findViewById(R.id.artistInfoTextPanel2)
        imageViewCard2 = findViewById(R.id.imageView2)
        urlButtonCard2 = findViewById(R.id.openUrlButton2)

        artistInfoTextViewCard3 = findViewById(R.id.artistInfoTextPanel3)
        imageViewCard3 = findViewById(R.id.imageView3)
        urlButtonCard3 = findViewById(R.id.openUrlButton3)
    }

    private fun initMoreDetailsPresenter() {
        moreDetailsPresenter = MoreDetailsInjector.getMoreDetailsPresenter()
        moreDetailsPresenter.searchArtist(initArtistName())
    }

    private fun initObservers() {
        moreDetailsPresenter.cardObservable
            .subscribe { value -> setArtistInfo(value) }
    }

    private fun initArtistName() = intent.getStringExtra(ARTIST_NAME_EXTRA).toString()


    private fun setArtistInfo(artistUiStateCollection: MoreDetailsUiState){
        runOnUiThread {
            setCardDescription(artistUiStateCollection)
        }
    }

    private fun setCardDescription(artistUiStateCollection: MoreDetailsUiState){
        if (artistUiStateCollection.cardList.isEmpty())
            updateTextCardNoResults()
        artistUiStateCollection.cardList.forEach { artistUiState ->
            updateAllArtist(artistUiState)
        }
    }

    private fun updateAllArtist(artistCard: Card){
        if (artistInfoTextViewCard1.text.equals(""))
            updateArtistInfoCard1(artistCard)
        else
        if (artistInfoTextViewCard2.text.equals(""))
            updateArtistInfoCard2(artistCard)
        else
        if (artistInfoTextViewCard3.text.equals(""))
            updateArtistInfoCard3(artistCard)
    }
    private fun updateArtistInfoCard1(artistUiState: Card){
        updateTextCard1(artistUiState)
        updateImageCard1(artistUiState)
        setCard1Button(artistUiState)
    }

    private fun updateTextCardNoResults() {
            artistInfoTextViewCard1.text = Html.fromHtml(noResults)

    }

    private fun updateTextCard1(artistUiState: Card) {
            artistInfoTextViewCard1.text = Html.fromHtml(artistUiState.description)

    }

    private fun updateImageCard1(artistUiState: Card) {
            imageLoader.loadImageIntoView(artistUiState.sourceLogoURL, imageViewCard1)

    }

    private fun setCard1Button(artistUiState: Card) {
        urlButtonCard1.setOnClickListener {
            setIntent(artistUiState)
        }
    }

    private fun updateArtistInfoCard2(artistUiState: Card){
        updateTextCard2(artistUiState)
        updateImageCard2(artistUiState)
        setLastCard2(artistUiState)

    }

    private fun updateTextCard2(artistUiState: Card) {
            artistInfoTextViewCard2.text = Html.fromHtml(artistUiState.description)

    }

    private fun updateImageCard2(artistUiState: Card) {
            imageLoader.loadImageIntoView(artistUiState.sourceLogoURL, imageViewCard2)

    }

    private fun setLastCard2(artistUiState: Card) {
        urlButtonCard2.setOnClickListener {
            setIntent(artistUiState)
        }
    }

    private fun updateArtistInfoCard3(artistUiState: Card){
        updateTextCard3(artistUiState)
        updateImageCard3(artistUiState)
        setCard3TimeButton(artistUiState)

    }

    private fun updateTextCard3(artistUiState: Card) {
            artistInfoTextViewCard3.text = Html.fromHtml(artistUiState.description)

    }

    private fun updateImageCard3(artistUiState: Card) {
            imageLoader.loadImageIntoView(artistUiState.sourceLogoURL, imageViewCard3)

    }

    private fun setCard3TimeButton(artistUiState: Card) {
        urlButtonCard3.setOnClickListener {
            setIntent(artistUiState)
        }
    }

    private fun setIntent(artistUiState: Card){
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(artistUiState.infoURL)
        startActivity(intent)
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }
}