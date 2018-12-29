package com.third.enterprise.service.security.user;

import com.third.enterprise.bean.OperationUser;
import com.third.enterprise.dao.OperationUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private OperationUserMapper operationUserMapper;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        OperationUser operationUser = operationUserMapper.selectUserAndRolesByAccount(account);
        if(operationUser == null){
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", account));
        }
        return UserFactory.create(operationUser);
    }
}
