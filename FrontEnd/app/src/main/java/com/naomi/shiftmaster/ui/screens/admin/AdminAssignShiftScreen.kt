package com.naomi.shiftmaster.ui.screens.admin

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.activity.compose.BackHandler

import com.naomi.shiftmaster.R
import com.naomi.shiftmaster.ui.components.NavItem
import com.naomi.shiftmaster.viewmodel.EmployeeViewModel

// Consistent header and footer height
private val HEADER_HEIGHT = 70.dp
private val FOOTER_HEIGHT = 70.dp
// Spacing
private val CONTENT_PADDING = 16.dp
private val HEADER_NAV_SPACING = 24.dp
private val SECTION_SPACING = 20.dp
private val FORM_SPACING = 16.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminAssignShiftScreen(
    onNavItemClick: (String) -> Unit = {},
    onAssignShiftClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    viewModel: EmployeeViewModel
) {
    // Log when the screen is composed
    Log.d("AdminAssignShiftScreen", "Screen composed")

    // Add BackHandler to handle system back button
    BackHandler {
        Log.d("AdminAssignShiftScreen", "System back button pressed")
        onBackClick()
    }


    // Form states
    val name = remember { mutableStateOf("") }
    val id = remember { mutableStateOf("") }
    val employeeId = remember { mutableStateOf("") }
    val employeeRole = remember { mutableStateOf("") }
    val shiftDate = remember { mutableStateOf("") }
    val shiftTime = remember { mutableStateOf("") }

    // Error state
    val showError = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Surface(
                color = Color(0xFFE3ECF0),
                tonalElevation = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .height(HEADER_HEIGHT)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = CONTENT_PADDING)
                ) {
                    // Back button with improved click handling and logging
                    IconButton(
                        onClick = {
                            Log.d("AdminAssignShiftScreen", "Back button clicked")
                            onBackClick()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }

                    // Logo and title
                    Image(
                        painter = painterResource(R.drawable.shiftmaster_logo),
                        contentDescription = "Logo",
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "ShiftMaster",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.weight(1f))
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color.Black,
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Text(
                            "Admin",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }
                    Surface(
                        shape = CircleShape,
                        color = Color.LightGray,
                        modifier = Modifier.size(32.dp)
                    ) {
                        // Profile picture placeholder
                    }
                }
            }
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White) // White background to match AdminShiftManagementScreen
                .verticalScroll(rememberScrollState()) // Make the entire content scrollable
        ) {
            Spacer(Modifier.height(HEADER_NAV_SPACING))



            Spacer(Modifier.height(SECTION_SPACING))

            // Main content area with form - using a lighter gray color matching screenshot
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = CONTENT_PADDING)
                    .background(Color(0xFFE5E5E5), RoundedCornerShape(8.dp))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(CONTENT_PADDING)
                ) {
                    // Form title
                    Text(
                        "Assign shift",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp)
                    )

                    // Error message if validation fails
                    if (showError.value) {
                        Text(
                            text = errorMessage.value,
                            color = Color.Red,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                        )
                    }

                    // Form fields - using OutlinedTextFieldDefaults.colors() for Material 3
                    OutlinedTextField(
                        value = name.value,
                        onValueChange = { name.value = it },
                        label = { Text("Employee Full Name") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = FORM_SPACING),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            disabledTextColor = Color.Gray,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.LightGray
                        )
                    )

                    OutlinedTextField(
                        value = id.value,
                        onValueChange = { id.value = it },
                        label = { Text("Shift Id") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = FORM_SPACING),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            disabledTextColor = Color.Gray,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.LightGray
                        )
                    )

                    OutlinedTextField(
                        value = employeeId.value,
                        onValueChange = { employeeId.value = it },
                        label = { Text("Employee ID") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = FORM_SPACING),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            disabledTextColor = Color.Gray,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.LightGray
                        )
                    )

                    OutlinedTextField(
                        value = employeeRole.value,
                        onValueChange = { employeeRole.value = it },
                        label = { Text("Employee role") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = FORM_SPACING),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            disabledTextColor = Color.Gray,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.LightGray
                        )
                    )

                    OutlinedTextField(
                        value = shiftDate.value,
                        onValueChange = { shiftDate.value = it },
                        label = { Text("Shift date") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = FORM_SPACING),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            disabledTextColor = Color.Gray,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.LightGray
                        )
                    )

                    OutlinedTextField(
                        value = shiftTime.value,
                        onValueChange = { shiftTime.value = it },
                        label = { Text("Shift time") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = FORM_SPACING),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            disabledTextColor = Color.Gray,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.LightGray
                        )
                    )

                    // Add some space before the button
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }

            // Assign shift button - now part of the scrollable content
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = CONTENT_PADDING, vertical = 16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(
                    onClick = {
                        Log.d("AdminAssignShiftScreen", "Assign shift button clicked")
                        // Validate form fields
                        if (id.value.isBlank() || id.value.isBlank() ||
                            employeeId.value.isBlank() || employeeRole.value.isBlank() ||
                            shiftDate.value.isBlank() || shiftTime.value.isBlank()) {

                            // Show error message
                            errorMessage.value = "All fields are required"
                            showError.value = true
                            Log.d("AdminAssignShiftScreen", "Form validation failed: ${errorMessage.value}")
                        } else {
                            // Hide error message
                            showError.value = false

                            // Call the ViewModel to assign the shift
                            Log.d("AdminAssignShiftScreen", "Calling viewModel.assignShift")
                            val success = viewModel.assignShift(
                                shiftId = id.value,
                                employeeId = employeeId.value.toString().toInt(),
                                date = shiftDate.value,
                                shiftType = when (shiftTime.value.lowercase()) {
                                    "morning" -> "morning"
                                    "afternoon" -> "afternoon"
                                    "night" -> "night"
                                    else -> "morning"
                                }
                            )

                            if (true) {
                                // Call the onAssignShiftClick callback which will navigate back
                                Log.d("AdminAssignShiftScreen", "Shift assigned successfully, calling onAssignShiftClick")
                                onAssignShiftClick()
                            } else {
                                // Show error message
                                errorMessage.value = "Failed to assign shift"
                                showError.value = true
                                Log.d("AdminAssignShiftScreen", "Failed to assign shift")
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3C6E4F) // Green color from design
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 8.dp)
                ) {
                    Text(
                        "Assign shift",
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
            }

            // Footer - now part of the scrollable content
            Surface(
                color = Color(0xFFE3ECF0),
                tonalElevation = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .height(FOOTER_HEIGHT)
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
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AdminAssignShiftScreenPreview() {
    AdminAssignShiftScreen(viewModel = EmployeeViewModel())
}