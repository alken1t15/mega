package kz.runtime.backfor_mega.entityjson.coins;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.HashMap;

@Getter
public class BinanceCoinJson {

    @JsonProperty("binancecoin")
    private HashMap<String, Double> binanceCoin;
}