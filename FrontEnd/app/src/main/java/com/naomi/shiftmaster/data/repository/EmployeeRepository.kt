package com.naomi.shiftmaster.data.repository

import com.naomi.shiftmaster.data.model.*
import com.naomi.shiftmaster.network.RetrofitClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
class EmployeeRepository {

    private val api = RetrofitClient.api
    private val _currentEmployee = MutableStateFlow(
        Employee(
            id = 11,
            name = "Abebe Asefa",
            email = "abebe@example.com",
            phone = "+251980909090",
            position = "Manager",
            password = "",
            profilePicture = "",
            status = "active",
            isAdmin = false,
            attendance = emptyList()
        )
    )
    val currentEmployee: Flow<Employee> = _currentEmployee.asStateFlow()
    fun setCurrentEmployee(data: Employee) {
        _currentEmployee.value = data
    }
    suspend fun updateProfile(id: Int, request: ProfileUpdateRequest): Boolean {
        return try {
            val updated = api.update(id,request)
            _currentEmployee.value = updated
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

//    suspend fun updateProfilePhoto(photoUrl: String): Boolean {
//        return try {
//            val current = _currentEmployee.value.copy(profilePicture = photoUrl)
//            api.update(current.id, current)
//            _currentEmployee.value = current
//            true
//        } catch (e: Exception) {
//            false
//        }
//    }

    suspend fun getAllEmployees(): List<Employee> = api.getAllEmployees()
    suspend fun getEmployee(id: Int): Employee = api.getEmployee(id)
    suspend fun registerEmployee(employee: Employee) = api.register(employee)
    suspend fun updateEmployee(id: Int, employee: ProfileUpdateRequest) = api.update(id, employee)
    suspend fun deleteEmployee(id: Int) = api.delete(id)
    suspend fun login(email: String, password: String): Employee {
        val response = api.login(mapOf("email" to email, "password" to password))
        println("✅✅✅✅✅✅✅✅✅✅✅✅✅✅✅✅✅✅✅✅✅✅ ${response.body()}")
        val employee = response.body()?.employee!!

        return employee
    }

    suspend fun clockIn(id: Int): Employee = api.clockIn(id).body()!!
    suspend fun clockOut(id: Int): Employee = api.clockOut(id).body()!!
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

    suspend fun getAllEmployeesWithStatus(): List<String> {
        val list = api.getAllEmployeesWithStatus().body()
        return list?.mapNotNull { it["status"] as? String } ?: emptyList()
    }

    suspend fun getSingleStatus(id: Int): String? {
        val map = api.getSingleStatus(id).body()
        return map?.get("status") as? String
    }

    suspend fun getAllEmployeesWithAttendance(): List<Attendance> {
        val list = api.getAllEmployeesWithAttendance().body()
        return list?.map {
            Attendance(
                employeeId = (it["id"] as? Double)?.toInt()?.toString() ?: "",
                date = it["date"] as? String ?: "",
                status = it["status"] as? String ?: "",
                clockInTime = it["clockInTime"] as? String ?: ""
            )
        } ?: emptyList()
    }

    suspend fun getSingleAttendance(id: Int): Attendance? {
        val map = api.getSingleAttendance(id).body()
        return map?.let {
            Attendance(
                employeeId = (it["id"] as? Double)?.toInt()?.toString() ?: "",
                date = it["date"] as? String ?: "",
                status = it["status"] as? String ?: "",
                clockInTime = it["clockInTime"] as? String ?: ""
            )
        }
    }
}
