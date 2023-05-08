package ayds.winchester.songinfo.moredetails.fulllogic.model.repository.external

import ayds.winchester.songinfo.moredetails.fulllogic.model.repository.external.wikipedia.WikipediaQueryInjector

object WikipediaInjector {

    val wikipediaService: WikipediaService = WikipediaQueryInjector.wikipediaService
}