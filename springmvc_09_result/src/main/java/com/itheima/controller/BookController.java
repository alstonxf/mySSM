package com.itheima.controller;

import com.itheima.domain.Book;
import com.itheima.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 图书控制器，处理与图书相关的请求
 */
@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * 保存一本书的信息
     *
     * @param book 要保存的书籍对象
     * @return 保存结果
     */
    @PostMapping
    public Result save(@RequestBody Book book) {
        boolean flag = bookService.save(book);
        // 根据保存结果返回不同的操作状态码和消息
        return new Result(flag ? Code.SAVE_OK : Code.SAVE_ERR, flag);
    }

    /**
     * 更新一本书的信息
     *
     * @param book 要更新的书籍对象
     * @return 更新结果
     */
    @PutMapping
    public Result update(@RequestBody Book book) {
        boolean flag = bookService.update(book);
        // 根据更新结果返回不同的操作状态码和消息
        return new Result(flag ? Code.UPDATE_OK : Code.UPDATE_ERR, flag);
    }

    /**
     * 删除指定编号的书籍信息
     *
     * @param id 书籍编号
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean flag = bookService.delete(id);
        // 根据删除结果返回不同的操作状态码和消息
        return new Result(flag ? Code.DELETE_OK : Code.DELETE_ERR, flag);
    }

    /**
     * 根据书籍编号获取书籍信息
     *
     * @param id 书籍编号
     * @return 书籍信息
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Book book = bookService.getById(id);
        // 根据获取结果返回不同的操作状态码和消息
        Integer code = book != null ? Code.GET_OK : Code.GET_ERR;
        String msg = book != null ? "" : "数据查询失败，请重试！";
        return new Result(code, book, msg);
    }

    /**
     * 获取所有书籍信息
     *
     * @return 所有书籍信息
     */
    @GetMapping
    public Result getAll() {
        List<Book> bookList = bookService.getAll();
        // 根据获取结果返回不同的操作状态码和消息
        Integer code = bookList != null ? Code.GET_OK : Code.GET_ERR;
        String msg = bookList != null ? "" : "数据查询失败，请重试！";
        return new Result(code, bookList, msg);
    }
}
