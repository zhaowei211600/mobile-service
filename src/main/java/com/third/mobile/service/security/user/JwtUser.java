package com.third.mobile.service.security.user;

import com.third.mobile.util.Constants;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtUser implements UserDetails {

    private final Integer id;
    private final String username;
    private final String password;
    private final String realName;
    private final String status;


    public JwtUser(
            Integer id,
            String username,
            String password,
            String realName,
            String status

    ){
        this.id = id;
        this.username = username;
        this.password = password;
        this.realName = realName;
        this.status = status;
    }

    public Integer getId(){
        return id;
    }

    public String getRealName(){
        return realName;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
        if(status.equals(Constants.UserState.PASSED)){
            return false;
        }
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
