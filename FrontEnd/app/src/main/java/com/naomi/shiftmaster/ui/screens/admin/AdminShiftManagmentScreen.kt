package com.naomi.shiftmaster.ui.screens.admin

import android.app.LauncherActivity
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.naomi.shiftmaster.R
import com.naomi.shiftmaster.ui.components.NavItem
import com.naomi.shiftmaster.viewmodel.AdminShiftViewModel
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// Consistent header and footer height
private val HEADER_HEIGHT = 70.dp
private val FOOTER_HEIGHT = 70.dp
// Spacing
private val CONTENT_PADDING = 16.dp
private val HEADER_NAV_SPACING = 24.dp
private val SECTION_SPACING = 20.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminShiftManagementScreen(
    onNavItemClick: (String) -> Unit,
    onAssignShiftClick: () -> Unit,
    onEditClick: (String, String) -> Unit,
    onDeleteClick: (String) -> Unit,
    viewModel: AdminShiftViewModel = viewModel()
) {

    val shiftItems by viewModel.shiftWithEmployees.collectAsState(emptyList())



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
                NavItem("Team", selected = false) { onNavItemClick("team") }
                NavItem("Shifts", selected = true) { onNavItemClick("shifts") }
                NavItem("Attendance", selected = false) { onNavItemClick("attendance") }
            }

            Spacer(Modifier.height(SECTION_SPACING))

            // Scrollable content including header, table, footer
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                // Wrapped content with padding for header & table only
                Column(modifier = Modifier.padding(horizontal = CONTENT_PADDING)) {
                    // Section header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "Shift management",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                "Manage shift",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                        Button(
                            onClick = onAssignShiftClick,
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Assign shift", color = Color.White)
                        }
                    }

                    Spacer(Modifier.height(24.dp)) // more space above table

                    // Table box with narrower width
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .align(Alignment.CenterHorizontally)
                            .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
                    ) {
                        Column {
                            // Header row
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFDDDDDD), RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                                    .padding(vertical = 12.dp, horizontal = 8.dp)
                            ) {
                                Text(
                                    "Name",
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp,
                                    modifier = Modifier.weight(1f),
                                    color = Color.DarkGray
                                )
                                Text(
                                    "Shift",
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp,
                                    modifier = Modifier.width(100.dp),
                                    textAlign = TextAlign.Center,
                                    color = Color.DarkGray
                                )
                                Text(
                                    "Edit     Delete",
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp,
                                    modifier = Modifier.width(100.dp),
                                    textAlign = TextAlign.Center,
                                    color = Color.DarkGray
                                )

                            }

                            // Employee rows (now using shiftItems)
                            shiftItems.forEachIndexed { index, shift ->
                                Column {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color.White)
                                            .padding(vertical = 12.dp, horizontal = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(shift.employee.name, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                                            Text(shift.employee.position, fontSize = 12.sp, color = Color.Gray)
                                        }
                                        Box(modifier = Modifier.width(100.dp), contentAlignment = Alignment.Center) {
                                            Text(shift.shiftType, fontSize = 14.sp)
                                        }
                                        Row(
                                            modifier = Modifier.width(100.dp),
                                            horizontalArrangement = Arrangement.SpaceEvenly
                                        ) {
                                            IconButton(onClick = { onEditClick(shift.employee.id.toString(), shift.id) }) {
                                                Icon(painter = painterResource(R.drawable.ic_edit), contentDescription = "Edit", tint = Color.Gray)
                                            }
                                            IconButton(onClick = { onDeleteClick(shift.id) }) {
                                                Icon(painter = painterResource(R.drawable.ic_delete), contentDescription = "Delete", tint = Color.Gray)
                                            }
                                        }
                                    }
                                    if (index < shiftItems.lastIndex) Divider(color = Color.LightGray, thickness = 1.dp)
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp)) // space before footer

                // Footer inside scroll, edge-to-edge
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
}

@Composable
private fun HeaderRow(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Image(painter = painterResource(R.drawable.shiftmaster_logo), contentDescription = "Logo", modifier = Modifier.size(40.dp))
        Spacer(Modifier.width(8.dp))
        Text("ShiftMaster", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.weight(1f))
        Surface(shape = RoundedCornerShape(16.dp), color = Color.Black, modifier = Modifier.padding(end = 12.dp)) {
            Text("Admin", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color.White, modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp))
        }
        Surface(shape = CircleShape, color = Color.LightGray, modifier = Modifier.size(32.dp)) {}
    }
}

@Preview(showBackground = true)
@Composable
private fun AdminShiftManagementScreenPreview() {
    AdminShiftManagementScreen(
        onNavItemClick = {},
        onAssignShiftClick = {},
        onEditClick = { _, _ -> },
        onDeleteClick = {}
    )
}