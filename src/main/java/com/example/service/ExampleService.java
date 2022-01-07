package com.example.service;

import com.example.requestResponceBody.LoginForm;
import org.springframework.stereotype.Service;

/**
 * created by Atiye Mousavi
 * Date: 9/11/2021
 * Time: 5:02 PM
 */
@Service
public class ExampleService {
    public boolean fakeAuthentication(LoginForm lf){
        return true;
    }
}
