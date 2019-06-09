package pl.robert.kotlinweb.task.exception

import lombok.Getter
import lombok.AllArgsConstructor

class InvalidTaskException(cause: CAUSE) : RuntimeException(cause.message) {

    @Getter
    @AllArgsConstructor
    enum class CAUSE(val message: String) {
        EMPTY_TITLE("Title is required"),
        LENGTH_TITLE("Title should have from 2 to 45 characters"),
        LENGTH_DETAILS("Details should have minimum 5 characters"),
        NOT_EXISTS("Task does not exists")
    }
}
