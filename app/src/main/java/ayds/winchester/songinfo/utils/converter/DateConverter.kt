package ayds.winchester.songinfo.utils.converter

import android.os.Build
import androidx.annotation.RequiresApi
import ayds.winchester.songinfo.utils.converter.AnioBisiesto.getAnioBisiesto
import java.time.Month
import java.util.*


interface DateConverter {
    fun getDateConverted(date : String): String
}

internal object DateConverterDayImpl : DateConverter {

    override fun getDateConverted(date: String): String {
        val delimiter = "-"
        val delimiterNuevo = "/"
        val dateSeparada= date.split(delimiter)
        return dateSeparada[2]+ delimiterNuevo +dateSeparada[1]+ delimiterNuevo +dateSeparada[0]
    }

}

internal object DateConverterMonthImpl : DateConverter {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getDateConverted(date : String): String{
        val delimiter = "-"
        val fecha = date.split(delimiter)
        return Month.of(Integer.parseInt(fecha[1])).toString().lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() } +","+fecha[0]

    }

}

internal object DateConverterYearImpl : DateConverter {
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