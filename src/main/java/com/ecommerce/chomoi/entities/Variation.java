package com.ecommerce.chomoi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prd_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonBackReference
    Product product;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "variation", orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference
    List<VariationOption> options = new ArrayList<>();
}
