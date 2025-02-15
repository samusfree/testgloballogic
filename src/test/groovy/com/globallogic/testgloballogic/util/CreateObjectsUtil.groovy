package com.globallogic.testgloballogic.util

import com.globallogic.testgloballogic.data.entity.Phone
import com.globallogic.testgloballogic.data.entity.User
import com.globallogic.testgloballogic.dto.PhoneDTO
import com.globallogic.testgloballogic.dto.UserDTO
import com.globallogic.testgloballogic.dto.UserRequestDTO

import java.time.LocalDateTime

class CreateObjectsUtil {
    public static String SECRET_KEY = "abcdefghijklmnOPQRSTUVWXYZabcdefghijklmnOPQRSTUVWXYZabcdefghijklmnOPQRSTUVWXYZ"

    public static String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImV4cCI6MTYyNjIwNjQwMH0."

    static UserRequestDTO getUserRequestDTO() {
        return new UserRequestDTO("test", "test@gmail.com", "Samus123", [getPhoneDTO()])
    }

    static UserDTO getUserDTO() {
        return new UserDTO(UUID.randomUUID(), "test", "test@gmail.com", "Samus123", LocalDateTime.now(),
                LocalDateTime.now(), LocalDateTime.now(), TOKEN, true, [getPhoneDTO()])
    }

    static User getUser() {
        return new User(UUID.randomUUID(), "test", "test@email.com", "Samus123",
                LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), TOKEN, true)
    }

    static PhoneDTO getPhoneDTO() {
        return new PhoneDTO("55555", "55", "51")
    }

    static Phone getPhone() {
        return new Phone(id: 1, number: "55555", cityCode: "55", countryCode: "51")
    }

    private CreateObjectsUtil() {
    }
}
