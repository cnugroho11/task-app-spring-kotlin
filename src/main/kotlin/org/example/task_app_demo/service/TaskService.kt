package org.example.task_app_demo.service

import org.example.task_app_demo.data.Task
import org.example.task_app_demo.data.model.util.SortType
import org.example.task_app_demo.data.model.TaskCreateRequest
import org.example.task_app_demo.data.model.TaskDto
import org.example.task_app_demo.data.model.TaskUpdateRequest
import org.example.task_app_demo.exception.BadRequestException
import org.example.task_app_demo.exception.TaskNotFoundException
import org.example.task_app_demo.repository.TaskRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.util.ReflectionUtils
import java.lang.reflect.Field
import java.util.stream.Collectors
import kotlin.reflect.full.memberProperties

@Service
class TaskService(private val repository: TaskRepository) {

    private fun convertEntityToDto(task: Task): TaskDto {
        return TaskDto(
            task.id,
            task.description,
            task.isReminderSet,
            task.isTaskOpen,
            task.createdAt,
            task.priority,
        )
    }

    private fun assignValuesToEntity(task: Task, taskRequest: TaskCreateRequest) {
        task.description = taskRequest.description
        task.isReminderSet = taskRequest.isReminderSet
        task.isTaskOpen = taskRequest.isTaskOpen
        task.createdAt = taskRequest.createdAt
        task.priority = taskRequest.priority
    }

    private fun checkForTaskId(id: Long) {
        if (!repository.existsById(id)) {
            throw TaskNotFoundException("Task with id: $id does not exists!")
        }
    }

    fun getAllTasks(
        page: Int,
        size: Int,
        sort: SortType
    ): List<TaskDto> {
        var sortValue: Sort = Sort.by(Sort.Direction.ASC, "id")
        if (sort != SortType.ASC) {
            sortValue = Sort.by(Sort.Direction.DESC, "id")
        }

        return repository
            .findAll(PageRequest.of(page - 1, size, sortValue))
            .stream()
            .map(this::convertEntityToDto)
            .collect(Collectors.toList())
    }

    fun getAllOpenTasks(
        page: Int,
        size: Int,
        sort: SortType
    ): List<TaskDto> {
        var sortValue: Sort = Sort.by(Sort.Direction.ASC, "id")
        if (sort != SortType.ASC) {
            sortValue = Sort.by(Sort.Direction.DESC, "id")
        }

        return repository
            .findTasksByIsTaskOpen(isTaskOpen = true, PageRequest.of(page - 1, size, sortValue))
            .stream()
            .map(this::convertEntityToDto)
            .collect(Collectors.toList())
    }

    fun getAllClosedTasks(
        page: Int,
        size: Int,
        sort: SortType
    ): List<TaskDto> {
        var sortValue: Sort = Sort.by(Sort.Direction.ASC, "id")
        if (sort != SortType.ASC) {
            sortValue = Sort.by(Sort.Direction.DESC, "id")
        }

        val pageRequest: PageRequest = PageRequest.of(page - 1, size, sortValue)
        return repository
            .findTasksByIsTaskOpen(isTaskOpen = false, pageRequest)
            .stream()
            .map(this::convertEntityToDto)
            .collect(Collectors.toList())
    }

    fun getTaskById(id: Long): TaskDto {
        checkForTaskId(id)
        val task: Task = repository.findTaskById(id)
        return convertEntityToDto(task)
    }

    fun createTask(createRequest: TaskCreateRequest): TaskDto {
        if (repository.doesDescriptionExists(createRequest.description)) {
            throw BadRequestException("there is already task with description: ${createRequest.description}")
        }

        val task = Task()
        assignValuesToEntity(task, createRequest)
        val savedTask = repository.save(task)
        return convertEntityToDto(savedTask)
    }

    fun updateTask(id: Long, updateRequest: TaskUpdateRequest): TaskDto {
        checkForTaskId(id)
        val existingTask: Task = repository.findTaskById(id)

        for (prop in TaskUpdateRequest::class.memberProperties) {
            if (prop.get(updateRequest) != null) {
                val field: Field? = ReflectionUtils.findField(Task::class.java, prop.name)
                field?.let {
                    it.isAccessible = true
                    ReflectionUtils.setField(it, existingTask, prop.get(updateRequest))
                }
            }
        }

        val savedTask: Task = repository.save(existingTask)
        return convertEntityToDto(savedTask)
    }

    fun deleteTask(id: Long): String {
        checkForTaskId(id)
        repository.deleteById(id)
        return "Task with id: $id has been deleted."
    }
}