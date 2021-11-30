package com.mytutorial.springbootsecurity.generator;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		String[][] passwords = new String[][] {
			{ "user", "password123"}, 
			{"admin" ,"nimda4321"}
		};
		
		for (String[] password : passwords) {
			System.out.println(String.format("User %s, encoded password :%s", 
					password[0], encoder.encode(password[1])));
		}

	}

}
