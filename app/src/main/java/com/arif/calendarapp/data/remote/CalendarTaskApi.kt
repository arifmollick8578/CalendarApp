package com.arif.calendarapp.data.remote

import com.arif.calendarapp.data.remote.dto.TaskDetails
import com.arif.calendarapp.data.remote.dto.TaskModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/** API interface to perform REST. */
interface CalendarTaskApi {
    @POST("/api/storeCalendarTask")
    suspend fun storeCalendarTask(user_id: Int, task: TaskModel)

    @POST("/api/getCalendarTaskLists")
    suspend fun getCalendarTaskLists(@Body user_id: Int): Call<List<TaskDetails>>

    @POST("/api/deleteCalendarTask")
    suspend fun deleteCalendarTask(user_id: Int, task_id: Int)
}
