package com.listApp.listApp.web;

import com.listApp.listApp.business.PersonService;
import com.listApp.listApp.data.Person;
import com.listApp.listApp.database.Config;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*; // To connect to DB
import java.util.List;
import java.util.Optional;
import java.util.Properties;


@Controller
@RequestMapping("/login")
public class LoginController {

    private final PersonService personService;

    public LoginController(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String renderLogin() {
        return "login";
    }

    @PostMapping("/authenticate")
    public String authenticate(HttpSession session, HttpServletRequest request) {
        String usernameInputFromForm = request.getParameter("username");
        String passwordInputFromFrom = request.getParameter("password");

        Optional<Long> optionalUserID = isValidUser(usernameInputFromForm, passwordInputFromFrom);

        if (optionalUserID.isPresent()) {

            Long userID = optionalUserID.get();
            session.setAttribute("userID", userID);

            Person user = this.personService.getPersonById(userID).get(0);
            String userFirstName = user.getFirstName();

            session.setAttribute("isLoggedIn", true); // Set isLoggedIn to true in session
            session.setAttribute("userID", userID);
            session.setAttribute("userFirstName", userFirstName);

            return "redirect:/home"; // Redirect to the home page after successful authentication
        } else {
            return "redirect:/login?error"; // Redirect back to the login page with an error parameter
        }
    }
    public Optional<Long> isValidUser(String username, String passwordInput) {
        // If username and password pair matches, then return an Optional
//        If the user is valid, the userID is retrieved from the result set and wrapped in an
//        Optional using Optional.of(). If the user is not valid or an exception occurs, an empty
//        Optional is returned (Optional.empty()).

        Optional<Long> userID = Optional.empty();

        Config dbConfig = new Config();

        String dbUrl = dbConfig.dbURL;
        String dbUser = dbConfig.dbUsername;
        String dbPassword = dbConfig.dbPassword;

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String sql = "SELECT * FROM login WHERE username = ? AND user_password = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, passwordInput);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        userID = Optional.of(resultSet.getLong("user_id"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userID;
    }

}