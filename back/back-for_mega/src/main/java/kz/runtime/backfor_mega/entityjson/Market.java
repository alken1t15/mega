package kz.runtime.backfor_mega.entityjson;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Market {
    private List<LocalDateTime> date;

    private String name;

    private List<Double> price;
}