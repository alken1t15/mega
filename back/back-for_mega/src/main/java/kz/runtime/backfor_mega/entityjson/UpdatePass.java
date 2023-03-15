package kz.runtime.backfor_mega.entityjson;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePass {
    private String email;
    private String pass;
    private String newPass;
}