package ayds.winchester.songinfo.moredetails.data.repository.local.sqldb

import android.database.Cursor
import ayds.winchester.songinfo.moredetails.domain.entities.Artist.ArtistInfo
import java.sql.SQLException

interface CursorToArtistMapper {
    fun map(cursor: Cursor): ArtistInfo?
}

class CursorToArtistMapperImpl : CursorToArtistMapper {

    override fun map(cursor: Cursor): ArtistInfo? =
        try {
            with(cursor) {
                if (moveToNext()) {
                    ArtistInfo(
                        artistName = getString(getColumnIndexOrThrow(ARTIST_COLUMN)),
                        artistInfo = getString(getColumnIndexOrThrow(INFO_COLUMN)),
                        wikipediaUrl = getString(getColumnIndexOrThrow(WIKIPEDIA_URL_COLUMN))
                    )
                } else {
                    null
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
            null
        }
}