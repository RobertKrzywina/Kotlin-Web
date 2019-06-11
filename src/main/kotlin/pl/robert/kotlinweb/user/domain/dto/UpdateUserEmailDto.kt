package pl.robert.kotlinweb.user.domain.dto

data class UpdateUserEmailDto(
        val oldEmail: String,
        val newEmail: String
)
