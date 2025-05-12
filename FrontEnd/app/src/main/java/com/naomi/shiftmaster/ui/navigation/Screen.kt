package com.naomi.shiftmaster.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object ShiftList : Screen("shift_list")

    // Employee screens
    object EmployeeShifts : Screen("employee_shifts")
    object EmployeeAttendance : Screen("employee_attendance")
    object EmployeeTeam : Screen("employee_team")
    object EmployeeProfile : Screen("employee_profile")

    // Admin screens
    object AdminEmployeeView : Screen("admin_employee_view")
    object AdminShiftManagement : Screen("admin_shift_management")
    object AdminAddEmployee : Screen("admin_add_employee")
    object AdminEditShift : Screen("admin_edit_shift")
    object AdminAssignShift : Screen("admin_assign_shift")
    object AdminAttendance : Screen("admin_attendance")
    object AdminUpdateEmployee: Screen("admin_update_employee")
}