package com.rvr.wearos.network

import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface RvrApi {
    @POST("api/v1/control")
    suspend fun controlLock(
        @Query("action") action: String, // "lock" | "unlock"
        @Query("device_id") deviceId: String
    ): Response<ControlResponse>
}

data class ControlResponse(
    val success: Boolean,
    val message: String?
)
