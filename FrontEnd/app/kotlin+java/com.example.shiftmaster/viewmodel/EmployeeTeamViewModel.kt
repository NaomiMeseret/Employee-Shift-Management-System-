package com.example.shiftmaster.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.shiftmaster.data.model.TeamMember

class EmployeeTeamViewModel : ViewModel() {
    // Sample team members data
    private val _teamMembers = mutableStateOf(
        listOf(
            TeamMember(
                id = "1",
                name = "Abebe Asefa",
                role = "Manager",
                status = "Active"
            ),
            TeamMember(
                id = "2",
                name = "Kebron Agegne",
                role = "supervisor",
                status = "Active"
            ),
            TeamMember(
                id = "3",
                name = "Mikhael Siyum",
                role = "Employee",
                status = "On leave"
            ),
            TeamMember(
                id = "4",
                name = "Sarah Asrat",
                role = "Employee",
                status = "Active"
            ),
            TeamMember(
                id = "5",
                name = "Zeritu Tesfa",
                role = "Employee",
                status = "On leave"
            )
        )
    )

    val teamMembers: State<List<TeamMember>> = _teamMembers


    fun fetchTeamMembers() {

    }

    // Filter team members by status
    fun filterByStatus(status: String): List<TeamMember> {
        return _teamMembers.value.filter { it.status == status }
    }

    // Search team members by name
    fun searchByName(query: String): List<TeamMember> {
        if (query.isBlank()) return _teamMembers.value
        return _teamMembers.value.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }
}