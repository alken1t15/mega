package kz.runtime.backfor_mega.dao;

import kz.runtime.backfor_mega.entity.User;
import kz.runtime.backfor_mega.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    List<Wallet> findAllById(Long id);

    Wallet findByNameWallet(String nameWallet);
}