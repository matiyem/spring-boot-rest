package com.example.springPagination.service;

import com.example.springPagination.model.Preference;
import com.example.springPagination.model.User;
import org.springframework.stereotype.Service;

/**
 * created by Atiye Mousavi
 * Date: 9/12/2021
 * Time: 1:23 PM
 */
//این Component@ و Service@ و Repository@ و Controller@ بصورت اتوماتیک توسط spring bean رجیستر میشوند
@Service
public class UserService implements IUserService {

    @Override
    public User getCurrentUser() {

        Preference preference=new Preference();
        preference.setId(1L);
        preference.setTimezone("Asia/Calcutta");

        User user=new User();
        user.setId(1L);
        user.setName("Micheal");
        user.setPreference(preference);
        return user;
    }
}
