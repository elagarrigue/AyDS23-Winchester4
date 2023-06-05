package ayds.winchester.songinfo.moredetails.data.repository.external


import ayds.winchester.songinfo.moredetails.domain.entities.Card

interface CardProxy {
    fun getCard(artistName: String): Card?
}