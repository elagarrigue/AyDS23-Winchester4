package ayds.winchester.songinfo.moredetails.data.external

import ayds.winchester.songinfo.moredetails.data.external.WikipediaService
import ayds.winchester.songinfo.moredetails.data.external.wikipedia.WikipediaQueryInjector

object WikipediaInjector {

    val wikipediaService: WikipediaService = WikipediaQueryInjector.wikipediaService
}