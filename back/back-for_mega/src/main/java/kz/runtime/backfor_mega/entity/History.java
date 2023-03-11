package kz.runtime.backfor_mega.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "history_table")
@Data
@NoArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "block_wallet")
    private String blockWallet;

    @Column(name = "price_date_buy")
    private LocalDate priceDatBuy;

    @Column(name = "price_date_sell")
    private LocalDate priceDateSell;

    @Column(name = "price_buy")
    private Double priceBuy;

    @Column(name = "price_sell")
    private Double priceSell;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

}