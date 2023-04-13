package ayds.winchester.songinfo.utils.converter

import android.os.Build
import androidx.annotation.RequiresApi
import ayds.winchester.songinfo.home.model.entities.Song

import ayds.winchester.songinfo.home.view.AnioBisiesto.getAnioBisiesto
import java.time.Month
import java.util.*

object DateFactory {
    fun get( song: Song.SpotifySong) =
        when (song.releaseDatePrecision) {
            "day" -> DateConverterDayImpl(song.releaseDate)
            "month" -> DateConverterMonthImpl(song.releaseDate)
            else-> DateConverterYearImpl(song.releaseDate)

        }
}
sealed class DateConverter {
    abstract fun getDateConverted(date : String): String
}

class DateConverterDayImpl (releaseDate:String): DateConverter() {
    override fun getDateConverted(date: String): String {
        val delimiter = "-"
        val delimiterNuevo = "/"
        val dateSeparada= date.split(delimiter)
        return dateSeparada[2]+ delimiterNuevo +dateSeparada[1]+ delimiterNuevo +dateSeparada[0]
    }

}
class DateConverterMonthImpl (date:String): DateConverter() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getDateConverted(date : String): String{
        val delimiter = "-"
        val fecha = date.split(delimiter)
        return Month.of(Integer.parseInt(fecha[1])).toString().lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() } +","+fecha[0]

    }

}
class DateConverterYearImpl (date:String): DateConverter() {
    override fun getDateConverted(date : String): String{
        var fecha = date
        fecha += if(getAnioBisiesto(Integer.parseInt(date))){
            "( a leap year)"
        } else{
            "( not a leap year)"
        }
        return fecha
    }

}