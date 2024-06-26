package com.gitlab.emradbuba.learning.learningproject.persistance;

import com.gitlab.emradbuba.learning.learningproject.model.IdCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdCardRepository extends JpaRepository<IdCard, Long> {
}
