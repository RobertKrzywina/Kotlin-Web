package pl.robert.kotlinweb.user.domain.exception

import lombok.Getter
import lombok.AllArgsConstructor

import pl.robert.kotlinweb.shared.GlobalExceptionHandler

import pl.robert.kotlinweb.shared.Constants.User.Companion.MESSAGE_EMPTY_EMAIL
import pl.robert.kotlinweb.shared.Constants.User.Companion.MESSAGE_EMAIL_FORMAT
import pl.robert.kotlinweb.shared.Constants.User.Companion.MESSAGE_EMAIL_UNIQUE
import pl.robert.kotlinweb.shared.Constants.User.Companion.MESSAGE_EMPTY_PASSWORD
import pl.robert.kotlinweb.shared.Constants.User.Companion.MESSAGE_LENGTH_PASSWORD
import pl.robert.kotlinweb.shared.Constants.User.Companion.MESSAGE_EMPTY_LAST_NAME
import pl.robert.kotlinweb.shared.Constants.User.Companion.MESSAGE_LENGTH_LAST_NAME
import pl.robert.kotlinweb.shared.Constants.User.Companion.MESSAGE_EMAIL_NOT_EXISTS
import pl.robert.kotlinweb.shared.Constants.User.Companion.MESSAGE_EMPTY_FIRST_NAME
import pl.robert.kotlinweb.shared.Constants.User.Companion.MESSAGE_LENGTH_FIRST_NAME

class InvalidUserException(cause: CAUSE) :
        GlobalExceptionHandler(cause.message, null, false, false) {

    @Getter
    @AllArgsConstructor
    enum class CAUSE constructor(val message: String) {
        EMPTY_EMAIL(MESSAGE_EMPTY_EMAIL),
        EMPTY_PASSWORD(MESSAGE_EMPTY_PASSWORD),
        EMPTY_FIRST_NAME(MESSAGE_EMPTY_FIRST_NAME),
        EMPTY_LAST_NAME(MESSAGE_EMPTY_LAST_NAME),
        LENGTH_PASSWORD(MESSAGE_LENGTH_PASSWORD),
        LENGTH_FIRST_NAME(MESSAGE_LENGTH_FIRST_NAME),
        LENGTH_LAST_NAME(MESSAGE_LENGTH_LAST_NAME),
        EMAIL_FORMAT(MESSAGE_EMAIL_FORMAT),
        EMAIL_UNIQUE(MESSAGE_EMAIL_UNIQUE),
        EMAIL_NOT_EXISTS(MESSAGE_EMAIL_NOT_EXISTS)
    }
}
