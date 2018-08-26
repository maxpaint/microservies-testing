package com.intellias.greeting.jpa.model;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

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
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class GreetingMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "greeting_message_generator")
    @SequenceGenerator(name = "greeting_message_generator", sequenceName = "greeting_message_seq")
    @Column(updatable = false, nullable = false)
    private Long id;

    @NotBlank
    @Column(columnDefinition = "json", nullable = false)
    @Type(type = "json")
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
