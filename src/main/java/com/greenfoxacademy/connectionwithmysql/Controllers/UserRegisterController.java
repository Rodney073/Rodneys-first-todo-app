package com.greenfoxacademy.connectionwithmysql.Controllers;

import com.greenfoxacademy.connectionwithmysql.Models.User;
import com.greenfoxacademy.connectionwithmysql.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserRegisterController {

    private UserService userService;
    String signInMessage = "";
    String signUpMessage = "";

    public UserRegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String login(Model model) {
        model.addAttribute("text", signInMessage);
        model.addAttribute("text2", signUpMessage);
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        if (userService.findUserByName(user.getName()).isPresent()) {
            signUpMessage = "Username already used, please Sign In!  →";
            signInMessage = "";
            return "redirect:/register";
        }
        userService.save(user);
        return "redirect:/?user_id=" + user.getId();
    }

    @PostMapping("/signIn")
    public String signIn(@ModelAttribute User user) {
        if (userService.findUserByName(user.getName()).isPresent() && userService.isPasswordOk(user.getName(), user.getPassword())) {
            return "redirect:/?user_id=" + userService.findUserByName(user.getName()).get().getId();

        } else if (userService.findUserByName(user.getName()).isPresent() && !userService.isPasswordOk(user.getName(), user.getPassword())) {
            signInMessage = "Incorrect password";
            signUpMessage = "";
            return "redirect:/register";

        } else
            signInMessage = "←  There is no user named *" + user.getName() + "* in the database, please Sign Up!";
        signUpMessage = "";
        return "redirect:/register";
    }

/*    @GetMapping("/logout")
    public String logout() {
        //this.userService.logout();
        return "redirect:/login";
    }*/
}
