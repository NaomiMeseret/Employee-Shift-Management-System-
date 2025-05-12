package com.naomi.shiftmaster.ui.screens.employee

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.naomi.shiftmaster.R
import com.naomi.shiftmaster.ui.components.NavItem
import com.naomi.shiftmaster.viewmodel.EmployeeShiftViewModel
import com.naomi.shiftmaster.viewmodel.EmployeeViewModel

// Consistent header and footer height
private val HEADER_HEIGHT = 70.dp
private val FOOTER_HEIGHT = 70.dp
// Spacing
private val CONTENT_PADDING = 16.dp
private val HEADER_NAV_SPACING = 24.dp
private val SECTION_SPACING = 20.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeShiftScreen(
    onNavItemClick: (String) -> Unit,
    viewModel: EmployeeViewModel
) {
    val shifts by viewModel.assignedShifts.collectAsState()
    val employeeId by viewModel.currentEmployeeId.collectAsState()

    LaunchedEffect(employeeId) {
        println("🎯 Loaded Employee ID: $employeeId")
        employeeId?.let { id ->
            viewModel.loadAssignedShifts(id)
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding() // This positions it just below the status bar/camera
                ) {
                    Surface(
                        color = Color(0xFFE3ECF0),
                        tonalElevation = 2.dp,
                        shadowElevation = 4.dp, // Added shadow for better visibility
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(HEADER_HEIGHT)
                    ) {
                        HeaderRow(
                            Modifier
                                .fillMaxSize()
                                .padding(horizontal = CONTENT_PADDING)
                        )
                    }
                }
            },
            contentWindowInsets = WindowInsets(0, 0, 0, 0)
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                // Increased spacing between header and nav bar
                Spacer(Modifier.height(HEADER_NAV_SPACING))

                // — Nav Bar —
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = CONTENT_PADDING)
                        .background(Color(0xFFB4D9E5), RoundedCornerShape(10.dp))
                        .padding(vertical = 8.dp)
                ) {
                    NavItem("Shifts",     selected = true)  { onNavItemClick("shifts") }
                    NavItem("Attendance", selected = false) { onNavItemClick("attendance") }
                    NavItem("Team",       selected = false) { onNavItemClick("team") }
                    NavItem("Profile",    selected = false) { onNavItemClick("profile") }
                }

                Spacer(Modifier.height(SECTION_SPACING))

                // — Calendar Section —
                Column(modifier = Modifier.padding(horizontal = CONTENT_PADDING)) {
                    Text("Calendar", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                    Text("view shifts", fontSize = 14.sp, color = Color.Gray)
                    Spacer(Modifier.height(8.dp))

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.calendar),
                            contentDescription = "Calendar",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                        )
                    }
                }

                Spacer(Modifier.height(SECTION_SPACING))

                // — Assigned Shifts —
                Column(
                    modifier = Modifier.padding(horizontal = CONTENT_PADDING),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text("Assigned shifts", fontSize = 16.sp, fontWeight = FontWeight.Medium)

                    shifts.forEach { shift ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFEDEDED), RoundedCornerShape(12.dp))
                                .padding(12.dp)
                        ) {
                            Column {
                                Text(
                                    text = "Employee: ${shift.employeeId}",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "Date: ${shift.date}",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }

                            Spacer(Modifier.weight(1f))

                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = when (shift.shiftType) {
                                    "Morning" -> Color(0xFF2E5D47)
                                    "Evening" -> Color.Gray
                                    else      -> Color.DarkGray
                                }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(80.dp)
                                        .padding(vertical = 4.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = shift.shiftType,
                                        color = Color.White,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(SECTION_SPACING))

                // — My Shift Button —
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Button(
                        onClick = { /* TODO */ },
                        modifier = Modifier
                            .width(200.dp)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1C2B24)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("My Shift", color = Color.White)
                    }
                }

                // Add consistent bottom spacing
                Spacer(Modifier.height(SECTION_SPACING))

                // Footer - now part of the scrollable content
                Surface(
                    color = Color(0xFFE3ECF0),
                    tonalElevation = 2.dp,
                    shadowElevation = 4.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding() // This positions it above the navigation bar
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "©2025 ShiftMaster. All rights reserved",
                            fontSize = 12.sp,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = CONTENT_PADDING)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HeaderRow(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(R.drawable.shiftmaster_logo),
            contentDescription = "Logo",
            modifier = Modifier.size(40.dp)
        )
        Spacer(Modifier.width(8.dp))
        Text("ShiftMaster", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.weight(1f))
        Text(
            "Employee",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color.DarkGray
        )
        Spacer(Modifier.width(12.dp))
        Surface(
            shape = CircleShape,
            color = Color.LightGray,
            modifier = Modifier.size(32.dp)
        ) { /* avatar placeholder */ }
    }
}

@Preview(showBackground = true)
@Composable
private fun EmployeeShiftScreenPreview() {
    EmployeeShiftScreen(onNavItemClick = {}, viewModel = EmployeeViewModel())
}