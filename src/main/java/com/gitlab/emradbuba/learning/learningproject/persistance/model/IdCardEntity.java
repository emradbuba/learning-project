package com.gitlab.emradbuba.learning.learningproject.persistance.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "ID_CARD")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class IdCardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Column(name = "BUSINESS_ID")
    private String businessId;

    @Column(nullable = false)
    @NotNull
    private String serialNumber;

    @Column(nullable = false)
    @NotNull
    private LocalDate validUntil;

    @Column(name = "PUBLISHER")
    @NotNull
    private String publishedBy;

    @OneToOne(mappedBy = "idCard")
    @JsonBackReference
    private PersonEntity person;
}
