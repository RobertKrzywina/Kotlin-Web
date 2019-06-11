package pl.robert.kotlinweb.task.domain

import lombok.AccessLevel
import lombok.experimental.FieldDefaults

import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

import pl.robert.kotlinweb.task.domain.exception.InvalidTaskException

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
class TaskService @Autowired constructor(val repository: TaskRepository) {

    val validator = TaskValidator()

    fun save(task: Task): Task {
        validator.checkInputData(task.title, task.details)
        return repository.save(task)
    }

    fun get(): Iterable<Task> = repository.findAll()

    fun getById(id: String): Task = repository
            .findById(id)
            .orElseThrow { InvalidTaskException(InvalidTaskException.CAUSE.NOT_EXISTS) }

    fun markAsDone(id: String) {
        repository.findById(id)
                .map { task -> task.status = true }
                .orElseThrow { InvalidTaskException(InvalidTaskException.CAUSE.NOT_EXISTS) }
    }

    fun deleteById(id: String) = repository.deleteById(id)

    fun deleteAll() = repository.deleteAll()
}
