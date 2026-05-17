package com.grupo2.EduPerformance.EduPerformance.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

// Entidad que representa a un Profesor.
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "profesores")
public class Profesor {

    // Identificador único del profesor.
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profesor_seq")
    @SequenceGenerator(name = "profesor_seq", sequenceName = "profesor_seq", allocationSize = 1)
    private Long id;

    // Relación Uno a Uno con el Usuario.
    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    // Relación Muchos a Muchos con los Cursos que dicta.
    @ManyToMany
    @JoinTable(
        name = "profesor_curso",
        joinColumns = @JoinColumn(name = "profesor_id"),
        inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
    private List<Cursos> cursos;
}
