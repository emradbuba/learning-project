package com.gitlab.emradbuba.learning.jpa.basicjpacrud.model;

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
public class IdCard {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String serialNumber;

    @Column(nullable = false)
    @NotNull
    private LocalDate validUntil;

    @Column(name = "PUBLISHER")
    @NotNull
    private String publishedBy;
 }
