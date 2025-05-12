package com.naomi.shiftmaster.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
// Replace wildcard import with specific imports
import com.naomi.shiftmaster.ui.screens.admin.AdminEmployeeViewScreen
import com.naomi.shiftmaster.ui.screens.admin.AdminAttendanceScreen
import com.naomi.shiftmaster.ui.screens.admin.AdminShiftManagementScreen
import com.naomi.shiftmaster.ui.screens.admin.AdminAssignShiftScreen
import com.naomi.shiftmaster.ui.screens.admin.AdminAddEmployeeScreen
import com.naomi.shiftmaster.ui.screens.admin.AdminEditShiftScreen
import com.naomi.shiftmaster.ui.screens.admin.AdminUpdateEmployeeScreen
// Employee screens
import com.naomi.shiftmaster.ui.screens.employee.EmployeeAttendanceScreen
import com.naomi.shiftmaster.ui.screens.employee.EmployeeShiftScreen
import com.naomi.shiftmaster.ui.screens.employee.EmployeeTeamScreen
import com.naomi.shiftmaster.ui.screens.employee.EmployeeProfileScreen
import com.naomi.shiftmaster.ui.screens.login.LoginScreen

import com.naomi.shiftmaster.viewmodel.AdminShiftViewModel
import com.naomi.shiftmaster.viewmodel.EmployeeViewModel

