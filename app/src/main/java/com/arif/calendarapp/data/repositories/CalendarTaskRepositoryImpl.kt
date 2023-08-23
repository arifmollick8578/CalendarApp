package com.arif.calendarapp.data.repositories

import com.arif.calendarapp.data.remote.CalendarTaskApi
import com.arif.calendarapp.data.remote.dto.TaskDetails
import com.arif.calendarapp.domain.repositories.CalendarTaskRepository
import retrofit2.Call

class CalendarTaskRepositoryImpl constructor(private val api: CalendarTaskApi) :
    CalendarTaskRepository {
    override suspend fun getCalendarTaskLists(userId: Int): Call<List<TaskDetails>> {
        return api.getCalendarTaskLists(userId)
    }
}
