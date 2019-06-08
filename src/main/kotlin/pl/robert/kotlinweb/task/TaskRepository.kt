package pl.robert.kotlinweb.task

import java.util.Optional

import org.springframework.data.repository.Repository

interface TaskRepository: Repository<Task, String> {

    fun save(task: Task): Task

    fun findAll(): Iterable<Task>

    fun findById(id: String): Optional<Task>

    fun deleteById(id: String)
}
