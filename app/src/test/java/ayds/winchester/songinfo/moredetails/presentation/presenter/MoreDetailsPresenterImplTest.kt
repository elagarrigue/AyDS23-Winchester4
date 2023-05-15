package ayds.winchester.songinfo.moredetails.presentation.presenter


import ayds.winchester.songinfo.moredetails.domain.entities.Artist
import ayds.winchester.songinfo.moredetails.domain.repository.ArtistRepository
import ayds.winchester.songinfo.moredetails.presentation.MoreDetailsUiState
import io.mockk.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class MoreDetailsPresenterImplTest {

    private lateinit var presenter: MoreDetailsPresenterImpl
    private val repository: ArtistRepository = mockk()
    private val artistDescriptionHelper: ArtistDescriptionHelper = mockk()

    @Before
    fun setUp() {
        presenter = MoreDetailsPresenterImpl(repository, artistDescriptionHelper)
    }

    @Test
    fun searchArtist_setsCorrectUiState() {
        // Arrange
        val artistName = "Artist Name"
        val artist = Artist.ArtistInfo("Artist Name", "Artist Description", "Artist Wikipedia URL")
        val expectedUiState = MoreDetailsUiState("Artist Image url","Artist Description", "Artist Wikipedia URL")
        coEvery { repository.getArtistByName(artistName) } returns artist
        every { artistDescriptionHelper.getArtistDescriptionText(artist) } returns "Artist Description"

        // Act
        presenter.searchArtist(artistName)

        // Assert
        val capturedState = slot<MoreDetailsUiState>()
        verify { presenter.artistObservable.notify(capture(capturedState)) }
        assertEquals(expectedUiState, capturedState.captured)
    }
}