package pl.robert.kotlinweb.security.user.domain

import spock.lang.Unroll
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
        service.save(new UserDto('abcd@mail.com', '12345', 'Joe', 'Doe'))
        service.save(new UserDto('efgh@mail.com', '67890', 'Tim', 'Nye'))
        service.save(new UserDto('ijkl@mail.com', '13579', 'Sue', 'Cox'))
    }

    @Unroll
    def 'Should throw an exception with specified message cause some of values are blank or empty = [#email, #pass, #firstName, #lastName]'(
            String email,
            String pass,
            String firstName,
            String lastName
    ) {
        given: 'initialized obj'
        dto.email = email
        dto.pass = pass
        dto.firstName = firstName
        dto.lastName = lastName

        when: 'we try to save user'
        service.save(dto)

        then: 'exception is thrown'
        RuntimeException exception = thrown()

        where:
        email           | pass    | firstName | lastName
        ''              | ''      | ''        | ''
        'mail@mail.com' | ' '     | ''        | ''
        'mail@mail.com' | '12345' | ''        | ''
        'mail@mail.com' | '12345' | 'John'    | ''
        '             ' | '12345' | 'John'    | 'Doe'
        'mail@mail.com' | '12345' | 'John'    | '   '
        'mail@mail.com' | '12345' | '    '    | '   '
        'mail@mail.com' | '     ' | '    '    | '   '
        '             ' | '     ' | '    '    | '   '
    }

    @Unroll
    def 'Should throw an exception with specified message cause invalid length of values = [#pass, #firstName, #lastName]'(
            String pass,
            String firstName,
            String lastName
    ) {
        given: 'initialized obj'
        dto.pass = pass
        dto.firstName = firstName
        dto.lastName = lastName

        when: 'we try to save user'
        service.save(dto)

        then: 'exception is thrown'
        RuntimeException exception = thrown()

        where:
        pass                                              | firstName                             | lastName
        'a'                                               | 'a'                                   | 'a'
        '12345'                                           | 'a'                                   | 'a'
        '12345'                                           | 'John'                                | 'a'
        'a'                                               | 'John'                                | 'Doe'
        '12345'                                           | 'John'                                | 'thisIstooLongLengthOfGivenLastName'
        '12345'                                           | 'thisIstooLongLengthOfGivenFirstName' | 'Doe'
        'thisIsUnfortunatelytooLongLengthOfGivenPassword' | 'John'                                | 'Doe'
    }

    @Unroll
    def 'Should throw an exception cause email format = [#email]'(String email) {
        when: 'we try to create an user'
        service.save(new UserDto(email, '12345', 'John', 'Doe'))

        then: 'exception is thrown'
        RuntimeException exception = thrown()

        where:
        email                          | _
        'plainaddress'                 | _
        '#@%^%#$@#$@#.com'             | _
        '@domain.com'                  | _
        'Joe Smith <email@domain.com>' | _
        'email.domain.com'             | _
        'email@domain@domain.com'      | _
        '.email@domain.com'            | _
        'email.@domain.com'            | _
        'email..email@domain.com'      | _
        'あいうえお@domain.com'          | _
        'email@domain.com (Joe Smith)' | _
        'email@domain'                 | _
    }

    @Unroll
    def 'Should throw an exception cause email need to be unique = [#email]'(String email) {
        when: 'we try to create an user'
        service.save(new UserDto(email, '12345', 'John', 'Doe'))

        then: 'exception is thrown'
        RuntimeException exception = thrown()

        where:
        email            | _
        'john@email.com' | _
        'john@email.com' | _
        'mike@email.com' | _
    }

    def 'Should throw an exception cause user does not exists'() {
        when: 'we ask for user by email'
        service.getByEmail('unknown@mail.com')

        then: 'exception is thrown'
        RuntimeException exception = thrown()
    }
}
