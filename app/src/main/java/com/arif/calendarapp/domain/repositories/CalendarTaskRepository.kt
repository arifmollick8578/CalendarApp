package com.arif.calendarapp.domain.repositories

import com.arif.calendarapp.data.remote.dto.TaskDetails
import retrofit2.Call

/** Repository class to fetch data in domain layer. */
interface CalendarTaskRepository {
    suspend fun getCalendarTaskLists(userId: Int): Call<List<TaskDetails>>
}
