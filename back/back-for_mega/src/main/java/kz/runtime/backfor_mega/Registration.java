package kz.runtime.backfor_mega;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Registration {
    private String userName;
    private String pass;
    private String email;
    private String phone;
    private LocalDate birthday;
}