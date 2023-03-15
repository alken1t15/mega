package kz.runtime.backfor_mega.serivce;

import kz.runtime.backfor_mega.dao.WalletRepository;
import kz.runtime.backfor_mega.entity.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletImpl implements WalletService {
    @Autowired
    private WalletRepository walletRepository;

    @Override
    public void save(Wallet wallet) {
        walletRepository.save(wallet);
    }

    @Override
    public List<Wallet> findAllById(Long id) {
        return walletRepository.findAllById(id);
    }

    @Override
    public Wallet findByNameWallet(String nameWallet) {
        return walletRepository.findByNameWallet(nameWallet);
    }


}