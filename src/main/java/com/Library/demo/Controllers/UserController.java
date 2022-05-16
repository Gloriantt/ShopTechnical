package com.Library.demo.Controllers;

import com.Library.demo.Models.User;
import com.Library.demo.Repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/userprofile")
    public String UserList(Model model,@AuthenticationPrincipal User user){
        List<User> uruser = userRepository.findurid(user.getId());
        model.addAttribute("users",uruser);
        return "Users";
    }
}
