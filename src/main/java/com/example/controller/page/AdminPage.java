package com.example.controller.page;

import com.example.service.BookService;
import com.example.service.StatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminPage {
    @Resource
    BookService bookService;

    @Resource
    StatService statService;

    @RequestMapping("index")
    public String index(HttpSession session, Model model) {
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("borrowList", bookService.getAllBorrowDetails());
        model.addAttribute("stat", statService.getGlobalStat());
        return "admin/index";
    }
    @RequestMapping("book")
    public String book(HttpSession session, Model model) {
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("bookList", bookService.getAllBook());
        return "admin/book";
    }
    @RequestMapping("add-book")
    public String addBook(HttpSession session, Model model) {
        model.addAttribute("user", session.getAttribute("user"));

        return "admin/add-book";
    }
}
