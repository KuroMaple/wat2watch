package com.team1.wat2watch.ui.navbar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NavBarViewModel(private val navController: NavController) : ViewModel() {
    private val _selectedItem = MutableStateFlow("Home Icon")
    val selectedItem: StateFlow<String> = _selectedItem

    init {
        // Listen for navigation changes and update the selected icon
        viewModelScope.launch {
            navController.currentBackStackEntryFlow.collect { backStackEntry ->
                val currentRoute = backStackEntry.destination.route
                _selectedItem.value = when (currentRoute) {
                    "home" -> "Home Icon"
                    "history" -> "History Icon"
                    "profile" -> "Profile Icon"
                    "search" -> "Search Icon"

                    else -> "Home Icon" // Default case
                }
            }
        }
    }

    fun onNavItemSelect(description: String){
        _selectedItem.value = description

        // Map icon descriptions to routes and navigate
        val route = when (description) {
            "Home Icon" -> "home"
            "History Icon" -> "history"
            "Profile Icon" -> "profile"
            "Search Icon" -> "search"
            else -> return
        }

        // Don't navigate if the route is already the same as the current one
        if (route == selectedItem.value) {
            return // Do nothing if already on the selected screen
        }

        // Check if the route is already in the back stack
        navController.currentBackStackEntry?.destination?.route?.let { currentRoute ->
            if (currentRoute == route) {
                return // Prevent navigation to the same screen
            }
        }

        navController.navigate(route){
            popUpTo("home") { inclusive = false } // Keep home in the back stack
            launchSingleTop = true // Avoid duplicate destinations in back stack
        }
    }
}