@Composable
fun AppNavHost(navController: NavHostController, startDestination: String = Screen.Login.route, employeeViewModel: EmployeeViewModel) {
    val adminShiftViewModel: AdminShiftViewModel = viewModel()



    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            LoginScreen(navController, viewModel = employeeViewModel)
        }

        // Employee routes
        composable(
            Screen.EmployeeShifts.route
        ) {
            EmployeeShiftScreen(
                onNavItemClick = { destination: String ->
                    when (destination) {
                        "shifts" -> { /* Already on this screen */ }
                        "attendance" -> navController.navigate(Screen.EmployeeAttendance.route)
                        "team" -> navController.navigate(Screen.EmployeeTeam.route)
                        "profile" -> navController.navigate(Screen.EmployeeProfile.route)
                    }

                },
                viewModel = employeeViewModel
            )
        }

        composable(Screen.EmployeeAttendance.route) {
            EmployeeAttendanceScreen(
                onNavItemClick = { destination: String ->
                    when (destination) {
                        "shifts" -> navController.navigate(Screen.EmployeeShifts.route)
                        "attendance" -> { /* Already on this screen */ }
                        "team" -> navController.navigate(Screen.EmployeeTeam.route)
                        "profile" -> navController.navigate(Screen.EmployeeProfile.route)
                    }
                }
            )
        }

        composable(Screen.EmployeeTeam.route) {
            EmployeeTeamScreen(
                onNavItemClick = { destination: String ->
                    when (destination) {
                        "shifts" -> navController.navigate(Screen.EmployeeShifts.route)
                        "attendance" -> navController.navigate(Screen.EmployeeAttendance.route)
                        "team" -> { /* Already on this screen */ }
                        "profile" -> navController.navigate(Screen.EmployeeProfile.route)
                    }
                },
                viewModel = employeeViewModel
            )
        }

        composable(Screen.EmployeeProfile.route) {
            EmployeeProfileScreen(
                onNavItemClick = { destination: String ->
                    when (destination) {
                        "shifts" -> navController.navigate(Screen.EmployeeShifts.route)
                        "attendance" -> navController.navigate(Screen.EmployeeAttendance.route)
                        "team" -> navController.navigate(Screen.EmployeeTeam.route)
                        "profile" -> { /* Already on this screen */ }
                    }
                },
                onSignOut = { ->
                    employeeViewModel.signOut()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true } // clears back stack
                    }
                },
                viewModel2 = employeeViewModel
            )
        }

        // Admin routes
        composable(Screen.AdminEmployeeView.route) {
            Log.d("Navigation", "Navigating to AdminEmployeeViewScreen")
            AdminEmployeeViewScreen(
                onNavItemClick = { destination: String ->
                    when (destination) {
                        "shifts" -> navController.navigate(Screen.AdminShiftManagement.route)
                        "team" -> { /* Already on this screen */ }
                        "attendance" -> navController.navigate(Screen.AdminAttendance.route)
                    }
                },
                onAddMemberClick = {  ->
                    navController.navigate(Screen.AdminAddEmployee.route)
                },
                onEditClick = { employeeId ->
                    navController.navigate("${Screen.AdminUpdateEmployee.route}/$employeeId")
                },
                onDeleteClick = { employeeId ->
                    navController.navigate(Screen.AdminEmployeeView.route)
                },
            )
        }

        composable(Screen.AdminShiftManagement.route) {
            Log.d("Navigation", "Navigating to AdminShiftManagementScreen")
            AdminShiftManagementScreen(
                onNavItemClick = { destination: String ->
                    when (destination) {
                        "shifts" -> { /* Already on this screen */ }
                        "team" -> navController.navigate(Screen.AdminEmployeeView.route)
                        "attendance" -> navController.navigate(Screen.AdminAttendance.route)
                    }
                },
                onAssignShiftClick = { ->
                    navController.navigate(Screen.AdminAssignShift.route)
                },
                onEditClick = { employeeId, shiftId ->
                    navController.navigate("${Screen.AdminEditShift.route}/$employeeId/$shiftId")
                },
                onDeleteClick = { shiftId ->
                    adminShiftViewModel.deleteShift(shiftId.toInt())
                },
                viewModel = adminShiftViewModel
            )
        }
        // Uncomment if implementing AddEmployeeScreen
        composable(Screen.AdminAddEmployee.route) {
            AdminAddEmployeeScreen(
                onNavItemClick = { destination: String ->
                    when (destination) {
                        "shifts" -> navController.navigate(Screen.AdminShiftManagement.route)
                        "team" -> navController.navigate(Screen.AdminEmployeeView.route)
                        "attendance" -> navController.navigate(Screen.AdminAttendance.route)
                    }
                },

                onBackClick = { ->
                    navController.navigate(Screen.AdminEmployeeView.route)
                },
                viewModel = employeeViewModel
            )
        }

        // Uncomment if implementing AddEmployeeScreen
        composable(
            route = "${Screen.AdminUpdateEmployee.route}/{employeeId}"

        ) {
            backStackEntry ->
            val employeeId = backStackEntry.arguments?.getString("employeeId") ?: ""

            AdminUpdateEmployeeScreen(
                employeeId = employeeId,
                onNavItemClick = { destination: String ->
                    when (destination) {
                        "shifts" -> navController.navigate(Screen.AdminShiftManagement.route)
                        "team" -> navController.navigate(Screen.AdminEmployeeView.route)
                        "attendance" -> navController.navigate(Screen.AdminAttendance.route)
                    }
                },

                onBackClick = { ->
                    navController.navigate(Screen.AdminEmployeeView.route)
                },
                viewModel = employeeViewModel
            )
        }

        // Uncomment if implementing AdminEditShiftScreen
        
        composable(
            route = "${Screen.AdminEditShift.route}/{employeeId}/{shiftId}",
        ) { backStackEntry ->
            val employeeId = backStackEntry.arguments?.getString("employeeId") ?: ""
            val shiftId = backStackEntry.arguments?.getString("shiftId") ?: ""
            AdminEditShiftScreen(
                employeeId = employeeId,
                shiftId = shiftId,
                onNavItemClick = { destination: String ->
                    when (destination) {
                        "shifts" -> navController.navigate(Screen.AdminShiftManagement.route)
                        "team" -> navController.navigate(Screen.AdminEmployeeView.route)
                        "attendance" -> navController.navigate(Screen.AdminAttendance.route)
                    }
                },
                onSaveClick = { ->
                    navController.navigate(Screen.AdminShiftManagement.route)
                    adminShiftViewModel.fetchEmployeesAndShifts()


                },
                onBackClick = { ->
                    navController.navigate(Screen.AdminEmployeeView.route)
                },
                viewModel = employeeViewModel
            )
        }
        

        // AdminAssignShift route (with fixed parameter type and viewModel)
        composable(Screen.AdminAssignShift.route) {
            AdminAssignShiftScreen(
                onNavItemClick = { destination: String ->
                    when (destination) {
                        "shifts" -> { /* Already on shifts screen in assign context */ }
                        "team" -> navController.navigate(Screen.AdminEmployeeView.route)
                        "attendance" -> navController.navigate(Screen.AdminAttendance.route)
                    }
                },
                onAssignShiftClick = { ->
                    navController.navigate(Screen.AdminShiftManagement.route)
                    adminShiftViewModel.fetchEmployeesAndShifts()
                },
                onBackClick = { ->
                    navController.navigate(Screen.AdminShiftManagement.route)
                },
                viewModel = EmployeeViewModel() // Add the viewModel parameter
            )
        }

        // Uncomment if implementing AdminEmployeeAttendanceScreen

        composable(Screen.AdminAttendance.route) {
            AdminAttendanceScreen(
                onNavItemClick = { destination: String ->
                    when (destination) {
                        "shifts" -> navController.navigate(Screen.AdminShiftManagement.route)
                        "team" -> navController.navigate(Screen.AdminEmployeeView.route)
                        "attendance" -> { /* Already on this screen */ }
                    }
                },
                onSignOut = { ->
                    employeeViewModel.signOut()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true } // clears back stack
                    }
                },
            )
        }

    }
}