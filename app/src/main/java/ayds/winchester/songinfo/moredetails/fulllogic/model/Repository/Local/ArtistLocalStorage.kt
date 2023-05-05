package ayds.winchester.songinfo.moredetails.fulllogic.model.Repository.Local

internal interface ArtistLocalStorage {

    fun getArtistInfoFromDataBase( artist: String): Artist.ArtistInfo?
    fun saveArtist( artist:Artist.ArtistInfo)
}