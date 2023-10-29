package com.AK.Rms.Model.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpOutputDto {

    private boolean signUpStatus;
    private String signUpStatusMessage;

}
