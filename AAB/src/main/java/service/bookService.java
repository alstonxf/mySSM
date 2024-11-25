package service;

import dao.user;
import org.springframework.stereotype.Service;

import java.util.List;


public interface bookService {

    void save();
    List<user> getUsersInfo();
}
