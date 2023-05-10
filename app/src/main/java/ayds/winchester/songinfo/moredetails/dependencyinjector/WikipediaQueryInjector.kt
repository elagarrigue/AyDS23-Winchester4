package ayds.winchester.songinfo.moredetails.dependencyinjector

import ayds.winchester.songinfo.moredetails.data.external.WikipediaService
import ayds.winchester.songinfo.moredetails.data.external.wikipedia.JsonToArtistResolver
import ayds.winchester.songinfo.moredetails.data.external.wikipedia.WikipediaAPI
import ayds.winchester.songinfo.moredetails.data.external.wikipedia.WikipediaServiceImpl
import ayds.winchester.songinfo.moredetails.data.external.wikipedia.WikipediaToArtistResolver
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