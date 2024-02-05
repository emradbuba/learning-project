package com.gitlab.emradbuba.learning.learningproject.persistance;

import com.gitlab.emradbuba.learning.learningproject.persistance.model.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
    Optional<PersonEntity> findByBusinessId(String businessId);
}
