package kz.runtime.backfor_mega.serivce;


import kz.runtime.backfor_mega.entity.User;

public interface UserService {
    void save(User user);

    User findByEmailAndPass(String email, String pass);
}