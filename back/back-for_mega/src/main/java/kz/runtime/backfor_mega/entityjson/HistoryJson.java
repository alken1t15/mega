package kz.runtime.backfor_mega.entityjson;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class HistoryJson {
    private LocalDateTime date;

    private String nameWallet;

    private String nameCrypt;

    private Double count;
}