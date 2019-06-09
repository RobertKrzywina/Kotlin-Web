package pl.robert.kotlinweb.app.task

import spock.lang.Shared
import spock.lang.Specification

import lombok.AccessLevel
import lombok.experimental.FieldDefaults

import pl.robert.kotlinweb.task.Task
import pl.robert.kotlinweb.task.TaskService
import pl.robert.kotlinweb.task.InMemoryTaskRepository

import java.util.concurrent.ConcurrentHashMap

@FieldDefaults(level = AccessLevel.PRIVATE)
class TaskSpec extends Specification {

    @Shared
    TaskService service

    @Shared
    ConcurrentHashMap<String, Task> db = new ConcurrentHashMap<>()

    @Shared
    Task task

    def setupSpec() {
        task = new Task(UUID.randomUUID().toString(), '1st task', 'Do it ASAP', false)
        service = new TaskService(new InMemoryTaskRepository(db))
    }

    def 'Should add todo task'() {
        when: 'we add task task'
        service.save(task)

        then: 'system has this task'
        db.size() == 1
    }

    def 'Should find task'() {
        when: 'we find task by UUID'
        Task foundTask = service.getById(task.id)

        then: 'system has this task'
        foundTask != null
    }

    def 'Should update task status'() {
        when: 'task is undone'
        !task.status

        and: 'we mark task as done'
        service.markAsDone(task.id)

        then: 'system has this task updated'
        service.getById(task.id).status
    }

    def 'Should delete task'() {
        when: 'we find task by UUID'
        Task foundTask = service.getById(task.id)

        and: 'delete this task'
        service.delete(foundTask.id)

        then: 'system has not this task anymore'
        db.isEmpty()
    }

    def 'Should add few tasks and delete all of them'() {
        when: 'we add few tasks'
        addTasks()

        and: 'we delete all of them'

        then: 'db should be empty'
        service.get().isEmpty()
    }

    private def addTasks() {
        service.save(new Task(UUID.randomUUID().toString(), '1st', 'Code in Kotlin', false))
        service.save(new Task(UUID.randomUUID().toString(), '2nd', 'Read java book', false))
        service.save(new Task(UUID.randomUUID().toString(), '3rd', 'Make dinner', false))
    }
}
