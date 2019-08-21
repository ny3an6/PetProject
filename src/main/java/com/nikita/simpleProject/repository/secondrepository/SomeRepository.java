package com.nikita.simpleProject.repository.secondrepository;

import com.nikita.simpleProject.model.second.SomeInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SomeRepository extends JpaRepository<SomeInformation, Long> {
}
