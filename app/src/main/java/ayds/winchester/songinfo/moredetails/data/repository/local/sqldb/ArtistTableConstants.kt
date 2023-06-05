package ayds.winchester.songinfo.moredetails.data.repository.local.sqldb

const val ARTISTS_TABLE = "cards"
const val ARTIST_COLUMN="artist"
const val INFO_COLUMN="info"
const val SOURCE_COLUMN="source"
const val URL_COLUMN="url"
const val LOGO_COLUMN="logo"
const val DATABASE_VERSION = 1
const val DATABASE_NAME="dictionary.db"

const val createArtistTable: String ="create table $ARTISTS_TABLE (" +
        "$ARTIST_COLUMN string, " +
        "$INFO_COLUMN string, " +
        "$SOURCE_COLUMN string, " +
        "$LOGO_COLUMN string, " +
        "$URL_COLUMN string)"

