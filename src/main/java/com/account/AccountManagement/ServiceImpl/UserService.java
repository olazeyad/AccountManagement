package com.account.AccountManagement.ServiceImpl;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserService implements UserDetailsService {

        @Override
        public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
            if (userName.equalsIgnoreCase("admin"))
                return new User("admin","admin", Arrays.asList(new SimpleGrantedAuthority("ADMIN")));
            else if (userName.equalsIgnoreCase("user"))
                return new User("user","user", Arrays.asList(new SimpleGrantedAuthority("USER")));
            else
                throw new UsernameNotFoundException("User not valid");
        }
}