package org.example.task_app_demo.data.model

import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

data class TaskCreateRequest(
    @NotBlank
    val description: String,

    val isReminderSet: Boolean,

    val isTaskOpen: Boolean,

    @NotBlank(message = "created_at can't be empty")
    val createdAt: LocalDateTime,

    val priority: Priority
)
