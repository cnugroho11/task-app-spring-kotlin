package org.example.task_app_demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TaskAppDemoApplication

fun main(args: Array<String>) {
	runApplication<TaskAppDemoApplication>(*args)
}
