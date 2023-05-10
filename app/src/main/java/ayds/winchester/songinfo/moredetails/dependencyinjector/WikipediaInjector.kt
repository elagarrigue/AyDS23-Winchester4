package ayds.winchester.songinfo.moredetails.dependencyinjector

import ayds.winchester.songinfo.moredetails.data.external.WikipediaService

object WikipediaInjector {

    val wikipediaService: WikipediaService = WikipediaQueryInjector.wikipediaService
}