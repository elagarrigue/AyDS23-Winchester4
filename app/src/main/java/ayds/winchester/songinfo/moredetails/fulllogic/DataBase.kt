package ayds.winchester.songinfo.moredetails.fulllogic

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

private const val ARTIST="artist"
private const val ARTISTS="artists"
private const val INFO="info"
private const val SOURCE="source"
private const val ID="id"
private const val SOURCENUMONE=1
private const val NAMEDATABASE="dictionary.db"
private const val CREATETABLE="create table artists (id INTEGER PRIMARY KEY AUTOINCREMENT, artist string, info string, source integer)"
private const val VERSIONDATABASE=1

class DataBase(context: Context?) : SQLiteOpenHelper(context, NAMEDATABASE, null, VERSIONDATABASE) {
    private val projection = arrayOf(
        ID,
        ARTIST,
        INFO
    )
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            CREATETABLE
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    private fun createMapValues(artist: String, info: String):ContentValues {
        val values = ContentValues()
        values.put(ARTIST, artist)
        values.put(INFO, info)
        values.put(SOURCE, SOURCENUMONE)
        return values
    }

    private fun getDataBaseWritable(): SQLiteDatabase {
        return writableDatabase
    }

    fun saveArtist( artist: String, info: String) {
        val database = getDataBaseWritable()
        val values = createMapValues(artist, info)
        database.insert(ARTISTS, null, values)
    }

    private fun getDataBaseReadable(): SQLiteDatabase {
        return readableDatabase
    }

    private fun createCursor(
        dataBase: SQLiteDatabase,
        artist: String
    ): Cursor {
        val selection = "$ARTIST = ?"
        val selectionArgs = arrayOf(artist)
        val sortOrder = "$ARTIST DESC"
        return dataBase.query(
            ARTISTS,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )
    }

    private fun getItems(cursor: Cursor):String {
        cursor.moveToNext()
        val info = cursor.getString(cursor.getColumnIndexOrThrow(INFO))
        cursor.close()
        return info
    }

    fun getArtistInfoFromDataBase( artist: String): String? {
        val database = getDataBaseReadable()
        val cursor = createCursor(database, artist)
        val item: String = getItems(cursor)
        return item
    }
}