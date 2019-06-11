package pl.robert.kotlinweb.task.domain

import java.util.UUID

import lombok.AccessLevel
import lombok.experimental.FieldDefaults

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "task")
@FieldDefaults(level = AccessLevel.PRIVATE)
data class Task(

        @Id
        val id: String = UUID.randomUUID().toString(),

        var title: String,

        var details: String,

        var status: Boolean
)
