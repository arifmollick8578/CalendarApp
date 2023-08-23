package com.arif.calendarapp.data.repositories

import com.arif.calendarapp.data.remote.dto.TaskDetails
import com.arif.calendarapp.data.remote.dto.TaskModel

/** Created fake data source to render static UI to daily task. */
object FakeRepository {
    private val title = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    /** Returned mock calendar tasks to display on UI. */
    fun getAllCalendarEvent(): List<TaskDetails> {
        val data = mutableListOf<TaskDetails>()
        var index = 0
        while (index < 26) {
            data.add(
                TaskDetails(
                    taskId = index,
                    taskModel = TaskModel(
                        title = title[index].toString(),
                        description = title[title.length - index - 1].toString()
                    )
                )
            )
            index++
        }
        return data
    }
}