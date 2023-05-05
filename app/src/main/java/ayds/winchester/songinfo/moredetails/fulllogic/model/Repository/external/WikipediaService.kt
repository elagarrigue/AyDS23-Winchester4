package ayds.winchester.songinfo.home.model.repository.external.spotify


interface WikipediaService {

    fun getArtist(title: String): Artist.ArtistInfo?
}