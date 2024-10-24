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
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "att_id")
    String id;

    @Column(name = "att_name", nullable = false)
    String name;

    @Column(name = "att_is_enter_by_hand")
    Boolean isEnterByHand = false;

    @Column(name = "att_required")
    Boolean required = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ctg_id")
    @JsonBackReference
    Category category;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attribute", orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    List<AttributeOption> options = new ArrayList<>();
}
