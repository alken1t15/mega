package kz.runtime.backfor_mega.entityjson;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MainObject {

   private RodObject rodObject;

   private List<Market> marketList;
}