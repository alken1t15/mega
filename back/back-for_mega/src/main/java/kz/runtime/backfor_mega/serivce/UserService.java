package kz.runtime.backfor_mega.serivce;


import kz.runtime.backfor_mega.entity.User;

import java.util.List;

public interface UserService {
    void save(User user);

    User findByEmailAndPass(String email, String pass);

    List<User> findAllByEmailAndPass(String email, String pass);

    User findByUserName(String userName);

    void delete(User user);
}