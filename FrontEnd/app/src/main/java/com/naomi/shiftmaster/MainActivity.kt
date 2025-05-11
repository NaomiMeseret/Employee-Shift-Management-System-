package com.naomi.shiftmaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.naomi.shiftmaster.ui.navigation.AppNavHost
import com.naomi.shiftmaster.ui.theme.ShiftMasterTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShiftMasterTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}
