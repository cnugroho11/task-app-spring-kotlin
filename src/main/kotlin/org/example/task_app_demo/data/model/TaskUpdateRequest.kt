package org.example.task_app_demo.data.model

import org.example.task_app_demo.data.model.util.Priority

data class TaskUpdateRequest(
    val description: String?,
    val isReminderSet: Boolean?,
    val isTaskOpen: Boolean?,
    val priority: Priority?,
)
