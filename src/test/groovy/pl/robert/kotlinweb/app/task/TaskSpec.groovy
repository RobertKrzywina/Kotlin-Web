package pl.robert.kotlinweb.app.task

import spock.lang.Specification

class TaskSpec extends Specification {

    def 'Should add todo task'() {
        when: 'we add task task'

        then: 'system has this task'
    }

    def 'Should find task'() {
        when: 'we find task by UUID'

        then: 'system has this task'
    }

    def 'Should update task status'() {
        when: 'task is undone'

        and: 'we mark task as done'

        then: 'system has this task updated'
    }

    def 'Should delete task'() {
        when: 'we find task by UUID'

        and: 'delete this task'

        then: 'system has not this task anymore'
    }
}
