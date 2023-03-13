package kz.runtime.backfor_mega.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "wallet")
@Getter
@Setter
@Data
@NoArgsConstructor
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_wallet")
    private String nameWallet;

    @Column(name = "name_crypt")
    private String nameCrypt;

    private Double count;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
}
