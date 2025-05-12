package com.naomi.shiftmaster.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.naomi.shiftmaster.data.model.Employee
import com.naomi.shiftmaster.data.model.Shift
import com.naomi.shiftmaster.data.repository.EmployeeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class EmployeeShiftViewModel : ViewModel() {

    private val repository = EmployeeRepository()

    private val _assignedShifts = MutableStateFlow<List<Shift>>(emptyList())
    val assignedShifts: StateFlow<List<Shift>> = _assignedShifts.asStateFlow()

    private val _employeeId = MutableStateFlow<String>("-1")
    val employeeId: StateFlow<String> = _employeeId.asStateFlow()
    init {
        viewModelScope.launch {



        }
    }


}
