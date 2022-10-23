package com.example.service;

import com.example.entity.Book;
import com.example.entity.BorrowDetails;

import java.util.List;

public interface BookService {

    List<Book> getAllBook();

    void deleteBook(int id);

    void addBook(String title, String describe, double price);

    void borrowBook(int bid,int id);

    List<Book> getAllBookWithoutBorrow();

    List<Book> getAllBorrowedBook(int id);

    void returnBook(int bid,int id);

    List<BorrowDetails> getAllBorrowDetails();
}
