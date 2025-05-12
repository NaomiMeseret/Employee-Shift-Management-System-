package com.naomi.shiftmaster.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naomi.shiftmaster.data.model.Shift
import com.naomi.shiftmaster.data.repository.EmployeeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AdminAssignShiftViewModel : ViewModel() {

    private val repository = EmployeeRepository()

    private val _singleAssignedShift = MutableStateFlow<List<Shift>>(emptyList())
    val singleAssignedShift: StateFlow<List<Shift>> = _singleAssignedShift.asStateFlow()


} 
