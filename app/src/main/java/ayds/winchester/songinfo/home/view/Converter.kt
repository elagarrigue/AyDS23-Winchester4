interface Converter {
    fun convertDate(): String
}

internal class DateConverterDayImpl (private val releaseDate:String): Converter {
    override fun convertDate(): String {
        val delimiter = "-"
        val delimiterNuevo = "/"
        val dateSeparada= releaseDate.split(delimiter)
        return dateSeparada[2]+ delimiterNuevo +dateSeparada[1]+ delimiterNuevo +dateSeparada[0]
    }

}

internal class DateConverterMonthImpl (private val releaseDate:String): Converter {
    override fun convertDate(): String{
        val delimiter = "-"
        val fecha = releaseDate.split(delimiter)
        val month:String = when (fecha[1]){
            "01"->"January"
            "02"->"February"
            "03"->"March"
            "04"->"April"
            "05"->"May "
            "06"->"June "
            "07"->"July"
            "08"->"August"
            "09"->"September "
            "10"->"October"
            "11"->"November "
            "12"->"December "
            else->""

        }
        return month +","+fecha[0]
    }

}

internal class DateConverterYearImpl (private val releaseDate:String): Converter {
    private fun getAnioBisiesto(anio : Int) = (anio % 4 == 0) && (anio % 100 != 0 || anio % 400 == 0)
    override fun convertDate(): String{
        var fecha = releaseDate
        fecha += if(getAnioBisiesto(Integer.parseInt(releaseDate))){
            "( a leap year)"
        } else{
            "( not a leap year)"
        }
        return fecha
    }
}

internal class DateConverterDefaultImpl (private val releaseDate:String): Converter {
    override fun convertDate()= releaseDate
}