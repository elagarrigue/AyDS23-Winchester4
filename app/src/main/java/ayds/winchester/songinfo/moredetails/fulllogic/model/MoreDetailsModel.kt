package ayds.winchester.songinfo.moredetails.fulllogic.model


import ayds.winchester.songinfo.moredetails.fulllogic.model.entities.Artist
import ayds.winchester.songinfo.moredetails.fulllogic.model.entities.Artist.ArtistInfo
import ayds.observer.Observable
import ayds.observer.Subject
import ayds.winchester.songinfo.moredetails.fulllogic.model.repository.ArtistRepository

interface  MoreDetailsModel {

    val artistObservable: Observable<Artist>

    fun searchArtist(artistInfo: ArtistInfo)

    fun getArtistById(id: String): Artist
}

internal class MoreDetailsModelImpl(private val repository: ArtistRepository) : MoreDetailsModel {

    override val artistObservable = Subject<Artist>()

    override fun searchArtist(artistInfo: ArtistInfo) {
        repository.getArtistByName(artistInfo).let {
            artistObservable.notify(it)
        }
    }

    override fun getArtistById(id: String): Artist = repository.getArtistById(id)
}