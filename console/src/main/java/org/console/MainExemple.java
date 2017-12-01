package org.console;

import org.core.model.User;
import org.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MainExemple {

	@Autowired
    private static UserRepository userRepository;

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String password = "admin";
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		
		//userRepository.createUser("admin", hashedPassword);
		System.out.println("ajout ok");
	}

}
