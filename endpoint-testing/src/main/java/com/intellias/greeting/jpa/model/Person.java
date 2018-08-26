package com.intellias.greeting.jpa.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(exclude = {"id", "firstName", "lastName"})
@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_generator")
    @SequenceGenerator(name = "person_generator", sequenceName = "person_seq")
    @Column(updatable = false, nullable = false)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String email;

    @NotBlank
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, optional = false)
    private GreetingMessage greetingMessage;

    public Optional<GreetingMessage> getGreetingMessage() {
        return ofNullable(greetingMessage);
    }
}
