package org.example.task_app_demo.controller

import jakarta.validation.Valid
import org.example.task_app_demo.data.model.*
import org.example.task_app_demo.data.model.util.JSONObjectWrapper
import org.example.task_app_demo.data.model.util.Pagination
import org.example.task_app_demo.data.model.util.SortType
import org.example.task_app_demo.service.TaskService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1")
class TaskController(private val service: TaskService) {

    @GetMapping("tasks")
    fun getAllTasks(
        @RequestParam(required = false, name = "isTaskOpen") isTaskOpen: Boolean?,
        @RequestParam(required = false, name = "isReminderSet") isReminderSet: Boolean?,
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int,
        @RequestParam(required = false, defaultValue = "ASC") sort: SortType
    ): JSONObjectWrapper =
        JSONObjectWrapper(
            service.getAllTasks(isTaskOpen, isReminderSet, page, size, sort),
            HttpStatus.OK,
            Pagination(page, size, sort)
        )

    @GetMapping("task/{id}")
    fun getTaskById(@PathVariable id: Long): JSONObjectWrapper =
        JSONObjectWrapper(service.getTaskById(id), HttpStatus.OK)

    @PostMapping("task/create")
    fun createTask(@Valid @RequestBody createRequest: TaskCreateRequest): JSONObjectWrapper =
        JSONObjectWrapper(service.createTask(createRequest), HttpStatus.CREATED)

    @PatchMapping("task/{id}")
    fun updateTask(
        @PathVariable id: Long,
        @Valid @RequestBody updateRequest: TaskUpdateRequest
    ): JSONObjectWrapper = JSONObjectWrapper(service.updateTask(id, updateRequest), HttpStatus.OK)

    @DeleteMapping("task/{id}")
    fun deleteTask(@PathVariable id: Long): JSONObjectWrapper =
        JSONObjectWrapper(service.deleteTask(id), HttpStatus.OK)
}