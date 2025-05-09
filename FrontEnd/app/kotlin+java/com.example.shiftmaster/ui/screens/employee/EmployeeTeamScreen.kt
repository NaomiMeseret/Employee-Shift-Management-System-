package com.example.shiftmaster.ui.screens.employee

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.shiftmaster.R
import com.example.shiftmaster.data.model.TeamMember
import com.example.shiftmaster.ui.components.NavItem
import com.example.shiftmaster.viewmodel.EmployeeTeamViewModel

// Consistent header and footer height
private val HEADER_HEIGHT = 70.dp
private val FOOTER_HEIGHT = 70.dp
// Spacing
private val CONTENT_PADDING = 16.dp
private val HEADER_NAV_SPACING = 24.dp
private val SECTION_SPACING = 20.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeTeamScreen(
    onNavItemClick: (String) -> Unit,
    viewModel: EmployeeTeamViewModel = viewModel()
) {
    val teamMembers by viewModel.teamMembers

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
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
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            containerColor = Color.White // Set the background color to white
        ) { padding ->
            // Main column that fills the screen
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(Color.White) // Ensure white background
            ) {

                Column(
                    modifier = Modifier
                        .weight(1f) // Take all available space
                        .fillMaxWidth()
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
                        NavItem("Shifts",     selected = false) { onNavItemClick("shifts") }
                        NavItem("Attendance", selected = false) { onNavItemClick("attendance") }
                        NavItem("Team",       selected = true)  { onNavItemClick("team") }
                        NavItem("Profile",    selected = false) { onNavItemClick("profile") }
                    }

                    Spacer(Modifier.height(SECTION_SPACING))

                    // — Team Members Section —
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = CONTENT_PADDING)
                            .background(Color(0xFFF2F2F2), RoundedCornerShape(12.dp))
                            .padding(16.dp)
                    ) {
                        Text(
                            "Team Members",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        // Team members list
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            teamMembers.forEach { member ->
                                TeamMemberItem(member)
                            }
                        }
                    }

                    // Add consistent bottom spacing
                    Spacer(Modifier.height(SECTION_SPACING))


                    Spacer(Modifier.weight(1f, fill = false))
                }

                // Footer
                Surface(
                    color = Color(0xFFE3ECF0),
                    tonalElevation = 2.dp,
                    shadowElevation = 4.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .height(FOOTER_HEIGHT)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
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
private fun TeamMemberItem(member: TeamMember) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {

        Surface(
            shape = CircleShape,
            color = Color.LightGray,
            modifier = Modifier.size(48.dp),
            contentColor = Color.White
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                // Show initials based on name
                val initials = getInitials(member.name)
                Text(
                    text = initials,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(Modifier.width(12.dp))

        // Name and role
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = member.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = member.role,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        // Status pill
        val isActive = member.status == "Active"
        val statusColor = if (isActive) Color(0xFF2E5D47) else Color(0xFF6E7E7A)
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = statusColor
        ) {
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .padding(vertical = 8.dp, horizontal = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = member.status,
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }
    }
}

/**
 * Helper function to get initials from a name
 */
private fun getInitials(name: String): String {
    val parts = name.split(" ")
    return when {
        parts.isEmpty() -> ""
        parts.size == 1 -> parts[0].take(1).uppercase()
        else -> "${parts[0].take(1)}${parts.last().take(1)}".uppercase()
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
private fun EmployeeTeamScreenPreview() {
    EmployeeTeamScreen(onNavItemClick = {})
}