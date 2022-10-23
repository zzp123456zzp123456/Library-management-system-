package com.example.controller.page;

import com.example.entity.AuthUser;
import com.example.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserPage {

    @Resource
    BookService bookService;
    @RequestMapping("index")
    public String index(HttpSession session, Model model) {
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("bookList", bookService.getAllBookWithoutBorrow());
        return "user/index";
    }
    @RequestMapping("book")
    public String book(HttpSession session, Model model) {
        AuthUser user = (AuthUser) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("bookList", bookService.getAllBorrowedBook(user.getId()));
        return "user/book";
    }
}
