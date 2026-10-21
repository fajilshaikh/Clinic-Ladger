package com.clinicledger.app.navigation

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Home")
    object History : Screen("history", "History")
    object Reports : Screen("reports", "Reports")
    object Settings : Screen("settings", "Settings")

    companion object {
        val bottomNavItems = listOf(Home, History, Reports, Settings)
    }
}
