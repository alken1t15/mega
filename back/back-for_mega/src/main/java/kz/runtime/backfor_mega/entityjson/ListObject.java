package kz.runtime.backfor_mega.entityjson;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListObject {
    private String name;

    private String fullName;

    private String nameImg;

    public ListObject() {
    }

    public ListObject(String name, String fullName, String nameImg) {
        this.name = name;
        this.fullName = fullName;
        this.nameImg = nameImg;
    }
}