package com.ecommerce.chomoi.entities;

import com.ecommerce.chomoi.enums.Role;
import com.ecommerce.chomoi.enums.ShopStatus;
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
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "shop_id")
    String id;

    @Column(name = "shop_name")
    String name;

    @Column(name = "shop_avatar")
    String avatar;

    @Column(name = "shop_cover_image")
    String cover_image;

    @Column(name = "shop_rating")
    Double rating;

    //relationship

    //Use Enum Status to make relationship between Shop and Status
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShopStatus status;

    @PrePersist
    protected void onCreate() {
        this.status = ShopStatus.ACTIVE;
    }

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;


}
