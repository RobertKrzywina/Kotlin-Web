package pl.robert.kotlinweb.security.user.domain

import spock.lang.Shared
import spock.lang.Specification

import lombok.AccessLevel
import lombok.experimental.FieldDefaults

import java.util.concurrent.ConcurrentHashMap

import pl.robert.kotlinweb.security.user.domain.dto.UserDto

@FieldDefaults(level = AccessLevel.PRIVATE)
class SecurityUserSpec extends Specification {

    @Shared
    UserService service

    @Shared
    ConcurrentHashMap<String, User> db = new ConcurrentHashMap<>()

    @Shared
    UserDto dto

    def setupSpec() {
        dto = new UserDto("mail@gmail.com", "pass", "John", "Doe")
        service = new UserService(new InMemoryUserRepository(db))
    }

    def 'Should add user'() {
        when: 'we add user'
        service.save(dto)

        then: 'system has this user'
        db.size() == 1
    }

    def 'Should find user'() {
        when: 'we find user by email'
        User foundUser = service.getByEmail(dto.email)

        then: 'system has this user'
        foundUser != null
    }

    def 'Should update user email'() {
        when: 'we update user email'
        User user = service.updateEmail(dto.email, 'new-mail@gmail.com')

        then: 'user has this email'
        user.email == 'new-mail@gmail.com'
    }

    def 'Should delete user'() {
        when: 'we find user by email'
        User foundUser = service.getByEmail('new-mail@gmail.com')

        and: 'we delete this user'
        service.deleteUser(foundUser.id)

        then: 'db should be empty'
        db.isEmpty()
    }

    def 'Should add few tasks and delete all of them'() {
        when: 'we add few tasks'
        addUsers()

        and: 'we delete all of them'
        service.deleteAll()

        then: 'db should be empty'
        service.getUsers().isEmpty()
    }

    private def addUsers() {
        service.save(new UserDto('abcd@mail.com', '12345', 'Joe','Doe'))
        service.save(new UserDto('efgh@mail.com', '67890', 'Tim','Nye'))
        service.save(new UserDto('ijkl@mail.com', '13579', 'Sue','Cox'))
    }
}
