package kz.runtime.backfor_mega.serivce;

import kz.runtime.backfor_mega.entity.Card;
import kz.runtime.backfor_mega.entity.Crypto;

import java.util.List;

public interface CryptoService {
    void save(Crypto crypto);

    List<Crypto> findByName(String name);

    List<Crypto> findByFullName(String name);
}