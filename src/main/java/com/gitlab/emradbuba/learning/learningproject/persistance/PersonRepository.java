package com.gitlab.emradbuba.learning.learningproject.persistance;

import com.gitlab.emradbuba.learning.learningproject.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
