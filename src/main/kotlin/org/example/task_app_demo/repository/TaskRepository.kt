package org.example.task_app_demo.repository

import org.example.task_app_demo.data.Task
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository: JpaRepository<Task, Long> {
    fun findTaskById(id: Long): Task

    fun findTasksByIsTaskOpen(isTaskOpen: Boolean, pageable: Pageable): List<Task>

    @Query(value = "SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END FROM Task t WHERE t.description = ?1")
    fun doesDescriptionExists(description: String): Boolean

}