package pl.robert.kotlinweb.user.domain

import lombok.AccessLevel
import lombok.experimental.FieldDefaults

import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

import pl.robert.kotlinweb.user.domain.dto.UserDto
import pl.robert.kotlinweb.user.domain.dto.UserDetailsDto
import pl.robert.kotlinweb.user.domain.exception.InvalidUserException

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserService @Autowired constructor(val repository: UserRepository) : UserDetailsService {

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
        user.pass = BCryptPasswordEncoder().encode(dto.pass)
        user.roles = "USER"
        return repository.save(user)
    }

    @Transactional
    fun updateEmail(oldEmail: String, newEmail: String): User = repository
            .findByEmail(oldEmail)
            .map { user ->
                user.email = newEmail
                user
            }
            .orElseThrow { InvalidUserException(InvalidUserException.CAUSE.EMAIL_NOT_EXISTS) }

    fun getAll() = repository.findAll()
            .map {
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

    fun deleteById(id: String) = repository.deleteById(id)

    fun deleteAll() = repository.deleteAll()
}
