package com.itabrek.baseback.dto;

import com.itabrek.baseback.entity.UserData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDataResponse {
    private UserResponse user;
    private String firstname;
    private String lastname;
    private String phone;
    private Date dateOfBirth;

    public UserDataResponse(UserData userData) {
        this.user = new UserResponse(userData.getUser());
        this.firstname = userData.getFirstname();
        this.lastname = userData.getLastname();
        this.phone = userData.getPhone();
        this.dateOfBirth = userData.getDateOfBirth();
    }
}
