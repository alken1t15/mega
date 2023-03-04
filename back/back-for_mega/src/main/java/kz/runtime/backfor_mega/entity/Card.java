package kz.runtime.backfor_mega.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "card_table")
@Data
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    @Column(name = "data_name")
    private String dataName;

    private String svv;

    @ManyToOne
    @JoinColumn(name = "id")
    private User user;

}