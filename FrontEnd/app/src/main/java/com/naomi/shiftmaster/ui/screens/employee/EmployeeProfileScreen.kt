package com.naomi.shiftmaster.ui.screens.employee

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.naomi.shiftmaster.R
import com.naomi.shiftmaster.ui.components.NavItem
import com.naomi.shiftmaster.viewmodel.EmployeeProfileViewModel
import com.naomi.shiftmaster.viewmodel.EmployeeViewModel
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.Image
import com.naomi.shiftmaster.data.model.ProfileUpdateRequest


private val HEADER_HEIGHT = 70.dp
private val CONTENT_PADDING = 16.dp
private val HEADER_NAV_SPACING = 24.dp
private val SECTION_SPACING = 20.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeProfileScreen(
    onSignOut: () -> Unit = {},
    onNavItemClick: (String) -> Unit,
    viewModel: EmployeeProfileViewModel = viewModel(),
    viewModel2: EmployeeViewModel
) {
    val profileState by viewModel.profileState.collectAsState()
    val isLoading     by viewModel.isLoading.collectAsState()
    val message       by viewModel.message.collectAsState()
    var fullName      by remember { mutableStateOf("") }
    var id            by remember { mutableStateOf("") }
    var phoneNumber   by remember { mutableStateOf("") }
    var position           by remember { mutableStateOf("") }
    var currentPass   by remember { mutableStateOf("") }
    var newPass       by remember { mutableStateOf("") }
    var confirmPass   by remember { mutableStateOf("") }

    val employeeId by viewModel2.currentEmployeeId.collectAsState()

    LaunchedEffect(employeeId) {
        println("🎯 Loaded Employee ID: $employeeId")
        employeeId?.let { id ->
            viewModel.loadUserDetails(id)
        }
    }
    LaunchedEffect(profileState) {
        profileState?.let { p ->
            fullName = p.name
            id = p.id.toString()
            phoneNumber = p.phone
            position = p.position
        }
    }

    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(message) {
        message?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearMessage()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
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
                .verticalScroll(rememberScrollState())
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
                NavItem("Shifts",     selected = false) { onNavItemClick("shifts") }
                NavItem("Attendance", selected = false) { onNavItemClick("attendance") }
                NavItem("Team",       selected = false) { onNavItemClick("team") }
                NavItem("Profile",    selected = true) { /* no-op */ }
            }

            Spacer(Modifier.height(SECTION_SPACING))

            // Main content padded
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = CONTENT_PADDING)
            ) {
                // Header + Sign out
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "User Profile",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Button(
                        onClick = { viewModel.signOut(onSignOut) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E5D47)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Text("Sign out", fontSize = 14.sp)
                    }
                }

                Spacer(Modifier.height(8.dp))
                Text("Manage your personal information", fontSize = 14.sp, color = Color.DarkGray)
                Spacer(Modifier.height(24.dp))

                // Profile picture & name
                Box(
                    Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Surface(
                            shape = CircleShape,
                            color = Color.LightGray,
                            modifier = Modifier
                                .size(100.dp)
                                .border(2.dp, Color.Gray, CircleShape)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                profileState?.let { p ->
                                    Text(
                                        p.name.take(2).uppercase(),
                                        fontSize = 32.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.DarkGray
                                    )
                                } ?: Text("AA", fontSize = 32.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        Spacer(Modifier.height(8.dp))
                        profileState?.let { p ->
                            Text(p.name, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                            Text(if (p.isAdmin) "Admin" else "Employee", fontSize = 14.sp, color = Color.Gray)
                        }
                        Spacer(Modifier.height(16.dp))

                    }
                }

                Spacer(Modifier.height(16.dp))
                ProfileFormField("Full Name", fullName,    onValueChange = { fullName = it })
                Spacer(Modifier.height(16.dp))
                ProfileFormField("ID",        id,          onValueChange = { id = it }, enabled = false)
                Spacer(Modifier.height(16.dp))
                ProfileFormField("Phone Number", phoneNumber, onValueChange = { phoneNumber = it },
                    keyboardType = KeyboardType.Phone)
                Spacer(Modifier.height(16.dp))
                ProfileFormField("Position",       position,         onValueChange = { position = it },
                    placeholder = "What is your role in the company…")
                Spacer(Modifier.height(24.dp))
                // Save button
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Button(
                        onClick = {
                            viewModel.updateProfile(id.toInt(), ProfileUpdateRequest( name = fullName,  phone = phoneNumber, isAdmin = if (position == "Admin" || position == "admin") true else false , position= position))

                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E5D47)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.width(200.dp),
                        enabled = !isLoading
                    ) {
                        if (isLoading) CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                        else Text("Save Changes")
                    }
                }

                Spacer(Modifier.height(SECTION_SPACING))

                // Password fields
                Text("Current password", fontSize = 14.sp, color = Color.DarkGray)
                Spacer(Modifier.height(4.dp))
                OutlinedTextField(
                    value = currentPass,
                    onValueChange = { currentPass = it },
                    placeholder = { Text("Enter your current password") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
                Text("New password", fontSize = 14.sp, color = Color.DarkGray)
                Spacer(Modifier.height(4.dp))
                OutlinedTextField(
                    value = newPass,
                    onValueChange = { newPass = it },
                    placeholder = { Text("Enter new password") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
                Text("Confirm new password", fontSize = 14.sp, color = Color.DarkGray)
                Spacer(Modifier.height(4.dp))
                OutlinedTextField(
                    value = confirmPass,
                    onValueChange = { confirmPass = it },
                    placeholder = { Text("Confirm new password") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(24.dp))

                // Save button
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Button(
                        onClick = {
                            if (currentPass.isNotEmpty() && newPass.isNotEmpty() && newPass == confirmPass) {
                                viewModel.changePassword(id.toInt(),currentPass, newPass, confirmPass )
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E5D47)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.width(200.dp),
                        enabled = !isLoading
                    ) {
                        if (isLoading) CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                        else Text("Change Password")
                    }
                }

                Spacer(Modifier.height(SECTION_SPACING))
            }

            // Full-width footer inside the same scroll
            Surface(
                color = Color(0xFFE3ECF0),
                tonalElevation = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp),
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
private fun ProfileFormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    enabled: Boolean = true
) {
    Column {
        Text(label, fontSize = 14.sp, color = Color.DarkGray)
        Spacer(Modifier.height(4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { if (placeholder.isNotEmpty()) Text(placeholder) },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled
        )
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
        Text("Employee", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color.DarkGray)
        Spacer(Modifier.width(12.dp))
        Surface(shape = CircleShape, color = Color.LightGray, modifier = Modifier.size(32.dp)) { /* avatar */ }
    }
}

@Preview(showBackground = true)
@Composable
private fun EmployeeProfileScreenPreview() {
    EmployeeProfileScreen(onNavItemClick = {}, viewModel2 = EmployeeViewModel())
}
