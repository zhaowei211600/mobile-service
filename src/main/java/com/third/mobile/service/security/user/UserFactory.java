package com.third.mobile.service.security.user;

import com.third.mobile.bean.OperationRole;
import com.third.mobile.bean.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class UserFactory {

    private UserFactory(){

    }

    public static JwtUser create(User user){
        return new JwtUser(
                user.getId(),
                user.getPhone(),
                user.getPassword(),
                user.getRealName()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<OperationRole> authorities){
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        GrantedAuthority grantedAuthority;
        for (OperationRole authority : authorities){
            grantedAuthority = new SimpleGrantedAuthority(authority.getName());
            grantedAuthorities.add(grantedAuthority);
        }
        return grantedAuthorities;
    }
}
