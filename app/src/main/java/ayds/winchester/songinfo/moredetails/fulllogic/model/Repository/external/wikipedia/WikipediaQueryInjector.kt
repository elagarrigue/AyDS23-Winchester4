package ayds.winchester.songinfo.moredetails.fulllogic.model.repository.external.wikipedia

import WikipediaServiceImpl
import ayds.winchester.songinfo.home.model.repository.external.spotify.WikipediaService
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object WikipediaQueryInjector {
    private const val BASE_URL = "https://en.wikipedia.org/w/"
    private val wikipediaAPIRetrofit = Retrofit.Builder()
                                               .baseUrl(BASE_URL)
                                               .addConverterFactory(ScalarsConverterFactory.create())
                                               .build()
    private val wikipediaAPI = wikipediaAPIRetrofit.create(WikipediaAPI::class.java)
    private val wikipediaToArtistResolver: WikipediaToArtistResolver = JsonToArtistResolver()


    val wikipediaService: WikipediaService = WikipediaServiceImpl(
        wikipediaAPI,
        wikipediaToArtistResolver
    )

}