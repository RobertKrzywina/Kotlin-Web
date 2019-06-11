package pl.robert.kotlinweb.task.domain

import java.util.Optional

import org.springframework.data.repository.Repository

interface TaskRepository: Repository<Task, String> {

    fun save(task: Task): Task

    fun findAll(): Iterable<Task>

    fun findById(id: String): Optional<Task>

    fun deleteById(id: String)

    fun deleteAll()
}
