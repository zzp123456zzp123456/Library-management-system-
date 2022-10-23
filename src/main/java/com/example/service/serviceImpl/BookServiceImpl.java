package com.example.service.serviceImpl;

import com.example.entity.Book;
import com.example.entity.Borrow;
import com.example.entity.BorrowDetails;
import com.example.mapper.BookMapper;
import com.example.mapper.UserMapper;
import com.example.service.BookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Resource
    BookMapper bookMapper;
    @Resource
    UserMapper userMapper;
    @Override
    public List<Book> getAllBook() {
        return bookMapper.allBook();
    }

    @Override
    public void deleteBook(int id) {
        bookMapper.delBook(id);
    }

    @Override
    public void addBook(String title, String describe, double price) {
        bookMapper.addBook(title, describe, price);
    }

    @Override
    public void borrowBook(int bid,int id) {
        Integer sid = userMapper.getSidByUserId(id);
        if (sid == null) {
            return;
        }
        bookMapper.addBorrow(bid, sid);
    }

    @Override
    public List<Book> getAllBookWithoutBorrow() {
        List<Book> books = bookMapper.allBook();
        List<Integer> borrows = bookMapper.borrowList()
                .stream()
                .map(Borrow::getBid)
                .collect(Collectors.toList());
        return books.stream()
                .filter(book -> !borrows.contains(book.getBid()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> getAllBorrowedBook(int id) {
        Integer sid = userMapper.getSidByUserId(id);
        if (sid == null) {
            return Collections.emptyList();
        }

        return bookMapper.borrowListBySid(sid)
                .stream()
                .map(borrow -> {
                    return bookMapper.getBookById(borrow.getBid());
                })
                .collect(Collectors.toList());
    }

    @Override
    public void returnBook(int bid, int id) {
        Integer sid = userMapper.getSidByUserId(id);
        if (sid == null) {
            return ;
        }
        bookMapper.deleteBorrow(bid, sid);
    }

    @Override
    public List<BorrowDetails> getAllBorrowDetails() {
        return bookMapper.borrowDetailsList();
    }
}
