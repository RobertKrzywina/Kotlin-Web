package pl.robert.kotlinweb.shared

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ControllerAdvice

import pl.robert.kotlinweb.task.domain.exception.InvalidTaskException
import pl.robert.kotlinweb.user.domain.exception.InvalidUserException

@ControllerAdvice
abstract class GlobalExceptionHandler(
        message: String?, cause: Throwable?, enableSuppression: Boolean, writableStackTrace: Boolean) :
        RuntimeException(message, cause, enableSuppression, writableStackTrace) {

    init {
        label = message
    }

    companion object {
        var label: String? = ""
    }

    @ExceptionHandler(value = [(InvalidTaskException::class), (InvalidUserException::class)])
    fun handleException(exception: InvalidTaskException): ResponseEntity<Any> {
        return ResponseEntity
                .badRequest()
                .body(exception.message)
    }
}
