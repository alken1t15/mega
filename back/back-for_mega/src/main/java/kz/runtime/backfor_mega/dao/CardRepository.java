package kz.runtime.backfor_mega.dao;

import kz.runtime.backfor_mega.entity.Card;
import kz.runtime.backfor_mega.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card,Long> {


}
