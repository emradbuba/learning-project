package com.gitlab.emradbuba.learning.learningproject.persistance;

import com.gitlab.emradbuba.learning.learningproject.model.EmploymentCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertRepo extends JpaRepository<EmploymentCertificate, Long> {
}