package ayds.winchester.songinfo.moredetails.data.external.wikipedia

import ayds.winchester.songinfo.moredetails.domain.entities.Artist.ArtistInfo
import com.google.gson.Gson
import com.google.gson.JsonObject


interface WikipediaToArtistResolver {
    fun getArtistFromExternalData(serviceData: String?): ArtistInfo?
}

private const val QUERY = "query"
private const val SEARCH = "search"
private const val SNIPPET = "snippet"
private const val PAGEID = "pageid"
private const val NAME = "title"
private const val BASE_WIKI_URL = "https://en.wikipedia.org/?curid="


class JsonToArtistResolver : WikipediaToArtistResolver {

    override fun getArtistFromExternalData(serviceData: String?): ArtistInfo? =
        try {
            serviceData?.getFirstItem()?.let { item ->
                ArtistInfo(
                    item.getArtistName(),
                    item.getArtistInfo(),
                    item.getWikipediaUrl()
                )
            }
        } catch (e: Exception) {
            null
        }

        private fun String?.getFirstItem(): JsonObject {
            val jobj = Gson().fromJson(this, JsonObject::class.java)
            val query = jobj[QUERY].asJsonObject
            val items = query[SEARCH].asJsonArray
            return items[0].asJsonObject
        }

        private fun JsonObject.getArtistInfo() = this[SNIPPET].asString

        private fun JsonObject.getWikipediaUrl() = BASE_WIKI_URL + this[PAGEID]

        private fun JsonObject.getArtistName() =  this[NAME].asString

}