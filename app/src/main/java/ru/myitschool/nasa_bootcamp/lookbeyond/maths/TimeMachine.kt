package ru.myitschool.nasa_bootcamp.lookbeyond.maths

import java.util.*



//МАШИНА ВРЕМЕНИ
object TimeMachine {

    fun julianCenturies(date: Date?): Double {
        val jd = calculateJulianDay(date)
        val delta = jd - 2451545.0
        return delta / 36525.0
    }

    // JD = 367 * Y - INT(7 * (Y + INT((M + 9)/12))/4) + INT(275 * M / 9 + D + 1721013.5 + UT/24
    fun calculateJulianDay(date: Date?): Double {
        val cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"))
        cal.time = date
        val hour = (cal[Calendar.HOUR_OF_DAY]
                + cal[Calendar.MINUTE] / 60.0f + cal[Calendar.SECOND] / 3600.0f).toDouble()
        val year = cal[Calendar.YEAR]
        val month = cal[Calendar.MONTH] + 1
        val day = cal[Calendar.DAY_OF_MONTH]
        return (367.0 * year - Math.floor(7.0 * (year + Math.floor((month + 9.0) / 12.0)) / 4.0) + Math.floor(275.0 * month / 9.0) + day
                + 1721013.5 + hour / 24.0)
    }

}
