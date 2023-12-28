package org.example.task_app_demo.controller

import jakarta.validation.Valid
import org.example.task_app_demo.data.model.TaskCreateRequest
import org.example.task_app_demo.data.model.TaskDto
import org.example.task_app_demo.data.model.TaskUpdateRequest
import org.example.task_app_demo.service.TaskService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1")
class TaskController(private val service: TaskService) {

    @GetMapping("tasks")
    fun getAllTasks(): ResponseEntity<List<TaskDto>> = ResponseEntity(service.getAllTasks(), HttpStatus.OK)

    @GetMapping("task/open")
    fun getAllOpenTasks(): ResponseEntity<List<TaskDto>> = ResponseEntity(service.getAllClosedTasks(), HttpStatus.OK)

    @GetMapping("task/closed")
    fun getAllClosedTasks(): ResponseEntity<List<TaskDto>> = ResponseEntity(service.getAllClosedTasks(), HttpStatus.OK)

    @GetMapping("task/{id}")
    fun getTaskById(@PathVariable id: Long): ResponseEntity<TaskDto> =
        ResponseEntity(service.getTaskById(id), HttpStatus.OK)

    @PostMapping("task/create")
    fun createTask(@Valid @RequestBody createRequest: TaskCreateRequest): ResponseEntity<TaskDto> =
        ResponseEntity(service.createTask(createRequest), HttpStatus.CREATED)

    @PatchMapping("task/{id}")
    fun updateTask(
        @PathVariable id: Long,
        @Valid @RequestBody updateRequest: TaskUpdateRequest
    ): ResponseEntity<TaskDto> = ResponseEntity(service.updateTask(id, updateRequest), HttpStatus.OK)

    @DeleteMapping("task/{id}")
    fun deleteTask(@PathVariable id: Long): ResponseEntity<String> = ResponseEntity(service.deleteTask(id), HttpStatus.OK)
}