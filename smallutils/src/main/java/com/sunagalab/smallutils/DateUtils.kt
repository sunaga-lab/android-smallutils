package com.sunagalab.smallutils

import java.util.*

object DateUtil {

    fun addDays(fromDate: Date, days: Int): Date = Date(fromDate.time + days*1000*60*60*24)
    fun addHours(fromDate: Date, hours: Int): Date = Date(fromDate.time + hours*1000*60*60)


    fun calcStartOfDay(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.time
    }

    fun isSameDay(date1: Date, date2: Date): Boolean = calcStartOfDay(date1) == calcStartOfDay(date2)


    fun getMinutesPart(date: Date): Int {
        val cal = Calendar.getInstance()
        cal.time = date
        return cal.get(Calendar.MINUTE)
    }

    fun getHoursPart(date: Date): Int {
        val cal = Calendar.getInstance()
        cal.time = date
        return cal.get(Calendar.HOUR_OF_DAY)
    }
}



