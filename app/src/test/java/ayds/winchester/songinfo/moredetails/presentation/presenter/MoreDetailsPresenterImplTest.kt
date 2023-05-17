package ayds.winchester.songinfo.moredetails.presentation.presenter

import ayds.observer.Observable
import ayds.winchester.songinfo.moredetails.domain.entities.Artist
import ayds.winchester.songinfo.moredetails.domain.repository.ArtistRepository
import ayds.winchester.songinfo.moredetails.presentation.MoreDetailsUiState
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class MoreDetailsPresenterImplTest {

    private val repository: ArtistRepository = mockk()
    private val artistDescriptionHelper: ArtistDescriptionHelper = mockk()

    private val presenter: MoreDetailsPresenter by lazy {
        MoreDetailsPresenterImpl(repository, artistDescriptionHelper)
    }

    @Test
    fun searchArtistTest() {
        val artistName = "ArtistName"
        val artist = Artist.ArtistInfo("ArtistName", "Description", "https://en.wikipedia.org/?curid=")

        val uiStateObservable: Observable<MoreDetailsUiState> = presenter.artistObservable
        val uiStateTester: (MoreDetailsUiState) -> Unit = mockk(relaxed = true)
        uiStateObservable.subscribe(uiStateTester)

        every { repository.getArtistByName(artistName) } returns artist
        every { artistDescriptionHelper.getArtistDescriptionText(artist) } returns "Description"

        presenter.searchArtist(artistName)

        verify { repository.getArtistByName(artistName) }

        verify { artistDescriptionHelper.getArtistDescriptionText(artist) }

        val expectedUiState = MoreDetailsUiState(
            artistInfo = "Description",
            artistUrl = "https://en.wikipedia.org/?curid="
        )
        verify { uiStateTester(expectedUiState) }
    }
}