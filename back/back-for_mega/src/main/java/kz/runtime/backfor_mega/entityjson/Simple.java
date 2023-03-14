package kz.runtime.backfor_mega.entityjson;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Simple {
    private Long price;

    private List<CoinsList> coinsLists;
}