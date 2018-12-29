package com.third.enterprise.service.security.user;

import com.third.enterprise.bean.OperationRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class UserFactory {

    private UserFactory(){

    }

    public static User create(com.third.enterprise.bean.OperationUser operationUser){
        return new User(
                operationUser.getId(),
                operationUser.getAccount(),
                operationUser.getPassword(),
                operationUser.getContactPhone(),
                mapToGrantedAuthorities(operationUser.getRoleList())
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
