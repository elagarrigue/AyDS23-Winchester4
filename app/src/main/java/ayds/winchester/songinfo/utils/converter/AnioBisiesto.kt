package ayds.winchester.songinfo.utils.converter

object AnioBisiesto {

    fun getAnioBisiesto(anio : Int) = (anio % 4 == 0) && (anio % 100 != 0 || anio % 400 == 0)

}