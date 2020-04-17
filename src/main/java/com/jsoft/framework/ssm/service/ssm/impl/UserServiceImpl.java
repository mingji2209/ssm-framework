package com.jsoft.framework.ssm.service.ssm.impl;

import com.jsoft.framework.ssm.dao.ssm.UserDao;
import com.jsoft.framework.ssm.dto.ssm.UserDTO;
import com.jsoft.framework.ssm.model.ssm.User;
import com.jsoft.framework.ssm.service.ssm.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;


    @Override
    public UserDTO find(int id) {
        User user = userDao.find( id );
        return converMood2DTA(user );
    }

    private UserDTO converMood2DTA(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId( user.getId() );
        userDTO.setName( user.getName() );
        userDTO.setAccount( user.getAccount() );
        return userDTO;
    }
}
