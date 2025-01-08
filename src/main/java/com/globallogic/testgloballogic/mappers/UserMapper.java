package com.globallogic.testgloballogic.mappers;

import com.globallogic.testgloballogic.data.entity.Phone;
import com.globallogic.testgloballogic.data.entity.User;
import com.globallogic.testgloballogic.dto.PhoneDTO;
import com.globallogic.testgloballogic.dto.UserDTO;
import com.globallogic.testgloballogic.dto.UserRequestDTO;
import com.globallogic.testgloballogic.security.CustomUserDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User fromRequest(UserRequestDTO request, String token);

    Phone fromPhoneDTO(UUID userId, PhoneDTO phoneDTO);

    PhoneDTO fromPhone(Phone phone);

    @Mapping(target = "created", source = "user.createdAt")
    @Mapping(target = "modified", source = "user.updatedAt")
    @Mapping(target = "lastLogin", source = "user.lastLogin")
    UserDTO fromUser(User user, List<PhoneDTO> phones);

    CustomUserDetails customUserDetailsFromUser(User user);
}
