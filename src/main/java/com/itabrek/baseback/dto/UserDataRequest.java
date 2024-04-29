package com.itabrek.baseback.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDataRequest {
    private String fullName;
    private String phone;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date dateOfBirth;
}
