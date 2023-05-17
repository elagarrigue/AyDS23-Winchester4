package ayds.winchester.songinfo.moredetails.presentation.presenter


import ayds.winchester.songinfo.moredetails.domain.entities.Artist

import junit.framework.TestCase.assertEquals
import org.junit.Test


internal class ArtistDescriptionHelperImplTest{
    private val artistDescriptionHelper by lazy { ArtistDescriptionHelperImpl() }

    @Test
    fun `given the artist description text which is locally store with the marker `() {
        // Arrange
        val artistName = "Linkin Park"
        val artistInfo = "Linkin Park is an American rock band from Agoura Hills, California."
        val artist = Artist.ArtistInfo(artistName, artistInfo, "url", true)

        // Act
        val result = artistDescriptionHelper.getArtistDescriptionText(artist)

        // Assert
        val expected = "[*]\n" +
            " <html><div width=400><font face=\"arial\"><b>LINKIN PARK</b> is an American rock band from Agoura Hills, California.</font></div></html>\n"
        assertEquals(expected, result)
    }
    @Test
    fun `given the artist description text which isn't locally store without the marker`() {
        // Arrange
        val artistName = "Linkin Park"
        val artistInfo = "Linkin Park is an American rock band from Agoura Hills, California."
        val artist = Artist.ArtistInfo(artistName, artistInfo, "url", false)

        // Act
        val result = artistDescriptionHelper.getArtistDescriptionText(artist)

        // Assert
        val expected = "\n" +
                       " <html><div width=400><font face=\"arial\"><b>LINKIN PARK</b> is an American rock band from Agoura Hills, California.</font></div></html>\n"
        assertEquals(expected, result)
    }

    @Test
    fun `given the artist no results message`() {
        // Arrange
        val artist = Artist.EmptyArtist()

        // Act
        val result = artistDescriptionHelper.getArtistDescriptionText(artist)

        // Assert
        val expected = "No results"
        assertEquals(expected, result)
    }

}