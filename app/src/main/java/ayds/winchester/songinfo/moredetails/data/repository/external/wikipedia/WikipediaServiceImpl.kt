package ayds.winchester.songinfo.moredetails.data.repository.external.wikipedia


import ayds.winchester.songinfo.moredetails.data.repository.external.WikipediaService
import ayds.winchester.songinfo.moredetails.domain.entities.Artist.ArtistInfo
import retrofit2.Response

class WikipediaServiceImpl(
    private val wikipediaAPI: WikipediaAPI,
    private val wikipediaToArtistResolver: WikipediaToArtistResolver,
) : WikipediaService {

    override fun getArtist(artistName: String): ArtistInfo? {
        val callResponse = getArtistFromService(artistName)
        return wikipediaToArtistResolver.getArtistFromExternalData(callResponse.body(),artistName)
    }

    private fun getArtistFromService(artistName: String): Response<String> {
        return wikipediaAPI.getArtistInfo(artistName).execute()
    }
}