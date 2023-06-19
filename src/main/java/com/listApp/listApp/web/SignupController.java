package com.listApp.listApp.web;

import com.listApp.listApp.data.Login;
import com.listApp.listApp.data.LoginRepository;
import com.listApp.listApp.data.Person;
import com.listApp.listApp.data.PersonRepository;
import com.listApp.listApp.database.Config;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.sql.*;
import java.util.Optional;

@Controller
public class SignupController {

    private final PersonRepository personRepository;

    private final LoginRepository loginRepository;

    public SignupController(PersonRepository personRepository, LoginRepository loginRepository) {
        this.personRepository = personRepository;
        this.loginRepository = loginRepository;
    }

    @GetMapping("/signup")
    public String showSignupForm() {
        return "signup";
    }


    @PostMapping("/signup/createAccount")
    public String createAccount(@RequestParam("username") String username,
                                @RequestParam("firstName") String firstName,
                                @RequestParam("lastName") String lastName,
                                @RequestParam("phoneNumber") String phoneNumber,
                                @RequestParam("password") String password1,
                                @RequestParam("confirmPassword") String password2,
                                HttpSession session) {

        // Insert new user into db

        // Check if the username is already in the database and if the passwords match
        if (!checkUsernameExists(username) && checkPasswordMatch(password1, password2)){

            Person person = new Person();
            person.setEmailAddress(username);
            person.setFirstName(firstName);
            person.setLastName(lastName);
            person.setPhoneNumber(phoneNumber);
            personRepository.save(person);

            Login newLogin = new Login();
            newLogin.setUser_password(password1);
            newLogin.setUsername(username);
            newLogin.setPerson(person);
            loginRepository.save(newLogin);

            session.setAttribute("isLoggedIn", true);
            session.setAttribute("userID", person.getPersonId());
            session.setAttribute("userFirstName", person.getFirstName());

            return "redirect:/index.html";

        }

        return "redirect:/signup";
    }

    public Boolean checkUsernameExists(String username) {
        // return true if username exists
        Config dbConfig = new Config();

        String dbUrl = dbConfig.dbURL;
        String dbUser = dbConfig.dbUsername;
        String dbPassword = dbConfig.dbPassword;

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String sql = "SELECT * FROM login WHERE username = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);

                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            // Handle the SQLException appropriately
            e.printStackTrace();
        }

        return false;
    }

    public Boolean checkPasswordMatch(String password1, String password2){
        return password1.equals(password2);
    }

}
