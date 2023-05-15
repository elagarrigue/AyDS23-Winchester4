package ayds.winchester.songinfo.moredetails.data.repository.external.wikipedia

import ayds.winchester.songinfo.moredetails.domain.entities.Artist.ArtistInfo
import ayds.winchester.songinfo.moredetails.domain.entities.BASE_WIKI_URL
import com.google.gson.Gson
import com.google.gson.JsonObject


interface WikipediaToArtistResolver {
    fun getArtistFromExternalData(serviceData: String?, artistName: String): ArtistInfo?
}

private const val QUERY = "query"
private const val SEARCH = "search"
private const val SNIPPET = "snippet"
private const val PAGEID = "pageid"


class JsonToArtistResolver : WikipediaToArtistResolver {

    override fun getArtistFromExternalData(serviceData: String?, artistName: String): ArtistInfo? =
        try {
            serviceData?.getFirstItem()?.let { item ->
                ArtistInfo(
                    artistName,
                    item.getArtistInfo(),
                    BASE_WIKI_URL + item.getWikipediaUrl()
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

        private fun JsonObject.getArtistInfo() = this[SNIPPET].asString.replace("\\n", "\n")

        private fun JsonObject.getWikipediaUrl() = this[PAGEID]


}