package kz.runtime.backfor_mega.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "crypto_table")
@Data
@NoArgsConstructor
public class Crypto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double price;

    @Column(name = "price_sell")
    private Double priceSell;

    private Double change;

    private LocalDateTime date;

    private String name;

}