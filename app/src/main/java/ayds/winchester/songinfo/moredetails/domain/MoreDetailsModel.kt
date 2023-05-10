package ayds.winchester.songinfo.moredetails.domain

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.winchester.songinfo.moredetails.domain.entities.Artist
import ayds.winchester.songinfo.moredetails.domain.repository.ArtistRepository

interface  MoreDetailsModel {

    val artistObservable: Observable<Artist>

    fun searchArtist(artistName: String)

}

internal class MoreDetailsModelImpl(private val repository: ArtistRepository) : MoreDetailsModel {

    override val artistObservable = Subject<Artist>()

    override fun searchArtist(artistName: String) {
        repository.getArtistByName(artistName).let {
            artistObservable.notify(it)
        }
    }
}
