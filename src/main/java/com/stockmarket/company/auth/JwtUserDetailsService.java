package com.stockmarket.company.auth;

import com.stockmarket.company.entity.MyUser;
import com.stockmarket.company.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    MyUserRepository myUserRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    public Collection<? extends GrantedAuthority> getAuthorities() {

        MyUser user = new MyUser() ;

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();


        authorities.add(new SimpleGrantedAuthority(user.getRole()));


        return authorities;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser user = myUserRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        //non dto code below	//return new org.springframework.security.core.userdetails.User(user.getname(), user.getpassword(),
        //	new ArrayList<>());
        return new MyUserDetails(user) {
        };//you have to implement userdetails if you dont want to use dto
    }

    // implement without dto	public MyUser save(UserDto user) {
    public MyUser save(MyUser user) {
        MyUser newUser = new MyUser();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setEmail(user.getEmail());
        newUser.setRole(user.getRole());
        newUser.setMobile(user.getMobile());
        // todo: email
        return myUserRepository.save(newUser);
    }
}
