package pl.robert.kotlinweb.user.domain

import lombok.AccessLevel
import lombok.experimental.FieldDefaults

import java.util.Optional
import java.util.concurrent.ConcurrentHashMap

import kotlin.collections.HashSet

@FieldDefaults(level = AccessLevel.PRIVATE)
class InMemoryUserRepository(var map: ConcurrentHashMap<String, User>) : UserRepository {

    override fun save(user: User): User {
        map[user.id] = user
        return user
    }

    override fun findById(id: String): Optional<User> {
        return map.entries
                .stream()
                .filter { map -> map.key == id }
                .map { it.value }
                .findFirst()
    }

    override fun findByEmail(email: String): Optional<User> {
        return map.entries
                .stream()
                .filter { map -> map.value.email == email }
                .map { it.value }
                .findFirst()
    }

    override fun findAll(): Iterable<User> = HashSet(map.values)

    override fun deleteById(id: String) {
        map.remove(id)
    }

    override fun deleteAll() {
        map.clear()
    }
}
