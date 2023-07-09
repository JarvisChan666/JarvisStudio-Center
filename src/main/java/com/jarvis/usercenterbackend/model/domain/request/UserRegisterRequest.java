package com.jarvis.usercenterbackend.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户注册请求体1
 *
 * @author jarvis
 */

@Data
public class UserRegisterRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1443169983171609254L;

    private String userAccount;
    private String userPassword;
    private String checkPassword;

}
