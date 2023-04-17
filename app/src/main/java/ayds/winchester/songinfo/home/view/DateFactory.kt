package ayds.winchester.songinfo.home.view

import Converter
import DateConverterDayImpl
import DateConverterDefaultImpl
import DateConverterMonthImpl
import DateConverterYearImpl
import ayds.winchester.songinfo.home.model.entities.Song

private const val DAY ="day"
private const val MONTH="month"
private const val YEAR="year"
interface DateFactory {
    fun getDateConverted( song: Song.SpotifySong):Converter
}
class DateFactoryImpl:DateFactory{
    override fun getDateConverted( song: Song.SpotifySong) =
        when (song.releaseDatePrecision) {
            DAY -> DateConverterDayImpl(song.releaseDate)
            MONTH -> DateConverterMonthImpl(song.releaseDate)
            YEAR -> DateConverterYearImpl(song.releaseDate)
            else -> DateConverterDefaultImpl(song.releaseDate)
        }
    }
