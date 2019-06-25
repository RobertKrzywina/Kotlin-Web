package pl.robert.kotlinweb.shared

import lombok.AccessLevel
import lombok.NoArgsConstructor

import java.util.regex.Pattern

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class Constants {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    class Task {
        companion object {

            const val LENGTH_MIN_TITLE = 2
            const val LENGTH_MAX_TITLE = 45
            const val LENGTH_MIN_DETAILS = 5

            const val MESSAGE_EMPTY_TITLE = "Title is required"
            const val MESSAGE_LENGTH_TITLE = "Title should have from 2 to 45 characters"
            const val MESSAGE_LENGTH_DETAILS = "Details should have minimum 5 characters"
            const val MESSAGE_NOT_EXISTS = "Task does not exists"
        }
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    class User {
        companion object {

            const val LENGTH_MIN_PASSWORD = 5
            const val LENGTH_MAX_PASSWORD = 40
            const val LENGTH_MIN_NAME = 2
            const val LENGTH_MAX_NAME = 30

            val EMAIL_REGEX: Pattern = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*" +
                    "@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", Pattern.CASE_INSENSITIVE)

            const val MESSAGE_EMPTY_EMAIL = "Email is required"
            const val MESSAGE_EMPTY_PASSWORD = "Password is required"
            const val MESSAGE_EMPTY_FIRST_NAME = "First name is required"
            const val MESSAGE_EMPTY_LAST_NAME = "Last name is required"
            const val MESSAGE_LENGTH_PASSWORD = "Password should have from 5 to 40 characters"
            const val MESSAGE_LENGTH_FIRST_NAME = "First name should have from 2 to 30 characters"
            const val MESSAGE_LENGTH_LAST_NAME = "Last name should have from 2 to 30 characters"
            const val MESSAGE_EMAIL_FORMAT = "Invalid email format"
            const val MESSAGE_EMAIL_UNIQUE = "Email should be unique"
            const val MESSAGE_EMAIL_NOT_EXISTS = "Email do not exists"
        }
    }
}
