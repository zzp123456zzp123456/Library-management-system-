package com.example.controller.page;

import com.example.entity.AuthUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;

/**
 * 专用于处理页面响应的控制器
 */
@Controller
@RequestMapping("/auth")
public class PageController {

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/register")
    public String register(Model model, HttpSession session) {
        if (session.getAttribute("isWhole") != null) {
            session.removeAttribute("isWhole");
            model.addAttribute("isWhole", true);
        } else {
            model.addAttribute("isWhole", false);
        }
        return "register";
    }

    @RequestMapping("/index")
    public String index(Model model, @SessionAttribute("user") AuthUser user) {
        model.addAttribute("user", user);
        return "index";
    }
}
