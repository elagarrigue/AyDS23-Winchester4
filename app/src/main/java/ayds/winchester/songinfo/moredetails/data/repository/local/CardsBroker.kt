package ayds.winchester.songinfo.moredetails.data.repository.local

import ayds.winchester.songinfo.moredetails.data.repository.external.ProxyInterface
import ayds.winchester.songinfo.moredetails.domain.entities.Card

interface Broker {
    fun getCards(artistName: String): List<Card>
}

internal class BrokerImpl(private val listOfProxy: List<ProxyInterface>): Broker {
    override fun getCards(artistName: String): List<Card>{
        val cards = mutableListOf<Card>()
        for(proxy in listOfProxy){
            proxy.getCard(artistName)?.let { cards.add(it)}
        }
        return cards
    }
}
