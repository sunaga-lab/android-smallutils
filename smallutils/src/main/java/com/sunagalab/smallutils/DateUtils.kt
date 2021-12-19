package com.sunagalab.smallutils

import java.util.*



fun Date.getFromCalendarField(fieldInt: Int): Int {
    return Calendar.getInstance().let {
        it.time = this
        it.get(fieldInt)
    }
}

val Date.dateStartOfHour: Date get() {
    val calendar = getCalendarInstance()
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.time
}

val Date.dateStartOfHourNextNearest: Date get() {
    val calendar = getCalendarInstance()
    if (calendar.get(Calendar.MINUTE) == 0 && calendar.get(Calendar.SECOND) == 0 && calendar.get(Calendar.MILLISECOND) == 0) {
        return this
    }
    calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 1)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.time
}

fun Date.getCalendarInstance(): Calendar = Calendar.getInstance().also { it.time = this }

fun Date.dateAddedDays(days: Int): Date = dateAddedMilliseconds(days*1000*60*60*24)
fun Date.dateAddedHours(hours: Int): Date = dateAddedMilliseconds(hours*1000*60*60)
fun Date.dateAddedMinutes(minutes: Int): Date = dateAddedMilliseconds(minutes*1000*60)
fun Date.dateAddedSeconds(secondsValue: Int): Date = dateAddedMilliseconds(secondsValue*1000)
fun Date.dateAddedMilliseconds(millisecondsValue: Int): Date = Date(this.time + millisecondsValue)


val Date.dateStartOfDay: Date get() {
    val calendar = getCalendarInstance()
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    return calendar.time
}

fun Date.isSameDayTo(rhs: Date): Boolean = this.dateStartOfDay == rhs.dateStartOfDay

val Date.secondsFromCalendar get() = getFromCalendarField(Calendar.SECOND)
val Date.minutesFromCalendar get() = getFromCalendarField(Calendar.MINUTE)
val Date.hoursFromCalendar get() = getFromCalendarField(Calendar.HOUR_OF_DAY)
val Date.dayOfWeekFromCalendar get() = getFromCalendarField(Calendar.DAY_OF_WEEK)
val Date.dayOfMonthFromCalendar get() = getFromCalendarField(Calendar.DAY_OF_MONTH)
val Date.monthFromCalendar get() = getFromCalendarField(Calendar.MONTH)
val Date.yearsFromCalendar get() = getFromCalendarField(Calendar.YEAR)


