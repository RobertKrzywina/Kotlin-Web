package pl.robert.kotlinweb.user.domain

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired

import pl.robert.kotlinweb.user.domain.dto.UserDto
import pl.robert.kotlinweb.user.domain.exception.InvalidUserException
import pl.robert.kotlinweb.shared.Constants.User.Companion.EMAIL_REGEX
import pl.robert.kotlinweb.shared.Constants.User.Companion.LENGTH_MIN_NAME
import pl.robert.kotlinweb.shared.Constants.User.Companion.LENGTH_MAX_NAME
import pl.robert.kotlinweb.shared.Constants.User.Companion.LENGTH_MIN_PASSWORD
import pl.robert.kotlinweb.shared.Constants.User.Companion.LENGTH_MAX_PASSWORD

@Component
class UserValidator @Autowired constructor(val repository: UserRepository) {

    fun checkInputData(dto: UserDto) {
        checkRequiredData(dto)
        checkLength(dto)
        checkEmail(dto.email)
    }

    private fun checkRequiredData(dto: UserDto) {
        var cause: InvalidUserException.CAUSE? = null

        when {
            dto.email.isBlank() -> cause = InvalidUserException.CAUSE.EMPTY_EMAIL
            dto.pass.isBlank() -> cause = InvalidUserException.CAUSE.EMPTY_PASSWORD
            dto.firstName.isBlank() -> cause = InvalidUserException.CAUSE.EMPTY_FIRST_NAME
            dto.lastName.isBlank() -> cause = InvalidUserException.CAUSE.EMPTY_LAST_NAME
        }

        if (cause != null) {
            throw InvalidUserException(cause)
        }
    }

    private fun checkLength(dto: UserDto) {
        var cause: InvalidUserException.CAUSE? = null

        when {
            dto.pass.length < LENGTH_MIN_PASSWORD || dto.pass.length > LENGTH_MAX_PASSWORD -> cause = InvalidUserException.CAUSE.LENGTH_PASSWORD
            dto.firstName.length < LENGTH_MIN_NAME || dto.firstName.length > LENGTH_MAX_NAME -> cause = InvalidUserException.CAUSE.LENGTH_FIRST_NAME
            dto.lastName.length < LENGTH_MIN_NAME || dto.lastName.length > LENGTH_MAX_NAME -> cause = InvalidUserException.CAUSE.LENGTH_LAST_NAME
        }

        if (cause != null) {
            throw InvalidUserException(cause)
        }
    }

    private fun checkEmail(email: String) {
        var cause: InvalidUserException.CAUSE? = null

        when {
            !EMAIL_REGEX.matcher(email).find() -> cause = InvalidUserException.CAUSE.EMAIL_FORMAT
            repository.findByEmail(email).isPresent -> cause = InvalidUserException.CAUSE.EMAIL_UNIQUE
        }

        if (cause != null) {
            throw InvalidUserException(cause)
        }
    }
}
