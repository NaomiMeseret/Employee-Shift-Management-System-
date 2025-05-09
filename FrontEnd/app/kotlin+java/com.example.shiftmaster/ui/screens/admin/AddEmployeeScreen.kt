package com.example.shiftmaster.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.shiftmaster.R
import kotlinx.coroutines.launch

@Composable
fun AddEmployeeScreen(navController: NavController) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var employeeId by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            // === Inline Header ===
            Surface(
                color = Color(0xFFCCD9D3),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.shiftmaster_logo), // Replace with your actual logo resource
                        contentDescription = "Logo",
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("ShiftMaster", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.weight(1f))
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFF0A1912),
                    ) {
                        Text(
                            "Admin",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color.Gray, shape = CircleShape)
                    )
                }
            }
        },
        bottomBar = {
            // === Inline Footer ===
            Surface(
                color = Color(0xFFCCD9D3),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "©2025 ShiftMaster. All rights reserved",
                        fontSize = 12.sp,
                        color = Color.DarkGray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // === Tab Bar ===
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFD9D9D9), RoundedCornerShape(10.dp))
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Shift",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.clickable {
                            // navController.navigate("shifts")
                        }
                    )
                    Text(
                        text = "Team",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.clickable {
                            navController.navigate("admin_team")
                        }
                    )
                    Text(
                        text = "Attendance",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.clickable {
                            navController.navigate("admin_employee_attendance")
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // === Add Employee Form ===
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp))
                        .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Add a New Employee",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        label = { Text("Employee First Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        label = { Text("Employee Last Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = employeeId,
                        onValueChange = { employeeId = it },
                        label = { Text("Employee ID") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Employee Phone") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = role,
                        onValueChange = { role = it },
                        label = { Text("Employee Role") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Employee Password") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Employee Added")

                                // Reset form fields
                                firstName = ""
                                lastName = ""
                                employeeId = ""
                                phone = ""
                                role = ""
                                password = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF216043)),
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Add Employee", color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun AddEmployeeScreenPreview() {
    val navController = rememberNavController()
    AddEmployeeScreen(navController)
}
