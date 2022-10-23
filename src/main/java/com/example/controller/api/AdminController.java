package com.example.controller.api;

import com.example.service.serviceImpl.BookServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api/admin")
public class AdminController {

    @Resource
    BookServiceImpl bookService;

    @GetMapping("/del-book")
    public String delBook(@RequestParam("id") int id) {
        bookService.deleteBook(id);
        return "redirect:/admin/book";
    }

    @PostMapping("/add-book")
    public String addBook(@RequestParam("title") String title,
                          @RequestParam("describe") String describe,
                          @RequestParam("price") double price) {
        bookService.addBook(title, describe, price);
        return "redirect:/admin/book";
    }
}
