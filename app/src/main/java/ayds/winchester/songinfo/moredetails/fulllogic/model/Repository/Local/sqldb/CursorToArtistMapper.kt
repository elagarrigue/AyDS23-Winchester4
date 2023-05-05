package ayds.winchester.songinfo.moredetails.fulllogic.model.Repository.Local.sqldb

import android.database.Cursor
import java.sql.SQLException

interface CursorToArtistMapper {
    fun map(cursor: Cursor): Artist.ArtistInfo?
}

internal class CursorToArtistMapperImpl : CursorToArtistMapper {

    override fun map(cursor: Cursor): Artist.ArtistInfo? =
        try {
            with(cursor) {
                if (moveToNext()) {
                    Artist.ArtistInfo(
                        id = getString(getColumnIndexOrThrow(ID_COLUMN)),
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