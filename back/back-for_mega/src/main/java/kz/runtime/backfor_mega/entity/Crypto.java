package kz.runtime.backfor_mega.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "crypto_table")
@Data
public class Crypto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double price;

    @Column(name = "price_sell")
    private Double priceSell;

    private String name;

    @ManyToOne
    @JoinColumn(name = "id")
    private User user;

}