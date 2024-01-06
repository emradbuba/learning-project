package com.gitlab.emradbuba.learning.jpa.basicjpacrud.persistance;

import com.gitlab.emradbuba.learning.jpa.basicjpacrud.model.IdCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdCardRepository extends JpaRepository<IdCard, Long> {
}
