package com.gitlab.emradbuba.learning.jpa.basicjpacrud.persistance;

import com.gitlab.emradbuba.learning.jpa.basicjpacrud.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
