package kz.runtime.backfor_mega.entityjson;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
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
}