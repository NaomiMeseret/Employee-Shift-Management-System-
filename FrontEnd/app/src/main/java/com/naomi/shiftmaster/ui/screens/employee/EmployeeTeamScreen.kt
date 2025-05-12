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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.naomi.shiftmaster.R
import com.naomi.shiftmaster.data.model.Employee
import com.naomi.shiftmaster.ui.components.NavItem
import com.naomi.shiftmaster.viewmodel.AdminEmployeeViewModel
import com.naomi.shiftmaster.viewmodel.EmployeeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeTeamScreen(
    onNavItemClick: (String) -> Unit,
    viewModel2: AdminEmployeeViewModel = viewModel(),
    viewModel: EmployeeViewModel
) {
    val employees by viewModel2.employees.collectAsState(emptyList())
    Scaffold(
        topBar = {
            Surface(
                color = Color(0xFFE3ECF0),
                tonalElevation = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
            ) {
                HeaderRow(Modifier.padding(horizontal = 16.dp, vertical = 10.dp))
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(12.dp))

            // — Nav Bar —
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .background(Color(0xFFB4D9E5), RoundedCornerShape(10.dp))
                    .padding(vertical = 6.dp)
            ) {
                NavItem("Shifts",     selected = false) { onNavItemClick("shifts") }
                NavItem("Attendance", selected = false) { onNavItemClick("attendance") }
                NavItem("Team",       selected = true)  { onNavItemClick("team") }
                NavItem("Profile",    selected = false) { onNavItemClick("profile") }
            }

            Spacer(Modifier.height(24.dp))

            // — Team Content —
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text("Team Members", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                Text("view your team", fontSize = 14.sp, color = Color.Gray)
                Spacer(Modifier.height(16.dp))

                // Placeholder content for team members
                employees.forEach { employee: Employee  ->
                    TeamMemberCard(
                        name = employee.name,
                        role = employee.position,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // — Footer —
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE3ECF0))
                    .padding(vertical = 16.dp),
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
private fun TeamMemberCard(name: String, role: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEDEDED))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar placeholder
            Surface(
                shape = CircleShape,
                color = Color.LightGray,
                modifier = Modifier.size(50.dp)
            ) { /* avatar placeholder */ }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = role,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
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