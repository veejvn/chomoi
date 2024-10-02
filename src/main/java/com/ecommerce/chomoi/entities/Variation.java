package com.ecommerce.chomoi.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Variation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "var_id")
    String id;

    @Column(name = "var_name", nullable = false)
    String name;

    @Column(name = "var_order")
    Integer order;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "variation", orphanRemoval = true)
    Set<OptionVariation> optionVariations = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prd_id")
    Product product;
}
