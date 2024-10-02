package com.ecommerce.chomoi.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "opt_id")
    String id;

    @Column(name = "opt_value", nullable = false)
    String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "att_id")
    Attribute attribute;
}
