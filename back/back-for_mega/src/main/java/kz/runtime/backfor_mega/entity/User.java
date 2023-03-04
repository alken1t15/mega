package kz.runtime.backfor_mega.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "user_table")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "second_name")
    private String secondName;
    @Column(name = "middle_name")
    private String middle_name;
    private Long age;

    private String address;

    private String phone;

    @OneToMany(mappedBy = "user")
    private List<Card> cardList;

    @OneToMany(mappedBy = "user")
    private List<History> historyList;

    @OneToMany(mappedBy = "user")
    private List<Crypto> cryptoList;
}