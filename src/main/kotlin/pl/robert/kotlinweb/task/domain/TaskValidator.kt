package pl.robert.kotlinweb.task.domain

import pl.robert.kotlinweb.task.domain.exception.InvalidTaskException

class TaskValidator {

    fun checkInputData(title: String, details: String) {
        var cause: InvalidTaskException.CAUSE? = null

        if (title.isBlank()) {
            cause = InvalidTaskException.CAUSE.EMPTY_TITLE
        } else if (title.length < 2 || title.length > 45) {
            cause = InvalidTaskException.CAUSE.LENGTH_TITLE
        } else if (details.length < 5) {
            cause = InvalidTaskException.CAUSE.LENGTH_DETAILS
        }

        if (cause != null) {
            throw InvalidTaskException(cause)
        }
    }
}
