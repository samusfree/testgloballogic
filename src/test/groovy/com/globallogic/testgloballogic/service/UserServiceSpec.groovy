package com.globallogic.testgloballogic.service

import com.globallogic.testgloballogic.data.entity.User
import com.globallogic.testgloballogic.data.repository.PhoneRepository
import com.globallogic.testgloballogic.data.repository.UserRepository
import com.globallogic.testgloballogic.dto.UserDTO
import com.globallogic.testgloballogic.dto.UserRequestDTO
import com.globallogic.testgloballogic.exception.EmailExistsException
import com.globallogic.testgloballogic.mappers.UserMapper
import com.globallogic.testgloballogic.service.impl.UserServiceImpl
import com.globallogic.testgloballogic.util.CreateObjectsUtil
import org.mapstruct.factory.Mappers
import spock.lang.Specification
import spock.lang.Subject

class UserServiceImplSpec extends Specification {
    @Subject
    UserServiceImpl userService
    UserRepository userRepository = Mock()
    PhoneRepository phoneRepository = Mock()
    JWTService jwtService = Mock()

    def setup() {
        userService = new UserServiceImpl(userRepository, phoneRepository, Mappers.getMapper(UserMapper), jwtService)
    }

    def "createUser should create a new user"() {
        given:
        UserRequestDTO userRequestDTO = CreateObjectsUtil.getUserRequestDTO()

        userRepository.findByEmail(userRequestDTO.email()) >> Optional.empty()
        userRepository.save(_ as User) >> { User user -> user }

        when:
        UserDTO result = userService.createUser(userRequestDTO)

        then:
        result != null
        result.email() == userRequestDTO.email()
        result.phones().size() == 1
    }

    def "createUser should throw EmailExistsException if email already exists"() {
        given:
        UserRequestDTO userRequestDTO = CreateObjectsUtil.getUserRequestDTO()

        userRepository.findByEmail(userRequestDTO.email()) >> Optional.of(CreateObjectsUtil.getUser())

        when:
        userService.createUser(userRequestDTO)

        then:
        thrown(EmailExistsException)
    }

    def "listUsers should return a list of users"() {
        given:
        userRepository.findAll() >> [CreateObjectsUtil.getUser()]
        phoneRepository.findByUserId(_ as UUID) >> [CreateObjectsUtil.getPhone()]
        when:
        List<UserDTO> result = userService.listUsers()

        then:
        result != null
        result.size() == 1
        result[0].email() == CreateObjectsUtil.getUser().email
        result[0].phones().size() == 1
    }
}