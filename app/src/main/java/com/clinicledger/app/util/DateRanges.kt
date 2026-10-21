package com.clinicledger.app.util

import java.util.Calendar

enum class ReportPeriod(val label: String) {
    TODAY("Today"),
    THIS_WEEK("This Week"),
    THIS_MONTH("This Month"),
    ALL_TIME("All Time"),
    CUSTOM("Custom")
}

data class DateRange(val start: Long, val end: Long)

private fun startOfDay(cal: Calendar): Calendar = cal.apply {
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
}

private fun endOfDay(cal: Calendar): Calendar = cal.apply {
    set(Calendar.HOUR_OF_DAY, 23)
    set(Calendar.MINUTE, 59)
    set(Calendar.SECOND, 59)
    set(Calendar.MILLISECOND, 999)
}

fun todayRange(): DateRange {
    val start = startOfDay(Calendar.getInstance()).timeInMillis
    val end = endOfDay(Calendar.getInstance()).timeInMillis
    return DateRange(start, end)
}

fun thisWeekRange(): DateRange {
    val startCal = Calendar.getInstance().apply {
        firstDayOfWeek = Calendar.MONDAY
        set(Calendar.DAY_OF_WEEK, firstDayOfWeek)
    }
    val start = startOfDay(startCal).timeInMillis
    val end = endOfDay(Calendar.getInstance()).timeInMillis
    return DateRange(start, end)
}

fun thisMonthRange(): DateRange {
    val startCal = Calendar.getInstance().apply { set(Calendar.DAY_OF_MONTH, 1) }
    val start = startOfDay(startCal).timeInMillis
    val end = endOfDay(Calendar.getInstance()).timeInMillis
    return DateRange(start, end)
}

fun allTimeRange(): DateRange = DateRange(0L, Long.MAX_VALUE)

fun rangeFor(period: ReportPeriod, customStart: Long? = null, customEnd: Long? = null): DateRange = when (period) {
    ReportPeriod.TODAY -> todayRange()
    ReportPeriod.THIS_WEEK -> thisWeekRange()
    ReportPeriod.THIS_MONTH -> thisMonthRange()
    ReportPeriod.ALL_TIME -> allTimeRange()
    ReportPeriod.CUSTOM -> DateRange(
        customStart?.let { startOfDay(Calendar.getInstance().apply { timeInMillis = it }).timeInMillis } ?: todayRange().start,
        customEnd?.let { endOfDay(Calendar.getInstance().apply { timeInMillis = it }).timeInMillis } ?: todayRange().end
    )
}
