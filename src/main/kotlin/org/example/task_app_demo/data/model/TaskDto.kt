package org.example.task_app_demo.data.model

import java.time.LocalDateTime

data class TaskDto(
    val id: Long,
    val description: String,
    val isReminderOpen: Boolean,
    val isTaskOpen: Boolean,
    val createdAt: LocalDateTime,
    val priority: Priority
)
