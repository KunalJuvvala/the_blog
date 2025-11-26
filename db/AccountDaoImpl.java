package com.ourblogs.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ourblogs.entity.Account;
import com.ourblogs.service.AccountDao;

public class AccountDaoImpl implements AccountDao {

   private Connection connection;

   public AccountDaoImpl(Connection connection) {
      this.connection = connection;
   }

   @Override
   public ArrayList<Account> getAllAccounts(){
      ArrayList<Account> accounts = new ArrayList<>();
      String sql = "SELECT * FROM account";
      try (PreparedStatement ps = connection.prepareStatement(sql)) {
         try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
               Account acc = new Account();
               String email = rs.getString("email");
               String password = rs.getString("password");
               acc.setEmail(email);
               acc.setPassword(password);
               accounts.add(acc);
            }
         } catch (SQLException e){
            System.out.println(e);
         }
      } catch (SQLException e) {
         System.out.println(e);
      } 
      return accounts;
   }
   
   @Override
   public Account getAccountByEmail(String email) {
      String sql = "SELECT * FROM account WHERE email = ?";
      try (PreparedStatement ps = connection.prepareStatement(sql)) {
         ps.setString(1, email);
         try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
               Account acc = new Account();
               String password = rs.getString("password");
               acc.setEmail(email);
               acc.setPassword(password);
               return acc;
            } else {
               return null;
            }
         } catch (SQLException e){
            System.out.println(e);
         }
           
      } catch (SQLException e) {
         System.out.println(e);
      } 
      return null;
   }
   
   @Override
   public void insertAccount(Account account) {
      String sql = "INSERT INTO account(email, password) VALUES (?,?)";
      try (PreparedStatement ps = connection.prepareStatement(sql)) {
         ps.setString(1, account.getEmail());
         ps.setString(2, account.getPassword());
         ps.executeUpdate();
      } catch (SQLException e) {
         System.out.println(e);
      } 
   }
}
