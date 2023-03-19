package kz.runtime.backfor_mega.entityjson;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCrypt {
    private String nameCrypt;
    private Double count;
    private String imgName;

    public UpdateCrypt() {
    }

    public UpdateCrypt(String nameCrypt, Double count, String imgName) {
        this.nameCrypt = nameCrypt;
        this.count = count;
        this.imgName = imgName;
    }
}