package ayds.winchester.songinfo.home.view

import Converter
import DateConverterDayImpl
import DateConverterDefaultImpl
import DateConverterMonthImpl
import DateConverterYearImpl
import ayds.winchester.songinfo.home.model.entities.Song

private const val day ="day"
private const val month="month"
private const val year="year"
interface DateFactory {
    fun getDateConverted( song: Song.SpotifySong):Converter
}
class DateFactoryImpl:DateFactory{
    override fun getDateConverted( song: Song.SpotifySong) =
        when (song.releaseDatePrecision) {
            day -> DateConverterDayImpl(song.releaseDate)
            month -> DateConverterMonthImpl(song.releaseDate)
            year -> DateConverterYearImpl(song.releaseDate)
            else -> DateConverterDefaultImpl(song.releaseDate)
        }
    }
