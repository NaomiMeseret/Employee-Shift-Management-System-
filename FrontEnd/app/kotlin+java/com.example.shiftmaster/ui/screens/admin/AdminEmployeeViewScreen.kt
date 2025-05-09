package com.example.shiftmaster.ui.screens.admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.shiftmaster.R
import com.example.shiftmaster.ui.components.NavItem
import com.example.shiftmaster.viewmodel.AdminEmployeeViewModel
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.navigationBarsPadding

private val HEADER_HEIGHT = 70.dp
private val FOOTER_HEIGHT = 70.dp
private val CONTENT_PADDING = 16.dp
private val HEADER_NAV_SPACING = 24.dp
private val SECTION_SPACING = 20.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminEmployeeViewScreen(
    navController: NavController,
    viewModel: AdminEmployeeViewModel = viewModel()
) {
    val employees = remember { viewModel.employees }

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
                HeaderRow(
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = CONTENT_PADDING)
                )
            }
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
        ) {
            Spacer(Modifier.height(HEADER_NAV_SPACING))

            // Nav bar
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = CONTENT_PADDING)
                    .background(Color(0xFFB4D9E5), RoundedCornerShape(10.dp))
                    .padding(vertical = 8.dp)
            ) {
                NavItem("Team", selected = true) { /* Already here */ }
                NavItem("Shifts", selected = false) { navController.navigate("shifts") }
                NavItem("Attendance", selected = false) { navController.navigate("attendance") }
            }

            Spacer(Modifier.height(SECTION_SPACING))

            // Scrollable content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                // Section header + Add button
                Column(modifier = Modifier.padding(horizontal = CONTENT_PADDING)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Employees", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                            Text("Manage your team", fontSize = 14.sp, color = Color.Gray)
                        }
                        Button(
                            onClick = { navController.navigate("add_employee") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Add Employee", color = Color.White)
                        }
                    }

                    Spacer(Modifier.height(24.dp))

                    // Table box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .align(Alignment.CenterHorizontally)
                            .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
                    ) {
                        Column {
                            // Table header
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFDDDDDD), RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                                    .padding(vertical = 12.dp, horizontal = 8.dp)
                            ) {
                                Text("Name", fontWeight = FontWeight.Medium, fontSize = 14.sp, modifier = Modifier.weight(1f), color = Color.DarkGray)
                                Text("ID", fontWeight = FontWeight.Medium, fontSize = 14.sp, modifier = Modifier.width(60.dp), textAlign = TextAlign.Center, color = Color.DarkGray)
                                Text("Edit     Delete", fontWeight = FontWeight.Medium, fontSize = 14.sp, modifier = Modifier.width(100.dp), textAlign = TextAlign.Center, color = Color.DarkGray)
                            }

                            // Table rows
                            employees.filter { it.isActive }.forEachIndexed { index, emp ->
                                Column {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color.White)
                                            .padding(vertical = 12.dp, horizontal = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(emp.fullName, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                                            Text(emp.role, fontSize = 12.sp, color = Color.Gray)
                                        }
                                        Box(modifier = Modifier.width(60.dp), contentAlignment = Alignment.Center) {
                                            Text(emp.id, fontSize = 14.sp)
                                        }
                                        Row(
                                            modifier = Modifier.width(100.dp),
                                            horizontalArrangement = Arrangement.SpaceEvenly
                                        ) {
                                            IconButton(onClick = { /* navController.navigate("edit_employee/${emp.id}") */ }) {
                                                Icon(painter = painterResource(R.drawable.ic_edit), contentDescription = "Edit", tint = Color.Gray)
                                            }
                                            IconButton(onClick = { /* handle delete */ }) {
                                                Icon(painter = painterResource(R.drawable.ic_delete), contentDescription = "Delete", tint = Color.Gray)
                                            }
                                        }
                                    }
                                    if (index < employees.filter { it.isActive }.lastIndex)
                                        Divider(color = Color.LightGray, thickness = 1.dp)
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                // Footer
                Surface(
                    color = Color(0xFFE3ECF0),
                    tonalElevation = 2.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .height(FOOTER_HEIGHT)
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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
}

@Composable
private fun HeaderRow(modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        Image(
            painter = painterResource(R.drawable.shiftmaster_logo),
            contentDescription = "Logo",
            modifier = Modifier.size(40.dp)
        )
        Spacer(Modifier.width(8.dp))
        Text("ShiftMaster", fontSize = 20.sp, fontWeight = FontWeight.Bold)
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
        Surface(shape = CircleShape, color = Color.LightGray, modifier = Modifier.size(32.dp)) {}
    }
}


@Preview(showBackground=true)
@Composable
private fun AdminEmployeeViewPreview() {
    AdminEmployeeViewScreen()
}
