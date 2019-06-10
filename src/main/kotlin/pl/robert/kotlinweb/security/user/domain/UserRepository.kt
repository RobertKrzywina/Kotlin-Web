package pl.robert.kotlinweb.security.user.domain

import java.util.Optional

import org.springframework.data.repository.Repository

interface UserRepository : Repository<User, String> {

    fun save(user: User): User

    fun findById(id: String): Optional<User>

    fun findByEmail(email: String): Optional<User>

    fun findAll(): Iterable<User>

    fun deleteById(id: String)

    fun deleteAll()
}
