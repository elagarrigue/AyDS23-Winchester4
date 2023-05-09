package ayds.winchester.songinfo.moredetails.fulllogic.model.repository.external.wikipedia


import ayds.winchester.songinfo.moredetails.fulllogic.model.repository.external.WikipediaService
import ayds.winchester.songinfo.moredetails.fulllogic.model.entities.Artist.ArtistInfo
import retrofit2.Response

class WikipediaServiceImpl(
    private val wikipediaAPI: WikipediaAPI,
    private val wikipediaToArtistResolver: WikipediaToArtistResolver,
) : WikipediaService {

    override fun getArtist(title: String): ArtistInfo? {
        val callResponse = getSongFromService(title)
        return wikipediaToArtistResolver.getArtistFromExternalData(callResponse.body())
    }

    private fun getSongFromService(query: String): Response<String> {
        return wikipediaAPI.getArtistInfo(query).execute()
    }
}