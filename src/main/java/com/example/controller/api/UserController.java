package com.example.controller.api;

import com.example.entity.AuthUser;
import com.example.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api/user")
public class UserController {

    @Resource
    BookService bookService;

    @RequestMapping("/borrow-book")
    public String borrowBook(@RequestParam("id") int bid,
                             @SessionAttribute("user") AuthUser user) {
        bookService.borrowBook(bid, user.getId());
        return "redirect:/user/book";
    }

    @GetMapping("/return-book")
    public String returnBook(@RequestParam("id") int bid,
                             @SessionAttribute("user") AuthUser user) {
        bookService.returnBook(bid, user.getId());
        return "redirect:/user/book";
    }
}
