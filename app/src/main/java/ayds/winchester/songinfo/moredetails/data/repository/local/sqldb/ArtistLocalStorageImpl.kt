package ayds.winchester.songinfo.moredetails.data.repository.local.sqldb

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ayds.winchester.songinfo.moredetails.data.repository.local.CardsRepository
import ayds.winchester.songinfo.moredetails.domain.entities.Card

class ArtistLocalStorageImpl (context: Context,
                              private val cursorToArtistMapper: CursorToArtistMapper,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION),
    CardsRepository {
    private val projection = arrayOf(
        ARTIST_COLUMN,
        INFO_COLUMN,
        SOURCE_COLUMN,
        URL_COLUMN,
        LOGO_COLUMN
    )
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            createArtistTable
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
    private fun createMapValues(cards: Collection<Card>, artistName: String): ContentValues {
        val values = ContentValues()
         cards.forEach { card ->
             values.put(ARTIST_COLUMN, artistName)
             values.put(INFO_COLUMN, card.description)
             values.put(SOURCE_COLUMN, card.source.ordinal)
             values.put(URL_COLUMN, card.infoURL)
             values.put(LOGO_COLUMN, card.sourceLogoURL)
         }
        return values
    }

    private fun getDataBaseWritable(): SQLiteDatabase {
        return writableDatabase
    }

    override fun saveCards(card: Collection<Card>, artistName: String) {
        val database = getDataBaseWritable()
        val values = createMapValues(card, artistName)
        database.insert(ARTISTS_TABLE, null, values)
    }

    private fun getDataBaseReadable(): SQLiteDatabase {
        return readableDatabase
    }

    override fun getCards(artistName: String): List<Card> {
        val selection = "$ARTIST_COLUMN = ?"
        val selectionArgs = arrayOf(artistName)
        val sortOrder = "$ARTIST_COLUMN DESC"
        val cursor = getDataBaseReadable().query(
            ARTISTS_TABLE,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )

        return cursorToArtistMapper.map(cursor)
    }
}