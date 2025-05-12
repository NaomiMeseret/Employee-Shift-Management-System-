package com.naomi.shiftmaster.network
import com.naomi.shiftmaster.data.model.Shift
import retrofit2.Response
import retrofit2.http.*
import com.naomi.shiftmaster.data.model.Employee
import com.naomi.shiftmaster.data.model.LoginResponse
import com.naomi.shiftmaster.data.model.PasswordChangeRequest
import com.naomi.shiftmaster.data.model.ProfileUpdateRequest
import com.naomi.shiftmaster.data.model.ShiftResponse
import com.naomi.shiftmaster.data.model.ShiftUpdate

import okhttp3.ResponseBody

interface ApiService {
    @GET("employees")
    suspend fun getAllEmployees(): List<Employee>

    @GET("employees/{id}")
    suspend fun getEmployee(@Path("id") id: Int): Employee

    @POST("register")
    suspend fun register(@Body employee: Employee): ResponseBody

    @PUT("updateEmployee/{id}")
    suspend fun update(@Path("id") id: Int, @Body employee: ProfileUpdateRequest): Employee

    @DELETE("deleteEmployee/{id}")
    suspend fun delete(@Path("id") id: Int): ResponseBody

    @POST("login")
    suspend fun login(@Body credentials: Map<String, String>): Response<LoginResponse>

    @POST("clockin/{id}")
    suspend fun clockIn(@Path("id") id: Int): Response<Employee>

    @POST("clockout/{id}")
    suspend fun clockOut(@Path("id") id: Int): Response<Employee>

    @POST("assignShift/{id}")
    suspend fun assignShift(@Path("id") id: Int, @Body shift: Map<String, String>): Response<Shift>

    @GET("assignedShift/{id}")
    suspend fun getAssignedShift(@Path("id") id: Int): Response<ShiftResponse>

    @GET("assignedShift")
    suspend fun getAllAssignedShifts(): Response<List<Shift>>

    @GET("status")
    suspend fun getAllEmployeesWithStatus(): Response<List<Map<String, Any>>>

    @GET("status/{id}")
    suspend fun getSingleStatus(@Path("id") id: Int): Response<Map<String, Any>>

    @GET("attendance")
    suspend fun getAllEmployeesWithAttendance(): Response<List<Map<String, Any>>>

    @GET("attendance/{id}")
    suspend fun getSingleAttendance(@Path("id") id: Int): Response<Map<String, Any>>

    @PUT("shift/{id}")
    suspend fun updateShift(@Path("id") id: String, @Body shift: ShiftUpdate): ResponseBody

    @DELETE("shift/{id}")
    suspend fun deleteShift(@Path("id") id: String): Response<ResponseBody>

    @POST("changePassword/{id}")
    suspend fun changePassword(@Path("id") id: Int, @Body request: PasswordChangeRequest): Response<ResponseBody>
}