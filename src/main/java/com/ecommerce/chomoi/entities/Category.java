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
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ctg_id")
    String id;

    @Column(name = "ctg_name")
    String name;

    @Column(name = "ctg_parent_id")
    String parent_id;

    @Column(name = "ctg_attribute")
    String attribute;

    @Column(name = "ctg_left")
    Integer left;

    @Column(name = "ctg_right")
    Integer right;

    @Column(name = "ctg_level")
    Integer level;
}
