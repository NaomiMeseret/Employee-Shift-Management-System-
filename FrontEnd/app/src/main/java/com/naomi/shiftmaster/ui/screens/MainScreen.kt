package com.naomi.shiftmaster.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.naomi.shiftmaster.data.repository.EmployeeRepository
import com.naomi.shiftmaster.ui.components.TopNavBar
import com.naomi.shiftmaster.ui.navigation.AppNavHost
import com.naomi.shiftmaster.ui.screens.employee.EmployeeShiftScreen
import com.naomi.shiftmaster.ui.screens.employee.EmployeeAttendanceScreen
import com.naomi.shiftmaster.ui.screens.employee.EmployeeTeamScreen
import com.naomi.shiftmaster.ui.screens.employee.EmployeeProfileScreen
import com.naomi.shiftmaster.viewmodel.EmployeeViewModel

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    // Shared ViewModel created here (scoped to MainScreen)
    val employeeViewModel : EmployeeViewModel = viewModel()

    Column {
        TopNavBar(navController = navController)
        AppNavHost(
            navController = navController,
            employeeViewModel = employeeViewModel
        )

        NavHost(navController = navController, startDestination = "Shifts") {
            composable("Shifts") {

                EmployeeShiftScreen(
                    viewModel = employeeViewModel,
                    onNavItemClick = { route -> navController.navigate(route) }
                )
            }
            composable("Attendance") {
                EmployeeAttendanceScreen(

                    onNavItemClick = { route -> navController.navigate(route) },
                    viewModel = employeeViewModel
                )
            }
            composable("Team") {
                EmployeeTeamScreen(

                    onNavItemClick = { route -> navController.navigate(route) },
                    viewModel = employeeViewModel
                    )
            }
            composable("Profile") {
                EmployeeProfileScreen(
                    onNavItemClick = {}, viewModel2 = employeeViewModel)
            }
        }
    }
}