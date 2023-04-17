package ayds.winchester.songinfo.home.view

import Converter
import DateConverterDayImpl
import DateConverterDefaultImpl
import DateConverterMonthImpl
import DateConverterYearImpl
import ayds.winchester.songinfo.home.model.entities.Song

private const val dia ="day"
private const val mes="month"
private const val anio="year"
interface DateFactory {
    fun getDateConverted( song: Song.SpotifySong):Converter
}
class DateFactoryImpl:DateFactory{
    override fun getDateConverted( song: Song.SpotifySong) =
        when (song.releaseDatePrecision) {
            dia -> DateConverterDayImpl(song.releaseDate)
            mes -> DateConverterMonthImpl(song.releaseDate)
            anio -> DateConverterYearImpl(song.releaseDate)
            else -> DateConverterDefaultImpl(song.releaseDate)
        }
    }
