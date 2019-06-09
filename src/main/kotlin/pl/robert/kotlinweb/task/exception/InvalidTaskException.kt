package pl.robert.kotlinweb.task.exception

import lombok.AllArgsConstructor
import lombok.Getter

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
