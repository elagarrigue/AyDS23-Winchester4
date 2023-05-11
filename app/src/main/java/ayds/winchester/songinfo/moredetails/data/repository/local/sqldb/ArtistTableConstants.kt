package ayds.winchester.songinfo.moredetails.data.repository.local.sqldb

const val ARTISTS_TABLE = "artists"
const val ARTIST_COLUMN="artist"
const val INFO_COLUMN="info"
const val WIKIPEDIA_URL_COLUMN="source"


const val createArtistTable: String ="create table $ARTISTS_TABLE (" +
        "$ARTIST_COLUMN string, " +
        "$INFO_COLUMN string, " +
        "$WIKIPEDIA_URL_COLUMN string)"

