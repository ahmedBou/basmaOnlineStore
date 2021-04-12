package com.ecom.appSf.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserResponse {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private AddressResponse address;
}

