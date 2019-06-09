package pl.robert.kotlinweb.task

import java.util.UUID

import lombok.AccessLevel
import lombok.NoArgsConstructor
import lombok.experimental.FieldDefaults

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "task")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
data class Task (

        @Id
        var id: String = UUID.randomUUID().toString(),

        var title: String,

        var details: String,

        var status: Boolean
)
