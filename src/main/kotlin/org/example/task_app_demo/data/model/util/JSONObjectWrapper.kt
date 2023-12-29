package org.example.task_app_demo.data.model.util

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus

@JsonInclude(JsonInclude.Include.NON_NULL)
data class JSONObjectWrapper(
    var data: Any,
    var httpStatus: HttpStatus,
    var pagination: Pagination? = null
)
