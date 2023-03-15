package kz.runtime.backfor_mega.serivce;

import kz.runtime.backfor_mega.entity.Card;
import kz.runtime.backfor_mega.entity.User;

public interface CardService {

    void save(Card card);

    Card findByNumberAndDataNameAndSvv(String number, String dataName, String svv);

    void delete(Card card);
}