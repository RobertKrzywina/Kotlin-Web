package pl.robert.kotlinweb.task.domain.exception

import lombok.Getter
import lombok.AllArgsConstructor

import pl.robert.kotlinweb.shared.GlobalExceptionHandler

import pl.robert.kotlinweb.shared.Constants.Task.Companion.MESSAGE_NOT_EXISTS
import pl.robert.kotlinweb.shared.Constants.Task.Companion.MESSAGE_EMPTY_TITLE
import pl.robert.kotlinweb.shared.Constants.Task.Companion.MESSAGE_LENGTH_TITLE
import pl.robert.kotlinweb.shared.Constants.Task.Companion.MESSAGE_LENGTH_DETAILS

class InvalidTaskException(cause: CAUSE) :
        GlobalExceptionHandler(cause.message, null, false, false) {

    @Getter
    @AllArgsConstructor
    enum class CAUSE(val message: String) {
        EMPTY_TITLE(MESSAGE_EMPTY_TITLE),
        LENGTH_TITLE(MESSAGE_LENGTH_TITLE),
        LENGTH_DETAILS(MESSAGE_LENGTH_DETAILS),
        NOT_EXISTS(MESSAGE_NOT_EXISTS)
    }
}
