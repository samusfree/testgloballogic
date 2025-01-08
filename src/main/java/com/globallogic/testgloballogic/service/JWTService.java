package com.globallogic.testgloballogic.service;

import com.globallogic.testgloballogic.dto.UserRequestDTO;

public interface JWTService {
    String getJWTToken(UserRequestDTO user);

    boolean validateAccessToken(String token);

    String getSubject(String token);
}
