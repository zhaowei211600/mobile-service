package com.third.mobile.service.security.user;

import com.third.mobile.bean.User;
import com.third.mobile.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        User user = userMapper.findByPhone(account);
        if(user == null){
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", account));
        }
        return UserFactory.create(user);
    }
}
