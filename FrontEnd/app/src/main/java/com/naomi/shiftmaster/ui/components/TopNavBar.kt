package com.naomi.shiftmaster.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.naomi.shiftmaster.ui.navigation.Screen

@Composable
fun TopNavBar(navController: NavController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    Row {
        NavItem(
            label = "Shifts",
            selected = currentRoute == Screen.ShiftList.route
        ) {
            if (currentRoute != Screen.ShiftList.route) {
                navController.navigate(Screen.ShiftList.route)
            }
        }

        NavItem(
            label = "My Shifts",
            selected = currentRoute == Screen.EmployeeShifts.route
        ) {
            if (currentRoute != Screen.EmployeeShifts.route) {
                navController.navigate(Screen.EmployeeShifts.route)
            }
        }
    }
}
