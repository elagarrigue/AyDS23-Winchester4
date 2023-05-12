package ayds.winchester.songinfo.moredetails.data.repository.external

import ayds.winchester.songinfo.moredetails.domain.entities.Artist.ArtistInfo


interface WikipediaService {
    fun getArtist(artistName: String): ArtistInfo?
}