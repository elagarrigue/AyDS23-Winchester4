package ayds.winchester.songinfo.moredetails.data.repository


import ayds.winchester.artistinfo.external.WikipediaService
import ayds.winchester.songinfo.moredetails.data.repository.local.ArtistLocalStorage
import ayds.winchester.songinfo.moredetails.domain.entities.Artist
import ayds.winchester.songinfo.moredetails.domain.repository.ArtistRepository
import io.mockk.MockKStubScope
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Test

 class ArtistRepositoryImplTest {
     private val artistLocalStorage: ArtistLocalStorage = mockk(relaxUnitFun = true)
     private val artistWikipediaService: WikipediaService = mockk(relaxUnitFun = true)

     private val artistRepositoryImpl: ArtistRepository by lazy {
         ArtistRepositoryImpl(artistLocalStorage, artistWikipediaService)
     }

     @Test
     fun `given an artist who is in database`() {
         val artistName = "The Beatles"
         val artistFromLocalStorage =
             Artist.ArtistInfo("The Beatles", "British rock band formed in Liverpool","url", true)
         every { artistLocalStorage.getArtistInfoFromDataBase(artistName) } returns artistFromLocalStorage

         val result = artistRepositoryImpl.getCards(artistName)

         verify(exactly = 1) { artistLocalStorage.getArtistInfoFromDataBase(artistName) }
         assertEquals(artistFromLocalStorage, result)
     }
     @Test
     fun `given an artist who isn't in database and isn't in wikipedia`() {
         val artistName = "Unknown Artist"
         every { artistLocalStorage.getArtistInfoFromDataBase(artistName) } returns null
         every { artistWikipediaService.getArtist(artistName) } returns null

         val result = artistRepositoryImpl.getCards(artistName)

         verify(exactly = 1) { artistLocalStorage.getArtistInfoFromDataBase(artistName) }
         verify(exactly = 1) { artistWikipediaService.getArtist(artistName) }

         assertEquals(Artist.EmptyArtist(), result)
     }
     @Test
     fun `given an artist who isn't in database and is in wikipedia`() {
         val artistName = "The Rolling Stones"
         val artistFromWikipedia =
             Artist.ArtistInfo("The Rolling Stones", "English rock band formed in London","url", false)
         every { artistLocalStorage.getArtistInfoFromDataBase(artistName) } returns null
         every { artistWikipediaService.getArtist(artistName) } returns artistFromWikipedia

         val result = artistRepositoryImpl.getCards(artistName)

         verify(exactly = 1) { artistLocalStorage.getArtistInfoFromDataBase(artistName) }
         verify(exactly = 1) { artistWikipediaService.getArtist(artistName) }
         verify(exactly = 1) { artistLocalStorage.saveArtist(artistFromWikipedia) }
         assertEquals(artistFromWikipedia, result)
     }
 }

private infix fun <T, B> MockKStubScope<T, B>.returns(artistFromWikipedia: Artist.ArtistInfo) {
    TODO("Not yet implemented")
}
