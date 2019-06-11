package pl.robert.kotlinweb.security.user.domain.dto

data class UserDetailsDto(
        val id: String,
        var email: String,
        var firstName: String,
        var lastName: String,
        var roles: String,
        var enabled: Boolean,
        var accountNonExpired: Boolean,
        var accountNonLocked: Boolean,
        var credentialsNonExpired: Boolean
)
