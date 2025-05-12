package com.naomi.shiftmaster.ui.screens.employee

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.navigationBarsPadding
import com.naomi.shiftmaster.R
import com.naomi.shiftmaster.ui.components.NavItem
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

// Consistent header and footer height
private val HEADER_HEIGHT    = 70.dp
private val FOOTER_HEIGHT    = 70.dp
// Spacing
private val CONTENT_PADDING    = 16.dp
private val HEADER_NAV_SPACING = 24.dp
private val SECTION_SPACING    = 20.dp

data class ActivityItem(val actionType: String, val date: String, val time: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeAttendanceScreen(
    onNavItemClick: (String) -> Unit
) {
    val recentActivities = remember {
        mutableStateListOf(
            ActivityItem("Clock In",  "Today",     "08:00"),
            ActivityItem("Clock Out", "Yesterday","16:00"),
            ActivityItem("Clock Out", "Yesterday","18:00")
        )
    }
    val currentTime = remember { mutableStateOf("00:00:00") }

    LaunchedEffect(Unit) {
        val fmt = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        while (true) {
            currentTime.value = fmt.format(Date())
            delay(1000)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                ) {
                    Surface(
                        color = Color(0xFFE3ECF0),
                        tonalElevation = 2.dp,
                        shadowElevation = 4.dp,
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
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(bottom = FOOTER_HEIGHT)
                    .verticalScroll(rememberScrollState())
            ) {
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
                    NavItem("Shifts",     selected = false) { onNavItemClick("shifts") }
                    NavItem("Attendance", selected = true ) { /* current */ }
                    NavItem("Team",       selected = false) { onNavItemClick("team") }
                    NavItem("Profile",    selected = false) { onNavItemClick("profile") }
                }

                Spacer(Modifier.height(SECTION_SPACING))

                // — Attendance Tracking Section —
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = CONTENT_PADDING)
                        .background(Color(0xFFEDEDED), RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Attendance Tracking", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Clock in/out and view attendance records",
                            fontSize = 14.sp,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(24.dp))
                        Text(currentTime.value, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                        Text("Current Time", fontSize = 14.sp, color = Color.DarkGray)
                        Spacer(Modifier.height(24.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Button(
                                onClick = {
                                    val fmt = SimpleDateFormat("HH:mm", Locale.getDefault())
                                    recentActivities.add(
                                        0,
                                        ActivityItem("Clock In", "Today", fmt.format(Date()))
                                    )
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .padding(end = 8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E5D47)),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Clock In", color = Color.White)
                            }
                            Button(
                                onClick = {
                                    val fmt = SimpleDateFormat("HH:mm", Locale.getDefault())
                                    recentActivities.add(
                                        0,
                                        ActivityItem("Clock Out", "Today", fmt.format(Date()))
                                    )
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .padding(start = 8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Clock Out", color = Color.Black)
                            }
                        }
                    }
                }

                Spacer(Modifier.height(SECTION_SPACING))

                // — Recent Activity —
                Column(modifier = Modifier.padding(horizontal = CONTENT_PADDING)) {
                    Text("Recent Activity", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    recentActivities.forEachIndexed { index, activity ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(activity.actionType, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                                Text(activity.date, fontSize = 14.sp, color = Color.Gray)
                            }
                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = Color(0xFFD3D3D3)
                            ) {
                                Text(
                                    activity.time,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                )
                            }
                        }
                        if (index < recentActivities.size - 1) Divider(color = Color.LightGray, thickness = 1.dp)
                    }
                }

                Spacer(Modifier.height(SECTION_SPACING))
            }
        }

        // — Fixed, full-width footer matching Profile page —
        Surface(
            color = Color(0xFFE3ECF0),
            tonalElevation = 2.dp,
            shadowElevation = 4.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .navigationBarsPadding()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(FOOTER_HEIGHT),  // Height on the Box ensures correct 70.dp
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "©2025 ShiftMaster. All rights reserved",
                    fontSize = 12.sp,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun HeaderRow(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.shiftmaster_logo),
            contentDescription = "Logo",
            modifier = Modifier.size(40.dp)
        )
        Spacer(Modifier.width(8.dp))
        Text("ShiftMaster", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.weight(1f))
        Text("Employee", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color.DarkGray)
        Spacer(Modifier.width(12.dp))
        Surface(shape = CircleShape, color = Color.LightGray, modifier = Modifier.size(32.dp)) { /* avatar */ }
    }
}

@Preview(showBackground = true)
@Composable
private fun EmployeeAttendancePreview() {
    EmployeeAttendanceScreen(onNavItemClick = {})
}
