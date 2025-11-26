package com.ourblogs;

// BCryptPasswordEncoder will be used to encode password and 
// authenticate the user when they log in. 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class PasswordEncrption {
	public static void main(String[] args) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		String plainTextPswd = "MyPassword1234";
		
		// encode the password to an ASCII string of 60 characters. 
		String encodedPswd = encoder.encode(plainTextPswd);
		System.out.println("plain text password: " + plainTextPswd);
		System.out.println("Encrpted password: " + encodedPswd);
		
		// the password entered for login
		// You can change it to a different string to fail authentication
		String loginPassword = "MyPassword1234";

		// check the login password matches the saved password. 
		boolean result = encoder.matches(loginPassword, encodedPswd);

		if (result) {
			System.out.println("-----Authenticated!");
		}
		else {
			System.out.println("-----Failed!");
		}
	}
}

