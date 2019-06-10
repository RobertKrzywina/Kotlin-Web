package pl.robert.kotlinweb.security.user.domain.exception

import lombok.Getter
import lombok.AllArgsConstructor

class InvalidUserException(cause: CAUSE) : RuntimeException(cause.message) {

    @Getter
    @AllArgsConstructor
    enum class CAUSE(val message: String) {
        EMPTY_EMAIL("Email is required"),
        EMPTY_PASSWORD("Password is required"),
        EMPTY_FIRST_NAME("First name is required"),
        EMPTY_LAST_NAME("Last name is required"),
    }
}
