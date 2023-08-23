package com.arif.calendarapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TaskDetails(
    @SerializedName("task_id")
    val taskId: Int,
    @SerializedName("TaskModel")
    val taskModel: TaskModel
)
