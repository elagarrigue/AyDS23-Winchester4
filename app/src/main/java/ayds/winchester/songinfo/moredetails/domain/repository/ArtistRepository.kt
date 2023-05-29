package ayds.winchester.songinfo.moredetails.domain.repository

import ayds.winchester.songinfo.moredetails.domain.entities.Card

interface ArtistRepository {
    fun getArtistByName(artistName: String): Collection<Card>?
}