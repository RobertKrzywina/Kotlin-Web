package pl.robert.kotlinweb.security.user.domain

import lombok.AccessLevel
import lombok.experimental.FieldDefaults

import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

import pl.robert.kotlinweb.security.user.domain.dto.UserDto
import pl.robert.kotlinweb.security.user.domain.dto.UserDetailsDto
import pl.robert.kotlinweb.security.user.domain.exception.InvalidUserException

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserService @Autowired constructor(val repository: UserRepository) : UserDetailsService {

    val encoder = BCryptPasswordEncoder()

    @Autowired
    val validator = UserValidator(repository)

    override fun loadUserByUsername(email: String): User {
        return repository
                .findByEmail(email)
                .orElseThrow { InvalidUserException(InvalidUserException.CAUSE.EMAIL_NOT_EXISTS) }
    }

    fun save(dto: UserDto): User {
        validator.checkInputData(dto)
        val user = User()
        user.email = dto.email
        user.firstName = dto.firstName
        user.lastName = dto.lastName
        user.pass = encoder.encode(dto.pass)
        user.roles = "USER"
        return repository.save(user)
    }

    @Transactional
    fun updateEmail(oldEmail: String, newEmail: String): User {
        val user = repository
                .findByEmail(oldEmail)
                .orElseThrow { InvalidUserException(InvalidUserException.CAUSE.EMAIL_NOT_EXISTS) }

        user.email = newEmail

        return user
    }

    fun getUsers() = repository.findAll().map {
        UserDetailsDto(
                it.id,
                it.email,
                it.firstName,
                it.lastName,
                it.roles,
                it.enabled,
                it.accountNonExpired,
                it.accountNonLocked,
                it.credentialsNonExpired
        )
    }

    fun getByEmail(email: String): User = repository
            .findByEmail(email)
            .orElseThrow { InvalidUserException(InvalidUserException.CAUSE.EMAIL_NOT_EXISTS) }

    fun deleteUser(id: String) = repository.deleteById(id)

    fun deleteAll() = repository.deleteAll()
}
