package pl.robert.kotlinweb.user.domain

import java.util.UUID

import lombok.AccessLevel
import lombok.EqualsAndHashCode
import lombok.NoArgsConstructor
import lombok.experimental.FieldDefaults

import java.util.stream.Collectors

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.authority.SimpleGrantedAuthority

@Document
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode
open class User : UserDetails {

    @Id
    val id: String = UUID.randomUUID().toString()

    var email: String = ""

    var pass: String = ""

    var firstName: String = ""

    var lastName: String = ""

    var roles: String = ""

    var enabled: Boolean = true

    var accountNonExpired: Boolean = true

    var accountNonLocked: Boolean = true

    var credentialsNonExpired: Boolean = true

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = roles
            .split(",")
            .stream()
            .map { authority -> SimpleGrantedAuthority(authority.trim()) }
            .collect(Collectors.toSet())

    override fun isEnabled(): Boolean = enabled

    override fun getUsername(): String = email

    override fun isCredentialsNonExpired(): Boolean = credentialsNonExpired

    override fun getPassword(): String = pass

    override fun isAccountNonExpired(): Boolean = accountNonExpired

    override fun isAccountNonLocked(): Boolean = accountNonLocked
}
