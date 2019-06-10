package pl.robert.kotlinweb.security.user.domain

import lombok.AccessLevel
import lombok.experimental.FieldDefaults

import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

import pl.robert.kotlinweb.security.user.domain.dto.UserDto
import pl.robert.kotlinweb.security.user.domain.dto.UserDetailsDto

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserService @Autowired constructor(val repository: UserRepository) : UserDetailsService {

    val encoder = BCryptPasswordEncoder()

    override fun loadUserByUsername(email: String): User {
        return repository.findByEmail(email)
    }

    fun save(dto: UserDto): User {
        val user = User()
        user.email = dto.email
        user.firstName = dto.firstName
        user.lastName = dto.lastName
        user.pass = encoder.encode(dto.pass)
        user.roles = "USER"
        return repository.save(user)
    }

    fun updateUser(toSave: User): User {
        val user = repository.findByEmail(toSave.email)
        if (toSave.pass.isNotEmpty()) {
            user.pass = encoder.encode(toSave.password)
        }
        user.firstName = toSave.firstName
        user.lastName = toSave.lastName
        user.accountNonExpired = toSave.accountNonExpired
        user.accountNonLocked = toSave.accountNonLocked
        user.credentialsNonExpired = toSave.credentialsNonExpired
        return repository.save(user)
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

    fun deleteUser(id: String) = repository.deleteById(id)

    fun deleteAll() = repository.deleteAll()
}
