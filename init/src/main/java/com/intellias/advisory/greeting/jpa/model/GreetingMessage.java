package com.intellias.advisory.greeting.jpa.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import static java.util.Objects.nonNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "greeting_message")
public class GreetingMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "greeting_message_generator")
    @SequenceGenerator(name = "greeting_message_generator", sequenceName = "greeting_message_seq")
    @Column(updatable = false, nullable = false)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String message;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    public GreetingMessage updateMessage(String message) {
        if (nonNull(message)) {
            this.message = message;
        }
        return this;
    }
}
