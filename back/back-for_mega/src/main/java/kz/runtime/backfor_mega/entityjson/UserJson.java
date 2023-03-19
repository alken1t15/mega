package kz.runtime.backfor_mega.entityjson;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UserJson {
    private Long id;
    private String userName;
    private String firstName;
    private String secondName;
    private String middle_name;
    private Integer age;

    private String address;

    private String phone;

    private String pass;

    private String email;

    private LocalDate birthday;

    public UserJson(Long id, String userName, String firstName, String secondName, String middle_name, Integer age, String address, String phone, String pass, String email, LocalDate birthday) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.secondName = secondName;
        this.middle_name = middle_name;
        this.age = age;
        this.address = address;
        this.phone = phone;
        this.pass = pass;
        this.email = email;
        this.birthday = birthday;
    }
}