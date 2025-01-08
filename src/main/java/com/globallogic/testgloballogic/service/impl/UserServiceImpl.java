package com.globallogic.testgloballogic.service.impl;


import com.globallogic.testgloballogic.data.entity.Phone;
import com.globallogic.testgloballogic.data.entity.User;
import com.globallogic.testgloballogic.data.repository.PhoneRepository;
import com.globallogic.testgloballogic.data.repository.UserRepository;
import com.globallogic.testgloballogic.dto.UserDTO;
import com.globallogic.testgloballogic.dto.UserRequestDTO;
import com.globallogic.testgloballogic.exception.EmailExistsException;
import com.globallogic.testgloballogic.mappers.UserMapper;
import com.globallogic.testgloballogic.service.JWTService;
import com.globallogic.testgloballogic.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.stream.Streams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PhoneRepository phoneRepository;
    private final UserMapper userMapper;
    private final JWTService jwtService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PhoneRepository phoneRepository,
                           UserMapper userMapper, JWTService jwtService) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.phoneRepository = phoneRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO createUser(UserRequestDTO user) {
        User userEntity = createUserIfNotExists(user);
        List<Phone> phones = user.phones().stream()
                .map(phoneDTO -> userMapper.fromPhoneDTO(userEntity.getId(), phoneDTO))
                .collect(Collectors.toList());
        phoneRepository.saveAll(phones);
        return userMapper.fromUser(userEntity,
                phones.stream().map(userMapper::fromPhone).collect(Collectors.toList()));
    }

    @Override
    public List<UserDTO> listUsers() {
        return Streams.of(userRepository.findAll())
                .map(user ->
                        userMapper.fromUser(user,
                                phoneRepository.findByUserId(user.getId()).stream().map(userMapper::fromPhone).toList()
                        )
                ).toList();
    }

    private User createUserIfNotExists(UserRequestDTO user) {
        Optional<User> userEntity = userRepository.findByEmail(user.email());

        if (userEntity.isPresent()) {
            throw new EmailExistsException(user.email());
        }

        return userRepository.save(userMapper.fromRequest(user, jwtService.getJWTToken(user)));
    }
}
