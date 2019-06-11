package pl.robert.kotlinweb.security.user.domain.dto

data class UserDto(
    var email: String,
    var pass: String,
    var firstName: String,
    var lastName: String
)
