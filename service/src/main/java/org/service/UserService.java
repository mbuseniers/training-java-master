package org.service;

import org.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {//implements UserDetailsService{

	@Autowired
    private UserRepository userRepository;
}
