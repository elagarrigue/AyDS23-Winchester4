package ayds.winchester.songinfo.home.view

import ayds.winchester.songinfo.home.model.entities.Song
import ayds.winchester.songinfo.home.model.entities.Song.SpotifySong
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test

class SongDescriptionHelperTest {

    private val dateFactory: DateFactory = mockk()
    private val songDescriptionHelper by lazy { SongDescriptionHelperImpl(dateFactory) }

    @Test
    fun `given a local song it should return the description`() {
        val song: Song = SpotifySong(
            "id",
            "Plush",
            "Stone Temple Pilots",
            "Core",
            "1992-01-01",
            "year",
            "url",
            "url",
            true,
        )
        every { dateFactory.getDateConverted(song as SpotifySong).convertDate() } returns "1992(a leap year)"

        val result = songDescriptionHelper.getSongDescriptionText(song)

        val expected =
            "Song: Plush [*]\n" +
                    "Artist: Stone Temple Pilots\n" +
                    "Album: Core\n" +
                    "Release date: 1992(a leap year)"

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `given a non local song it should return the description`() {
        val song: Song = SpotifySong(
            "id",
            "Plush",
            "Stone Temple Pilots",
            "Core",
            "1992-01-01",
            "year",
            "url",
            "url",
            false,
        )
        every { dateFactory.getDateConverted(song as SpotifySong).convertDate() } returns "1992(a leap year)"

        val result = songDescriptionHelper.getSongDescriptionText(song)

        val expected =
            "Song: Plush \n" +
                    "Artist: Stone Temple Pilots\n" +
                    "Album: Core\n" +
                    "Release date: 1992(a leap year)"

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `given a non spotify song it should return the song not found description`() {
        val song: Song = mockk()

        val result = songDescriptionHelper.getSongDescriptionText(song)

        val expected = "Song not found"

        Assert.assertEquals(expected, result)
    }
}