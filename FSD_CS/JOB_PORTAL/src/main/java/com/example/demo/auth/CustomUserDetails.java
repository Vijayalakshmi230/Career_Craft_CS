package com.example.demo.auth;

import lombok.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entity.User;

import java.util.*;


@Getter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails{
	

	    private User user;

	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
	    }

	    public String getPassword() { return user.getPassword(); }

	    public String getUsername() { return user.getEmail(); }

	    public boolean isAccountNonExpired() { return true; }

	    public boolean isAccountNonLocked() { return true; }

	    public boolean isCredentialsNonExpired() { return true; }

	    public boolean isEnabled() { return true; }
}
