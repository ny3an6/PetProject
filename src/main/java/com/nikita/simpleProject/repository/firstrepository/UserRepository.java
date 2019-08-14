package com.nikita.simpleProject.repository.firstrepository;

import com.nikita.simpleProject.model.first.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
   User findUserByLogin(@NotNull String login);
}
