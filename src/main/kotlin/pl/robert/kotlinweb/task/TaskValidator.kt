package pl.robert.kotlinweb.task

import lombok.NoArgsConstructor
import pl.robert.kotlinweb.task.exception.InvalidTaskException

@NoArgsConstructor
class TaskValidator {

    fun checkInputData(title: String, details: String) {
        var cause: InvalidTaskException.CAUSE? = null

        if (title.isEmpty() || title.isBlank()) {
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
