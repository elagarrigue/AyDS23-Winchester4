package ayds.winchester.songinfo.moredetails.fulllogic.model.repository.external

import ayds.winchester.songinfo.moredetails.fulllogic.model.entities.Artist.ArtistInfo


interface WikipediaService {
    fun getArtist(title: String): ArtistInfo?
}