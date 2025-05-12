package com.naomi.shiftmaster.data.repository

import com.naomi.shiftmaster.data.model.*
import com.naomi.shiftmaster.network.RetrofitClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
class EmployeeRepository {

    private val api = RetrofitClient.api


    suspend fun getAllEmployees(): List<Employee> = api.getAllEmployees()
    suspend fun getEmployee(id: Int): Employee = api.getEmployee(id)
    suspend fun registerEmployee(employee: Employee) = api.register(employee)
    suspend fun updateEmployee(id: Int, employee: ProfileUpdateRequest) = api.update(id, employee)
    suspend fun deleteEmployee(id: Int) = api.delete(id)
    suspend fun login(email: String, password: String): Employee {
        val response = api.login(mapOf("email" to email, "password" to password))
        val employee = response.body()?.employee!!
        return employee
    }



    suspend fun assignShift(shiftId: String, employeeId: Int, date: String, shiftType: String) {
       api.assignShift(employeeId, mapOf("date" to date, "shiftType" to shiftType, "shiftId" to shiftId))

    }
    suspend fun getAssignedShift(id: Int): List<Shift> {
        return api.getAssignedShift(id).body()?.shifts ?:  emptyList()
    }

    suspend fun getAllAssignedShifts(): List<Shift> {
        return api.getAllAssignedShifts().body() ?: emptyList()
    }

    suspend fun updateShift(id: String, updates: ShiftUpdate) {
        api.updateShift(id, updates)
    }

    suspend fun deleteShift(id: String): Boolean {
        return try {
            api.deleteShift(id)
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun changePassword(id: Int, request: PasswordChangeRequest): Boolean {
        return try {
            api.changePassword(id, request)
            true
        } catch (e: Exception) {
            false
        }
    }


}
