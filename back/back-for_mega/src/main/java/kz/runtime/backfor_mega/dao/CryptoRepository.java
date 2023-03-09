package kz.runtime.backfor_mega.dao;

import kz.runtime.backfor_mega.entity.Crypto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoRepository extends JpaRepository<Crypto,Long> {
}
