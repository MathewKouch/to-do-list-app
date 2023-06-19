package com.listApp.listApp.business;

import com.listApp.listApp.data.Login;
import com.listApp.listApp.data.LoginRepository;
import com.listApp.listApp.data.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoginService {

    private final LoginRepository loginRepository;

    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

//    public List<Login> getUsernameAndPasswordByUserId(Long userID) {
//        Iterable<Login> logins = this.loginRepository.findLoginByUserId(userID);
//        Map<String, Login> LoginMap = new HashMap();
//        logins.forEach(login -> {
//            Login aLogin = new Login();
////            aLogin.setUserId(login.getUserId());
//            aLogin.setUsername(login.getUsername());
//            aLogin.setUser_password(login.getUser_password());
//            LoginMap.put(login.getUsername(), aLogin);
//        });
//
//        List<Login> allLoginsByUserId = new ArrayList<>();
//        for (String id : LoginMap.keySet()) {
//            allLoginsByUserId.add(LoginMap.get(id));
//        }
//
//        return allLoginsByUserId;
//    }
}
