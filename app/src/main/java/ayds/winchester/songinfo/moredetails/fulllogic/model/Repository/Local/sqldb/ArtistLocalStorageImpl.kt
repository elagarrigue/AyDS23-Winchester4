package ayds.winchester.songinfo.moredetails.fulllogic.model.Repository.Local.sqldb

import Artist
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ayds.winchester.songinfo.moredetails.fulllogic.model.Repository.Local.ArtistLocalStorage


private const val DATABASE_VERSION = 1
private const val DATABASE_NAME="dictionary.db"

class ArtistLocalStorageImpl (context: Context,
                              private val cursorToArtistMapper: CursorToArtistMapper,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION),
    ArtistLocalStorage {
    private val projection = arrayOf(
        ID_COLUMN,
        ARTIST_COLUMN,
        INFO_COLUMN,
        WIKIPEDIA_URL_COLUMN
    )
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            createArtistTable
        )
    }
    private fun createMapValues(artist:Artist.ArtistInfo): ContentValues {
        val values = ContentValues()
        values.put(ARTIST_COLUMN, artist.artistName)
        values.put(INFO_COLUMN, artist.artistInfo)
        values.put(WIKIPEDIA_URL_COLUMN, artist.wikipediaUrl)
        return values
    }

    private fun getDataBaseWritable(): SQLiteDatabase {
        return writableDatabase
    }

    override fun saveArtist( artist:Artist.ArtistInfo) {
        val database = getDataBaseWritable()
        val values = createMapValues(artist)
        database.insert(ARTISTS_TABLE, null, values)
    }

    private fun getDataBaseReadable(): SQLiteDatabase {
        return readableDatabase
    }

    override fun getArtistInfoFromDataBase( artist: String): Artist.ArtistInfo? {
        val selection = "$ARTIST_COLUMN = ?"
        val selectionArgs = arrayOf(artist)
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
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
}