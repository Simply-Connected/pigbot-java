package com.simplyconnected.pigbot.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "telegram_id")
    private Long telegramId;

    @Column(name = "name")
    @Type(type = "org.hibernate.type.TextType")
    private String name;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Pig pig;
}