package pl.robert.kotlinweb.shared

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import pl.robert.kotlinweb.task.exception.InvalidTaskException

@ControllerAdvice
class GlobalExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [(InvalidTaskException::class)])
    fun handleInvalidLengthException(exception: InvalidTaskException): ResponseEntity<Any> {
        return ResponseEntity
                .badRequest()
                .body(exception.message)
    }
}
