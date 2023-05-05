package ayds.winchester.songinfo.moredetails.fulllogic.model.repository.external.wikipedia

import com.google.gson.Gson
import com.google.gson.JsonObject


interface WikipediaToArtistResolver {
    fun getArtistFromExternalData(serviceData: String?): Artist.ArtistInfo?
}

private const val QUERY = "query"
private const val SEARCH = "search"
private const val ID = "id"
private const val NAME = "name"
private const val BASE_WIKI_URL = "https://en.wikipedia.org/?curid="


internal class JsonToArtistResolver : WikipediaToArtistResolver{

    override fun getArtistFromExternalData(serviceData: String?): Artist.ArtistInfo? =
        try {
            serviceData?.getFirstItem()?.let { item ->
                Artist.ArtistInfo(
                    item.getId(),
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
            val tracks = jobj[QUERY].asJsonObject
            val items = tracks[SEARCH].asJsonArray
            return items[0].asJsonObject
        }

        private fun JsonObject.getArtistInfo() = this["snippet"].asString

        private fun JsonObject.getWikipediaUrl() = BASE_WIKI_URL + this["pageid"]

        private fun JsonObject.getId() = this[ID].asString

        private fun JsonObject.getArtistName() =  this[NAME].asString

}