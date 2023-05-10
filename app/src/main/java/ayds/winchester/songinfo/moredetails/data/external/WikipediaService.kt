package ayds.winchester.songinfo.moredetails.data.external

import ayds.winchester.songinfo.moredetails.domain.entities.Artist.ArtistInfo


interface WikipediaService {
    fun getArtist(title: String): ArtistInfo?
}