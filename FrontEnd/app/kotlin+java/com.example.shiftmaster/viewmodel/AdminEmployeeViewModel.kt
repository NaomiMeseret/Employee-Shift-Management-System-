package com.example.shiftmaster.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.shiftmaster.data.model.Employee

class AdminEmployeeViewModel : ViewModel() {
    // List of employees
    private val _employees = mutableStateListOf<Employee>()
    val employees: List<Employee> get() = _employees

    init {
        // Load sample data
        loadSampleEmployees()
    }

    private fun loadSampleEmployees() {
        val sampleEmployees = listOf(
            Employee("0001", "Abebe Kebede", "Manager", phoneNumber = "0911223344", isActive = true),
            Employee("1002", "Selam Tesfaye", "HR Officer", phoneNumber = "0922334455", isActive = true),
            Employee("1003", "Mulugeta Bekele", "Software Developer", phoneNumber = "0933445566", isActive = true),
            Employee("1004", "Hana Mekonnen", "Marketing Specialist", phoneNumber = "0944556677", isActive = true),
            Employee("1005", "Yared Alemu", "Accountant", phoneNumber = "0955667788", isActive = true),
            Employee("1006", "Liya Getachew", "Receptionist", phoneNumber = "0966778899", isActive = true),
            Employee("1007", "Kebede Tsegaye", "Security Guard", phoneNumber = "0977889900", isActive = true),
            Employee("1008", "Mekdes Worku", "Project Coordinator", phoneNumber = "0988990011", isActive = true),
            Employee("1010", "Samuel Fikadu", "IT Support", phoneNumber = "0999001122", isActive = true),
            Employee("1011", "Eden Abate", "Business Analyst", phoneNumber = "0900112233", isActive = true),
            Employee("1012", "Daniel Tefera", "Finance Officer", phoneNumber = "0911223355", isActive = true)
        )

        _employees.addAll(sampleEmployees)
    }

    fun addEmployee(employee: Employee) {
        _employees.add(employee)
    }

    fun deleteEmployee(employeeId: String) {
        // Find the employee and set isActive to false (soft delete)
        val index = _employees.indexOfFirst { it.id == employeeId }
        if (index != -1) {
            val employee = _employees[index]
            _employees[index] = employee.copy(isActive = false)
        }
    }
}