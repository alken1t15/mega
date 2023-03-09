package kz.runtime.backfor_mega.serivce;

import kz.runtime.backfor_mega.dao.CryptoRepository;
import kz.runtime.backfor_mega.entity.Crypto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CryptoImp implements CryptoService {
    @Autowired
   private CryptoRepository cryptoRepository;

    @Override
    public void save(Crypto crypto) {
        cryptoRepository.save(crypto);
    }
}
