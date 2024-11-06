package com.ecommerce.chomoi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "crt_id")
    String id;

    @OneToOne
    @JoinColumn(name = "acc_id")
    Account account;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "cart", orphanRemoval = true)
    @JsonIgnore
    List<CartItem> cartItems = new ArrayList<>();
}
