package kz.runtime.backfor_mega.dao;

import kz.runtime.backfor_mega.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {
}