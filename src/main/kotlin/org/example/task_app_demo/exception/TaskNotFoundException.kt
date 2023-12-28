package org.example.task_app_demo.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(HttpStatus.NOT_FOUND)
data class TaskNotFoundException(override val message: String): RuntimeException()
