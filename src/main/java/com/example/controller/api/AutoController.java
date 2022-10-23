package com.example.controller.api;

import com.example.service.AutoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/api")
public class AutoController {

    @Resource
    AutoService service;

    @PostMapping("/doRegister")
    public String doRegister(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("sex") String sex,
                             @RequestParam("grade") String grade,
                             HttpSession session) throws Exception {
        if (username != "" && password != "") {
            if (sex != "" && grade != "") {
                service.register(username, password, sex, grade);
                return "redirect:/auth/login";
            }
        }
        session.setAttribute("isWhole", true);
        return "forward:/auth/register";
    }

    @GetMapping("/doRegister")
    public String getRegister() {

        return "redirect:/auth/register";
    }
}
