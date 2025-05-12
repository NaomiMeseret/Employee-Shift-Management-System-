package com.naomi.shiftmaster.ui.screens.admin

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.naomi.shiftmaster.viewmodel.EmployeeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import android.app.DatePickerDialog
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import java.util.Calendar
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.naomi.shiftmaster.R
import com.naomi.shiftmaster.data.model.Employee
import kotlinx.coroutines.launch

// Consistent header and footer height
private val HEADER_HEIGHT = 70.dp
private val FOOTER_HEIGHT = 70.dp
// Spacing
private val CONTENT_PADDING = 16.dp
private val HEADER_NAV_SPACING = 24.dp
private val SECTION_SPACING = 20.dp
private val FORM_SPACING = 16.dp

@Composable
fun AdminEditShiftScreen(
    employeeId: String,
    shiftId: String,
    onNavItemClick: (String) -> Unit = {},
    onBackClick: () -> Unit = {},
    onSaveClick: () -> Unit = {},
    viewModel: EmployeeViewModel

) {
    //sample to get started with
    val employee: Employee =Employee(
        id = 11,
        name = "Abebe Asefa",
        email = "abebe@example.com",
        phone = "+251980909090",
        position = "Manager",
        password = "",
        profilePicture = "",
        status = "active",
        isAdmin = false,
        attendance = emptyList()
    )


    LaunchedEffect(employeeId) {
        val employee = viewModel.fetchEmployee(employeeId.toIntOrNull() ?: return@LaunchedEffect)
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var shiftType by remember { mutableStateOf("") }
    var shiftDate by remember { mutableStateOf("") }

    val context = LocalContext.current
    val showDatePicker = remember { mutableStateOf(false) }
    val shiftOptions = listOf("Morning", "Afternoon", "Night")
    val expanded = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {

            // === Inline Header ===
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



                Spacer(modifier = Modifier.height(16.dp))

                // === shift time ===
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp))
                        .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {

                    Text(
                        text = "Edit a Shift",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    employee?.let {
                        Text(text = "Employee Name: ${it.name}", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(text = "Employee Id: ${it.id}", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(text = "Employee Position: ${it.position}", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Box {
                        OutlinedTextField(
                            value = shiftType,
                            onValueChange = {},
                            label = { Text("Shift Type") },
                            modifier = Modifier.fillMaxWidth(),
                            readOnly = true,
                            trailingIcon = {
                                IconButton(onClick = { expanded.value = true }) {
                                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                                }
                            }
                        )
                        DropdownMenu(
                            expanded = expanded.value,
                            onDismissRequest = { expanded.value = false }
                        ) {
                            shiftOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        shiftType = option
                                        expanded.value = false
                                    }
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                  Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = shiftDate,
                        onValueChange = {},
                        label = { Text("Shift Date") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showDatePicker.value = true },
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { showDatePicker.value = true }) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = "Pick Date"
                                )
                            }
                        }
                    )
                }
                if (showDatePicker.value) {
                    val calendar = Calendar.getInstance()
                    DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            shiftDate = "$year-${month + 1}-${dayOfMonth}"
                            showDatePicker.value = false
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }


                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            viewModel.updateShift(shiftId.toInt(), shiftType, shiftDate)



                            coroutineScope.launch {

                                        snackbarHostState.showSnackbar("Shift Edited")



                            }
                            onSaveClick()

                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF216043)),
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Save Edit", color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun AdminEditShiftScreennPreview() {
    val navController = rememberNavController()
    AdminEditShiftScreen(employeeId = "-1", shiftId = "-1", viewModel = EmployeeViewModel())
}

