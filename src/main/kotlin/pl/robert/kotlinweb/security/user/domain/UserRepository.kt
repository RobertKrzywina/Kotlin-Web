package pl.robert.kotlinweb.security.user.domain

import org.springframework.data.repository.Repository

interface UserRepository : Repository<User, String> {

    fun save(user: User): User

    fun findOneByEmail(email: String): User

    fun findAll(): Iterable<User>

    fun deleteById(id: String)
}
