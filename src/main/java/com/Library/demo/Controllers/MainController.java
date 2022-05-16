package com.Library.demo.Controllers;

import com.Library.demo.Models.User;
import com.Library.demo.Repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
        @Autowired
        private UserRepository userRepository;
        @GetMapping("/")
        public String Main(Model model,@AuthenticationPrincipal User user) {
            if(user.getId()== 999){
                model.addAttribute("title", "Главная");
                return "home";
            }else
                model.addAttribute("title", "Главная");
                return "home-user";

        }


    }
