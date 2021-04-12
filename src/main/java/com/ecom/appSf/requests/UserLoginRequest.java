package com.ecom.appSf.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserLoginRequest {
    private String email;
    private String password;
}
