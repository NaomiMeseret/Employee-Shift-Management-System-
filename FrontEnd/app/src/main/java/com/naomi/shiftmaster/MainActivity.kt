package com.naomi.shiftmaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.naomi.shiftmaster.ui.navigation.AppNavHost
import com.naomi.shiftmaster.ui.theme.ShiftMasterTheme
import com.naomi.shiftmaster.viewmodel.EmployeeViewModel

import androidx.lifecycle.viewmodel.compose.viewModel
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            ShiftMasterTheme {
                val navController = rememberNavController()
                val employeeViewModel: EmployeeViewModel = viewModel() // create it here

                AppNavHost(
                    navController = navController,
                    employeeViewModel = employeeViewModel // ✅ pass it
                )
            }
        }
    }
}
