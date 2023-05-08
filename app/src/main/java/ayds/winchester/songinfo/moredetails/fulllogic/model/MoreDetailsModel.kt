package ayds.winchester.songinfo.moredetails.fulllogic.model


import ayds.winchester.songinfo.moredetails.fulllogic.model.entities.Artist
import ayds.observer.Observable
import ayds.observer.Subject
import ayds.winchester.songinfo.moredetails.fulllogic.model.repository.ArtistRepository

interface  MoreDetailsModel {

    val artistObservable: Observable<Artist>

    fun searchArtist(artistName: String)

    fun getArtistById(id: String): Artist
}

internal class MoreDetailsModelImpl(private val repository: ArtistRepository) : MoreDetailsModel {

    override val artistObservable = Subject<Artist>()

    override fun searchArtist(artistName: String) {
        repository.getArtistByName(artistName).let {
            artistObservable.notify(it)
        }
    }

    override fun getArtistById(id: String): Artist = repository.getArtistById(id)
}