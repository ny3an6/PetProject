package com.nikita.simpleProject.repository.firstrepository;

import com.nikita.simpleProject.model.first.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    @Query(nativeQuery = true, value = "select * from user_info u where u.username=?1")
    Optional<UserInfo> findByUserName(String userName);
}
