package com.naomi.shiftmaster.ui.screens.admin

import androidx.compose.runtime.*

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
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
import androidx.media3.exoplayer.offline.Download
import com.naomi.shiftmaster.R
import com.naomi.shiftmaster.ui.components.NavItem
import com.naomi.shiftmaster.viewmodel.AdminAttendanceViewModel

private val HEADER_HEIGHT = 70.dp
private val FOOTER_HEIGHT = 70.dp
private val CONTENT_PADDING = 16.dp
private val HEADER_NAV_SPACING = 24.dp
private val SECTION_SPACING = 20.dp
private val TABLE_SPACING = 24.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminAttendanceScreen(
    onNavItemClick: (String) -> Unit = {},
    onSignOut: () -> Unit = {},
    viewModel: AdminAttendanceViewModel = viewModel()
) {
    Log.d("AdminAttendanceScreen", "Screen composed")
    val groupedAttendance by  viewModel.groupedAttendance.collectAsState(emptyList())
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
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(HEADER_NAV_SPACING))

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
                NavItem("Shifts", selected = false) { onNavItemClick("shifts") }
                NavItem("Attendance", selected = true) { onNavItemClick("attendance") }
            }
            Spacer (Modifier.height(SECTION_SPACING))

            Column(modifier = Modifier.padding(horizontal = CONTENT_PADDING)) {
                Button(
                    onClick = { viewModel.signOut(onSignOut) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E5D47)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(36.dp)
                ) {
                    Text("Sign out", fontSize = 14.sp)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Attendance Tracker",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    IconButton(onClick = { viewModel.downloadAttendanceData() }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Export",
                            tint = Color.Black
                        )
                    }
                }

                
                groupedAttendance.forEachIndexed { dateIndex, dailyData ->
                    Text(
                        dailyData.date,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
                    ) {
                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFDDDDDD), RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                                    .padding(vertical = 12.dp, horizontal = 8.dp)
                            ) {
                                Text("Name", fontWeight = FontWeight.Medium, fontSize = 14.sp, modifier = Modifier.weight(1f), color = Color.DarkGray)
                                Text("ID", fontWeight = FontWeight.Medium, fontSize = 14.sp, modifier = Modifier.width(80.dp), textAlign = TextAlign.Center, color = Color.DarkGray)
                                Text("Shift", fontWeight = FontWeight.Medium, fontSize = 14.sp, modifier = Modifier.width(80.dp), textAlign = TextAlign.Center, color = Color.DarkGray)
                                Text("Present", fontWeight = FontWeight.Medium, fontSize = 14.sp, modifier = Modifier.width(80.dp), textAlign = TextAlign.Center, color = Color.DarkGray)
                            }

                            dailyData.records.forEachIndexed { index, record ->
                                Column {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color.White)
                                            .padding(vertical = 12.dp, horizontal = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(record.employee.name, fontWeight = FontWeight.Medium, fontSize = 14.sp)
//                                            Text(record.employee.role, fontSize = 12.sp, color = Color.Gray)
                                        }
                                        Box(modifier = Modifier.width(80.dp), contentAlignment = Alignment.Center) {
                                            Text(record.employee.id.toString(), fontSize = 14.sp)
                                        }
                                        Box(modifier = Modifier.width(80.dp), contentAlignment = Alignment.Center) {
                                            Text(record.shiftType, fontSize = 14.sp)
                                        }
                                        Box(modifier = Modifier.width(80.dp), contentAlignment = Alignment.Center) {
                                            if (record.isPresent) {
                                                Icon(Icons.Default.Check, contentDescription = "Present", tint = Color.Green)
                                            } else {
                                                Icon(Icons.Default.Close, contentDescription = "Absent", tint = Color.Red)
                                            }
                                        }
                                    }
                                    if (index < dailyData.records.lastIndex) Divider(color = Color.LightGray, thickness = 1.dp)
                                }
                            }
                        }
                    }

                  
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

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

@Preview(showBackground = true)
@Composable
private fun AdminAttendanceScreenPreview() {
    AdminAttendanceScreen()
}