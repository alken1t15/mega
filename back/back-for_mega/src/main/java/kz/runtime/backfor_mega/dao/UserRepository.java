package kz.runtime.backfor_mega.dao;

import kz.runtime.backfor_mega.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndPass(String email, String pass);
}