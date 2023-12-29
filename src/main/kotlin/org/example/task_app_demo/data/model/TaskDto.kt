package org.example.task_app_demo.data.model

import org.example.task_app_demo.data.model.util.Priority
import java.time.LocalDateTime

data class TaskDto(
    val id: Long,
    val description: String,
    val isReminderSet: Boolean,
    val isTaskOpen: Boolean,
    val createdAt: LocalDateTime,
    val priority: Priority
)
