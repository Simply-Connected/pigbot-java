package com.simplyconnected.pigbot.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.OffsetDateTime;

// hru hru

@Entity
@Table(name = "pigs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pig {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "weight")
    private Long weight;

    @Column(name = "name")
    @Type(type = "org.hibernate.type.TextType")
    private String name;

    @Column(name = "last_grow")
    private OffsetDateTime lastGrow;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
