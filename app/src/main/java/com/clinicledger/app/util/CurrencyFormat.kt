package com.clinicledger.app.util

import java.text.NumberFormat
import java.util.Locale

private val inrGroupingFormat: NumberFormat = NumberFormat.getNumberInstance(Locale("en", "IN"))

/** Formats a whole-rupee amount as "₹1,00,000" using Indian digit grouping. */
fun formatInr(amount: Long): String = "₹${inrGroupingFormat.format(amount)}"
