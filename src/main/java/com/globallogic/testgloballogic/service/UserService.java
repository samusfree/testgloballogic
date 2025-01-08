package com.globallogic.testgloballogic.service;


import com.globallogic.testgloballogic.dto.UserDTO;
import com.globallogic.testgloballogic.dto.UserRequestDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserRequestDTO user);

    List<UserDTO> listUsers();
}
