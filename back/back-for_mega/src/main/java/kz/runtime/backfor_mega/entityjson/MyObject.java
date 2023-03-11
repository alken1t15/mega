package kz.runtime.backfor_mega.entityjson;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;

@Getter
@Setter
@ToString
public class MyObject {
    private String id;
    private String symbol;
    private String name;
    private HashMap<String, String> platforms;
}