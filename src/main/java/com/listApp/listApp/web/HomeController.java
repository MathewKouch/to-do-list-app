package com.listApp.listApp.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping({"/home", "/index.html", "/", " "})
@SessionAttributes({"isLoggedIn", "userFirstName"})
public class HomeController {

    @RequestMapping
    public String renderHome(HttpSession session) {
        Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
        if (isLoggedIn!=null) {
            session.setAttribute("isLoggedIn", isLoggedIn);
            return "home_template";
        } else {
            session.setAttribute("isLoggedIn", false);
            return "home_template";
        }

    }

    @GetMapping("/sign-out")
    public String signOut(SessionStatus sessionStatus) {
        sessionStatus.setComplete(); // Clear the session attributes
        return "redirect:/login"; // Redirect to the login page
    }
}
