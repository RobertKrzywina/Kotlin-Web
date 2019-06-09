package pl.robert.kotlinweb.task

import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TaskService @Autowired constructor(private val repository: TaskRepository) {

    val validator = TaskValidator()

    fun save(task: Task): Task{
        validator.checkInputData(task.title, task.details)
        return repository.save(task)
    }

    fun get(): Iterable<Task> = repository.findAll()

    fun getById(id: String): Task = repository.findById(id).get()

    fun markAsDone(id: String) {
        repository.findById(id).get().status = true
    }

    fun deleteById(id: String) = repository.deleteById(id)

    fun deleteAll() = repository.deleteAll()
}
