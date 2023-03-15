package kz.runtime.backfor_mega.serivce;

import kz.runtime.backfor_mega.entity.User;
import kz.runtime.backfor_mega.entity.Wallet;

import java.util.List;

public interface WalletService {
    void save(Wallet wallet);

    List<Wallet> findAllById(Long id);

    Wallet findByNameWallet(String nameWallet);

    void delete(Wallet wallet);
}