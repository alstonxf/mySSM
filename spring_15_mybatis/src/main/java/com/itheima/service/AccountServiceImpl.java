package com.itheima.service;

import com.itheima.dao.Account;
import com.itheima.dao.AccountDao;

import java.util.List;


public class AccountServiceImpl implements AccountService {

    private AccountDao accountDao;

    public void save(Account account) {
        accountDao.save(account);
    }

    public void update(Account account){
        accountDao.update(account);
    }

    public void delete(Integer id) {
        accountDao.delete(id);
    }

    public Account findById(Integer id) {
        return accountDao.findById(id);
    }

    public List<Account> findAll() {
        return accountDao.findAll();
    }
}