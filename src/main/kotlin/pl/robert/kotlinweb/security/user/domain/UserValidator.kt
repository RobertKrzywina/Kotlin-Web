package pl.robert.kotlinweb.security.user.domain

import java.util.regex.Pattern

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired

import pl.robert.kotlinweb.security.user.domain.dto.UserDto
import pl.robert.kotlinweb.security.user.domain.exception.InvalidUserException

@Component
class UserValidator @Autowired constructor(val repository: UserRepository) {

    val EMAIL_REGEX = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*" +
            "@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", Pattern.CASE_INSENSITIVE)

    fun checkInputData(dto: UserDto) {
        var cause: InvalidUserException.CAUSE? = null

        if (dto.email.isBlank()) {
            cause = InvalidUserException.CAUSE.EMPTY_EMAIL
        } else if (dto.pass.isBlank()) {
            cause = InvalidUserException.CAUSE.EMPTY_PASSWORD
        } else if (dto.firstName.isBlank()) {
            cause = InvalidUserException.CAUSE.EMPTY_FIRST_NAME
        } else if (dto.lastName.isBlank()) {
            cause = InvalidUserException.CAUSE.EMPTY_LAST_NAME
        } else if (dto.pass.length < 5 || dto.pass.length > 40) {
            cause = InvalidUserException.CAUSE.LENGTH_PASSWORD
        } else if (dto.firstName.length < 2 || dto.firstName.length > 30) {
            cause = InvalidUserException.CAUSE.LENGTH_FIRST_NAME
        } else if (dto.lastName.length < 2 || dto.lastName.length > 30) {
            cause = InvalidUserException.CAUSE.LENGTH_LAST_NAME
        } else if (!EMAIL_REGEX.matcher(dto.email).find()) {
            cause = InvalidUserException.CAUSE.EMAIL_FORMAT
        } else if (repository.findByEmail(dto.email).isPresent) {
            cause = InvalidUserException.CAUSE.EMAIL_UNIQUE
        }

        if (cause != null) {
            throw InvalidUserException(cause)
        }
    }
}
