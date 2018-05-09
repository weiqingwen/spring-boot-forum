package com.qingwenwei.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.qingwenwei.persistence.model.User;
import com.qingwenwei.service.UserService;

public class MyUserDetailsService implements UserDetailsService {

	private boolean accountNonExpired = true;
	private boolean credentialsNonExpired = true;
	private boolean accountNonLocked = true;

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			User user = this.userService.findByUsername(username);
			if (null == user) {
				throw new UsernameNotFoundException("Can't find user by username: " + username);
			}

			List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();

			// grant roles to user
			for (String role : user.getRolesSet()) {
				grantedAuthorities.add(new SimpleGrantedAuthority(role));
			}

			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
					user.isEnabled(), accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuthorities);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
