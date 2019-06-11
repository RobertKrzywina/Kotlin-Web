package pl.robert.kotlinweb.task.domain

import pl.robert.kotlinweb.task.domain.exception.InvalidTaskException
import pl.robert.kotlinweb.shared.Constants.Task.Companion.LENGTH_MIN_TITLE
import pl.robert.kotlinweb.shared.Constants.Task.Companion.LENGTH_MAX_TITLE
import pl.robert.kotlinweb.shared.Constants.Task.Companion.LENGTH_MIN_DETAILS

class TaskValidator {

    fun checkInputData(title: String, details: String) {
        var cause: InvalidTaskException.CAUSE? = null

        when {
            title.isBlank() -> cause = InvalidTaskException.CAUSE.EMPTY_TITLE
            title.length < LENGTH_MIN_TITLE || title.length > LENGTH_MAX_TITLE -> cause = InvalidTaskException.CAUSE.LENGTH_TITLE
            details.length < LENGTH_MIN_DETAILS -> cause = InvalidTaskException.CAUSE.LENGTH_DETAILS
        }

        if (cause != null) {
            throw InvalidTaskException(cause)
        }
    }
}
