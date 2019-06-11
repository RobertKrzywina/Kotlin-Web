package pl.robert.kotlinweb.task.domain

import spock.lang.Unroll
import spock.lang.Shared
import spock.lang.Specification

import lombok.AccessLevel
import lombok.experimental.FieldDefaults

import pl.robert.kotlinweb.task.Task
import pl.robert.kotlinweb.task.TaskService
import pl.robert.kotlinweb.task.InMemoryTaskRepository

import java.util.concurrent.ConcurrentHashMap

import pl.robert.kotlinweb.task.exception.InvalidTaskException

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
        service.deleteById(foundTask.id)

        then: 'db should be empty'
        db.isEmpty()
    }

    def 'Should add few tasks and delete all of them'() {
        when: 'we add few tasks'
        addTasks()

        and: 'we delete all of them'
        service.deleteAll()

        then: 'db should be empty'
        service.get().isEmpty()
    }

    private def addTasks() {
        service.save(new Task(UUID.randomUUID().toString(), '1st', 'Code in Kotlin', false))
        service.save(new Task(UUID.randomUUID().toString(), '2nd', 'Read java book', false))
        service.save(new Task(UUID.randomUUID().toString(), '3rd', 'Make dinner', false))
    }

    @Unroll
    def 'Should throw an exception with specified message cause title is empty or blank = #title'(String title) {
        given: 'initialized obj'
        task.title = title

        when: 'we try to create task'
        service.save(task)

        then: 'exception is thrown'
        InvalidTaskException exception = thrown()
        exception.message == InvalidTaskException.CAUSE.EMPTY_TITLE.message

        where:
        title | _
        ''    | _
        ' '   | _
    }

    @Unroll
    def 'Should throw an exception with specified message cause invalid length of values = #title, #details'(String title, String details) {
        given: 'initalized obj'
        task.title = title
        task.details = details

        when: 'we try to create task'
        service.save(task)

        then: 'exception is thrown'
        InvalidTaskException exception = thrown()
        exception.message == InvalidTaskException.CAUSE.LENGTH_TITLE.message ||
                InvalidTaskException.CAUSE.LENGTH_DETAILS.message

        where:
        title                                              | details
        'a'                                                | 'a'
        'a'                                                | 'ab'
        'ab'                                               | 'abcd'
        'thisIsUnfortunatelyTooLongTitleLengthOfGivenTask' | 'abcde'
    }

    def 'Should throw an exception cause task does not exists'() {
        when: 'we ask for task by id'
        service.getById('1234')

        then: 'exception is thrown'
        InvalidTaskException exception = thrown()
        exception.message == InvalidTaskException.CAUSE.NOT_EXISTS.message
    }
}
