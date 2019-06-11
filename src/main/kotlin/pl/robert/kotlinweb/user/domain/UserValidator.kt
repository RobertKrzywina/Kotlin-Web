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
        var cause: InvalidUserException.CAUSE? = null

        if (dto.email.isBlank()) {
            cause = InvalidUserException.CAUSE.EMPTY_EMAIL
        } else if (dto.pass.isBlank()) {
            cause = InvalidUserException.CAUSE.EMPTY_PASSWORD
        } else if (dto.firstName.isBlank()) {
            cause = InvalidUserException.CAUSE.EMPTY_FIRST_NAME
        } else if (dto.lastName.isBlank()) {
            cause = InvalidUserException.CAUSE.EMPTY_LAST_NAME
        } else if (dto.pass.length < LENGTH_MIN_PASSWORD || dto.pass.length > LENGTH_MAX_PASSWORD) {
            cause = InvalidUserException.CAUSE.LENGTH_PASSWORD
        } else if (dto.firstName.length < LENGTH_MIN_NAME || dto.firstName.length > LENGTH_MAX_NAME) {
            cause = InvalidUserException.CAUSE.LENGTH_FIRST_NAME
        } else if (dto.lastName.length < LENGTH_MIN_NAME || dto.lastName.length > LENGTH_MAX_NAME) {
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
