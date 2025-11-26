package com.ourblogs.ui.view;
import java.util.Scanner;
import com.ourblogs.entity.BloggerDTO;

import com.ourblogs.entity.Account;
import com.ourblogs.entity.BloggerDTO;

public class LoginView {
	public static Account acceptUserLogin(String message, Scanner scanner) {
		System.out.println(message);
		Account account = new Account();
		System.out.print("\nPlease enter your email, then press Enter: ");
		account.setEmail(scanner.nextLine());
		System.out.print("\nPlease enter your password, then press Enter: ");
		account.setPassword(scanner.nextLine());
		
		
		return account;
	}

}
	
	
