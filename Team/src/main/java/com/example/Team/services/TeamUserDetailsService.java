package com.example.Team.services;

import com.example.Team.daos.UserDao;
import com.example.Team.models.User;
import eu.fraho.spring.securityJwt.base.dto.JwtUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TeamUserDetailsService implements UserDetailsService {
    private UserDao userDao;
    public TeamUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getUserByUsername(username);
        List<String> roles = userDao.getUserRoles(username);

         if (user == null) {
             throw new UsernameNotFoundException("Username not found");
         }

         JwtUser jwtUser = new JwtUser();
         jwtUser.setUsername(user.getUsername());
         jwtUser.setPassword(user.getPassword());
         jwtUser.setAccountNonExpired(true);
         jwtUser.setAccountNonLocked(true);
         jwtUser.setCredentialsNonExpired(true);
         jwtUser.setEnabled(true);
         jwtUser.setApiAccessAllowed(true);

         List<GrantedAuthority> authorities = new ArrayList<>();

         for (String role : roles) {
             authorities.add(new SimpleGrantedAuthority(role));
         }

         jwtUser.setAuthorities(authorities);
        return jwtUser;
    }
}
