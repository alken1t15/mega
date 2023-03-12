package kz.runtime.backfor_mega.serivce;

import kz.runtime.backfor_mega.dao.UserRepository;
import kz.runtime.backfor_mega.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByEmailAndPass(String email, String pass) {
        return userRepository.findByEmailAndPass(email, pass);
    }

    @Override
    public List<User> findAllByEmailAndPass(String email, String pass) {
        return userRepository.findAllByEmailAndPass(email,pass);
    }
}