package kz.runtime.backfor_mega.entityjson;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateAccount {
    private String userName;

    private String userNameModified;

    private String firstName;

    private String secondName;

    private String lastName;

    private Integer age;

    private String phone;

    private LocalDate birthday;
}