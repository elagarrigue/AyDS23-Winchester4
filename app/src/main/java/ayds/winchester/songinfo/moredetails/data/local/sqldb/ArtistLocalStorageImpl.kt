package ayds.winchester.songinfo.moredetails.data.local.sqldb

import ayds.winchester.songinfo.moredetails.domain.entities.Artist.ArtistInfo
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ayds.winchester.songinfo.moredetails.data.local.ArtistLocalStorage


private const val DATABASE_VERSION = 1
private const val DATABASE_NAME="dictionary.db"

class ArtistLocalStorageImpl (context: Context,
                              private val cursorToArtistMapper: CursorToArtistMapper,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION),
    ArtistLocalStorage {
    private val projection = arrayOf(
        ARTIST_COLUMN,
        INFO_COLUMN,
        WIKIPEDIA_URL_COLUMN
    )
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            createArtistTable
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
    private fun createMapValues(artist: ArtistInfo): ContentValues {
        val values = ContentValues()
        values.put(ARTIST_COLUMN, artist.artistName)
        values.put(INFO_COLUMN, artist.artistInfo)
        values.put(WIKIPEDIA_URL_COLUMN, artist.wikipediaUrl)
        return values
    }

    private fun getDataBaseWritable(): SQLiteDatabase {
        return writableDatabase
    }

    override fun saveArtist( artist: ArtistInfo) {
        val database = getDataBaseWritable()
        val values = createMapValues(artist)
        database.insert(ARTISTS_TABLE, null, values)
    }

    private fun getDataBaseReadable(): SQLiteDatabase {
        return readableDatabase
    }

    override fun getArtistInfoFromDataBase( artistName: String): ArtistInfo? {
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