package pl.robert.kotlinweb.security.user.domain.dto

data class UpdateUserEmailDto(
        val oldEmail: String,
        val newEmail: String
)