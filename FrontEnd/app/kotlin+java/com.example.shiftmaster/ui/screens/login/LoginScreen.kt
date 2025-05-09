package com.example.shiftmaster.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.shiftmaster.R
import com.example.shiftmaster.ui.navigation.Screen
import com.example.shiftmaster.viewmodel.LoginViewModel

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = viewModel()) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val loginResult by viewModel.loginResult.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.shiftmaster_logo),
            contentDescription = "ShiftMaster Logo",
            modifier = Modifier
                .size(100.dp)
                .padding(bottom = 24.dp)
        )

        Text(
            text = "ShiftMaster",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Text(
            text = "Enter your credentials to access your account",
            fontSize = 14.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
        )

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Email", fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = viewModel::updateEmail,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(text = "Password", fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = viewModel::updatePassword,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.login() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E5D47))
            ) {
                Text(text = "Sign in", color = Color.White)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Show error if login failed
            loginResult?.let { result ->
                when (result) {
                    is LoginViewModel.LoginResult.Success -> {
                        val route =
                            if (result.role == "admin") Screen.AdminEmployeeView.route else Screen.EmployeeShifts.route
                        LaunchedEffect(Unit) {
                            navController.navigate(route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }
                    }

                    is LoginViewModel.LoginResult.Error -> {
                        Text(
                            text = result.message,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "©2025 ShiftMaster. All rights reserved",
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}
