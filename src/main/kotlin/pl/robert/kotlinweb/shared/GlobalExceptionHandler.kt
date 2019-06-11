package pl.robert.kotlinweb.shared

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

import pl.robert.kotlinweb.task.domain.exception.InvalidTaskException
import pl.robert.kotlinweb.user.domain.exception.InvalidUserException

@ControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [(InvalidTaskException::class), (InvalidUserException::class)])
    fun handleException(exception: InvalidTaskException): ResponseEntity<Any> {
        return ResponseEntity
                .badRequest()
                .body(exception.message)
    }
}
