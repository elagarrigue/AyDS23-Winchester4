package ayds.winchester.songinfo.home.model.repository.external.spotify

import ayds.winchester.songinfo.moredetails.fulllogic.model.entities.Artist.ArtistInfo


interface WikipediaService {
    fun getArtist(title: String): ArtistInfo?
}