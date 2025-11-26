package com.ourblogs.service;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ourblogs.entity.Account;

public interface AccountDao {
	ArrayList<Account> getAllAccounts() throws SQLException;
	Account getAccountByEmail(String email);
	void insertAccount(Account account);
}