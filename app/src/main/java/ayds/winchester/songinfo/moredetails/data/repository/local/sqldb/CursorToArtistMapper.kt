package ayds.winchester.songinfo.moredetails.data.repository.local.sqldb

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.SQLException
import ayds.winchester.songinfo.moredetails.domain.entities.Card

interface CursorToArtistMapper {
    fun map(cursor: Cursor): Collection<Card>?
}

class CursorToArtistMapperImpl : CursorToArtistMapper {

    @SuppressLint("SuspiciousIndentation")
    override fun map(cursor: Cursor): Collection<Card>? {
        val cards : MutableCollection<Card> = mutableListOf()
            try {
                with(cursor) {
                    while (cursor.moveToNext()) {
                        cards.add(
                            Card(
                                description = getString(cursor.getColumnIndexOrThrow(INFO_COLUMN)),
                                infoURL = getString(cursor.getColumnIndexOrThrow(URL_COLUMN)),
                                source = getString(cursor.getColumnIndexOrThrow(SOURCE_COLUMN)),
                                sourceLogoURL = getString(cursor.getColumnIndexOrThrow(LOGO_COLUMN))
                            )
                        )
                    }
                }
            } catch (e: SQLException)
            {
                e.printStackTrace()
                null
            }

        return cards
    }
}