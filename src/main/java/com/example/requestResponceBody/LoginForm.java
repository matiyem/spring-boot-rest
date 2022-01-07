package com.example.requestResponceBody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * created by Atiye Mousavi
 * Date: 9/11/2021
 * Time: 5:03 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginForm {
    private String userName;
    private String password;

}
