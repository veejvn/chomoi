package com.ecommerce.chomoi.entities;

import com.ecommerce.chomoi.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ord_id")
    String id;

    @Column(name = "ord_total_quantity", nullable = false)
    int totalQuantity;

    @Column(name = "ord_total_price", nullable = false)
    BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    OrderStatus status;

    @Column(name = "ord_note", columnDefinition = "TEXT")
    String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adr_id")
    @JsonBackReference
    Address address;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "byr_id")
    @JsonBackReference
    Account buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shp_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonBackReference
    Shop shop;

    @PrePersist
    protected void onCreate() {
        this.status = OrderStatus.PENDING;
    }
}
