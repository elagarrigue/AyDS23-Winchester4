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
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            CREATETABLE
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    private fun createMapValues(values: ContentValues, artist: String, info: String) {
        values.put(ARTIST, artist)
        values.put(INFO, info)
        values.put(SOURCE, SOURCENUMONE)
    }

    private fun getDataBaseWritable(): SQLiteDatabase {
        return writableDatabase
    }

    fun saveArtist( artist: String, info: String) {
        val database = getDataBaseWritable()
        val values = ContentValues()
        createMapValues(values, artist, info)
        database.insert(ARTISTS, null, values)
    }

    private fun getDataBaseReadable(): SQLiteDatabase {
        return readableDatabase
    }

    private fun createCursor(
        dataBase: SQLiteDatabase,
        artist: String
    ): Cursor {
        val projection = arrayOf(
            ID,
            ARTIST,
            INFO
        )
        val selection = ARTIST+ " = ?"
        val selectionArgs = arrayOf(artist)
        val sortOrder = ARTIST+" DESC"
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

    private fun addItems(cursor: Cursor, items: MutableList<String>) {
        while (cursor.moveToNext()) {
            val info = cursor.getString(cursor.getColumnIndexOrThrow(INFO))
            items.add(info)
        }
        cursor.close()
    }

    fun getInfo( artist: String): String? {
        val database = getDataBaseReadable()
        val cursor = createCursor(database, artist)
        val items: MutableList<String> = ArrayList()
        addItems(cursor, items)
        return if (items.isEmpty()) null else items[0]
    }
}