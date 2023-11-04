package com.gitlab.emradbuba.learning.jpa.basicjpacrud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PERSON_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Person person; // <-- Foreign key: PK in Person
 }
