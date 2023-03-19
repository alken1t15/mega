package kz.runtime.backfor_mega.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "history_table")
@Data
@NoArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "block_wallet")
    private String nameWallet;

    @Column(name = "dates")
    private LocalDateTime dates;

    @Column(name = "name_crypt")
    private String nameCrypt;

    private Double counts;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;


    public History(String nameWallet, LocalDateTime dates, String nameCrypt, Double counts, User user) {
        this.nameWallet = nameWallet;
        this.dates = dates;
        this.nameCrypt = nameCrypt;
        this.counts = counts;
        this.user = user;
    }
}