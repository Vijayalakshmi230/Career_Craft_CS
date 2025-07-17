package com.example.demo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import com.example.demo.entity.User;
import com.example.demo.repo.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	 @Autowired
	    private UserRepo repo;

	    @Override
	    public UserDetails loadUserByUsername(String email)
	            throws UsernameNotFoundException {
	        User user = repo.findByEmail(email)
	                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
	        return new CustomUserDetails(user);
	    }

}
