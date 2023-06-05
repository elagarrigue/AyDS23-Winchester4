package ayds.winchester.songinfo.moredetails.data.repository.local.sqldb

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.SQLException
import ayds.winchester.songinfo.moredetails.domain.entities.Card
import ayds.winchester.songinfo.moredetails.domain.entities.Source

interface CursorToArtistMapper {
    fun map(cursor: Cursor): List<Card>
}

class CursorToArtistMapperImpl : CursorToArtistMapper {

    @SuppressLint("SuspiciousIndentation")
    override fun map(cursor: Cursor): List<Card> {
        val cards : MutableList<Card> = mutableListOf()
            try {
                with(cursor) {
                    while (cursor.moveToNext()) {
                        cards.add(
                            Card(
                                description = getString(cursor.getColumnIndexOrThrow(INFO_COLUMN)),
                                infoURL = getString(cursor.getColumnIndexOrThrow(URL_COLUMN)),
                                source = getSource(cursor.getColumnIndexOrThrow(SOURCE_COLUMN)),
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
    private fun getSource(index: Int):Source{
        return Source.values()[index]
    }

}
