package pl.robert.kotlinweb.task

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.beans.factory.annotation.Autowired

@RestController
@RequestMapping("api/task")
class TaskController @Autowired constructor(private val service: TaskService) {

    @PostMapping
    fun save(@RequestBody task: Task): ResponseEntity<Task> = ResponseEntity.ok(service.save(task))

    @GetMapping
    fun get(): ResponseEntity<Iterable<Task>> = ResponseEntity.ok(service.get())

    @GetMapping("{id}")
    fun getById(@PathVariable id: String): ResponseEntity<Task> = ResponseEntity.ok(service.getById(id))

    @PutMapping("{id}")
    fun markAsDone(@PathVariable id: String): ResponseEntity<Any> = ResponseEntity.ok(service.markAsDone(id))

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Any> = ResponseEntity.ok(service.delete(id))
}
