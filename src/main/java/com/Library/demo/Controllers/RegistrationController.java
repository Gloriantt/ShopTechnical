package com.Library.demo.Controllers;

import com.Library.demo.Models.Role;
import com.Library.demo.Models.User;
import com.Library.demo.Repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration() {
        return "Registration";
    }

    @PostMapping("/registration")
    public String AddUser(User user, Map<String, Object> model) {
        User usersfromdb = userRepository.findByUsername(user.getUsername());
        if (usersfromdb != null) {
            model.put("message", "User exists");
            return "Registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        return "redirect:/login";
    }



}

