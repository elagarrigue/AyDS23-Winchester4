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
class DataBase(context: Context?) : SQLiteOpenHelper(context, "dictionary.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "create table artists (id INTEGER PRIMARY KEY AUTOINCREMENT, artist string, info string, source integer)"
        )
        Log.i("DB", "DB created")
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}


    private fun createMapValues(values: ContentValues, artist: String, info: String) {
        val source = 1
        values.put(ARTIST, artist)
        values.put(INFO, info)
        values.put(SOURCE, source)
    }

    private fun getDataBaseWritable(): SQLiteDatabase {
        return this.writableDatabase
    }

    fun saveArtist( artist: String, info: String) {
        val database = getDataBaseWritable()
        val values = ContentValues()
        createMapValues(values, artist, info)
        database.insert(ARTISTS, null, values)
    }

    private fun getDataBaseReadable(): SQLiteDatabase {
        return this.readableDatabase
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
        val selection = "artist  = ?"
        val selectionArgs = arrayOf(artist)
        val sortOrder = "artist DESC"
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