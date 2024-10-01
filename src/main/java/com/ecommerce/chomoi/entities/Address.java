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
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "address_id")
    String id;

    @Column(name = "address_province")
    String province;

    @Column(name = "address_district")
    String district;

    @Column(name = "address_ward")
    String ward;

    @Column(name = "address_detail")
    String detail;

    @Column(name = "address_receiver_name")
    String receiver_name;

    @Column(name = "address_receiver_phone")
    String receiver_phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    Account account;



}
