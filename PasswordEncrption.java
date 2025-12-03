package com.ourblogs;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class PasswordEncrption {
	public static void main(String[] args) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		String plainTextPswd = "MyPassword1234";
		String encodedPswd = encoder.encode(plainTextPswd);
		System.out.println("plain text password: " + plainTextPswd);
		System.out.println("Encrpted password: " + encodedPswd);
		String loginPassword = "MyPassword1234";
		boolean result = encoder.matches(loginPassword, encodedPswd);

		if (result) {
			System.out.println("-----Authenticated!");
		}
		else {
			System.out.println("-----Failed!");
		}
	}
}

