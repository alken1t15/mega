package kz.runtime.backfor_mega.dao;

import kz.runtime.backfor_mega.entity.Crypto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

public interface CryptoRepository extends JpaRepository<Crypto, Long> {
    List<Crypto> findByName(String name);
}