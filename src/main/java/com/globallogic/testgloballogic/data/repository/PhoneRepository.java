package com.globallogic.testgloballogic.data.repository;

import com.globallogic.testgloballogic.data.entity.Phone;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PhoneRepository extends CrudRepository<Phone, UUID> {
    List<Phone> findByUserId(UUID userId);
}
