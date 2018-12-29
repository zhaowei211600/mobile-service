package com.third.enterprise.service.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtUser implements UserDetails {

    private final Integer id;
    private final String username;
    private final String password;
    private final String phoneNumber;
    private final Collection<? extends GrantedAuthority> authorities;


    public JwtUser(
            Integer id,
            String username,
            String password,
            String phoneNumber,
            Collection<? extends GrantedAuthority> authorities

    ){
        this.id = id;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.authorities = authorities;
    }

    public Integer getId(){
        return id;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
