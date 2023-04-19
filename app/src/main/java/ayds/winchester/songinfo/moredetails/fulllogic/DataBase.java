package ayds.winchester.songinfo.moredetails.fulllogic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {
  private static void createMapValues(ContentValues values,String artist, String info){
    int source=1;
    values.put("artist", artist);
    values.put("info", info);
    values.put("source", source);
  }
  public static void saveArtist(DataBase dataBaseHelper, String artist, String info)
  {
    SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
    ContentValues values = new ContentValues();
    createMapValues(values,artist,info);
    database.insert("artists", null, values);
  }
  private static SQLiteDatabase getDataBase(DataBase dataBaseHelper){
    return dataBaseHelper.getReadableDatabase();
  }
  private static Cursor createCursor(SQLiteDatabase dataBase, String artist){
    String[] projection = {
            "id",
            "artist",
            "info"
    };
    String selection = "artist  = ?";
    String[] selectionArgs = { artist };
    String sortOrder = "artist DESC";
    Cursor cursor = dataBase.query(
            "artists",
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
    );
    return cursor;
  }
  private static void addItems(Cursor cursor,List<String> items){
    while(cursor.moveToNext()) {
      String info = cursor.getString(cursor.getColumnIndexOrThrow("info"));
      items.add(info);
    }
    cursor.close();
  }
  public static String getInfo(DataBase dataBaseHelper, String artist) {
    SQLiteDatabase database = getDataBase(dataBaseHelper);
    Cursor cursor= createCursor(database,artist);
    List<String> items = new ArrayList<String>();
    addItems(cursor,items);
    if(items.isEmpty())
      return null;
    else
      return items.get(0);
  }
  public DataBase(Context context) {
    super(context, "dictionary.db", null, 1);
  }
  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(
            "create table artists (id INTEGER PRIMARY KEY AUTOINCREMENT, artist string, info string, source integer)");
    Log.i("DB", "DB created");
  }
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}