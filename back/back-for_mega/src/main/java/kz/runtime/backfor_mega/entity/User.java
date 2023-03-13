package kz.runtime.backfor_mega.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "user_table")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "second_name")
    private String secondName;
    @Column(name = "middle_name")
    private String middle_name;
    private Integer age;

    private String address;

    private String phone;

    private String pass;

    private String email;

    private LocalDate birthday;

    @Column(name = "register_account")
    private LocalDate registerAccount;

    @OneToMany(mappedBy = "user")
    private List<Card> cardList;

    @OneToMany(mappedBy = "user")
    private List<History> historyList;

    @OneToMany(mappedBy = "user")
    private List<Wallet> walletList;

    public User(String userName, String pass, String email, String phone, LocalDate birthday) {
        this.userName = userName;
        this.phone = phone;
        this.pass = pass;
        this.email = email;
        this.birthday = birthday;
    }
}