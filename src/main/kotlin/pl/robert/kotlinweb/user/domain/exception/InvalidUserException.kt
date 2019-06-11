package pl.robert.kotlinweb.user.domain.exception

import lombok.Getter
import lombok.AllArgsConstructor

class InvalidUserException(cause: CAUSE) : RuntimeException(cause.message) {

    @Getter
    @AllArgsConstructor
    enum class CAUSE constructor(val message: String) {
        EMPTY_EMAIL("Email is required"),
        EMPTY_PASSWORD("Password is required"),
        EMPTY_FIRST_NAME("First name is required"),
        EMPTY_LAST_NAME("Last name is required"),
        LENGTH_PASSWORD("Password should have from 5 to 40 characters"),
        LENGTH_FIRST_NAME("Title should have from 2 to 30 characters"),
        LENGTH_LAST_NAME("Title should have from 2 to 30 characters"),
        EMAIL_FORMAT("Invalid email format"),
        EMAIL_UNIQUE("Email should be unique"),
        EMAIL_NOT_EXISTS("Email do not exists")
    }
}
