package com.itabrek.baseback.repository;

import com.itabrek.baseback.entity.User;
import com.itabrek.baseback.entity.UserData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDataRepository extends CrudRepository<UserData, Long> {
    Optional<UserData> findByUserUsername(String username);
    Optional<UserData> findUserDataByUser(User user);
}
