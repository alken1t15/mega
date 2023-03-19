package kz.runtime.backfor_mega.entityjson;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class HistoryJson {

    private Long id;

    private LocalDateTime date;

    private String nameWallet;

    private String nameCrypt;

    private Double count;

    public HistoryJson() {
    }

    public HistoryJson(Long id, LocalDateTime date, String nameWallet, String nameCrypt, Double count) {
        this.id = id;
        this.date = date;
        this.nameWallet = nameWallet;
        this.nameCrypt = nameCrypt;
        this.count = count;
    }
}