package org.uy.record.service;

import org.springframework.stereotype.Service;
import org.uy.record.base64.Base64Encode;
import org.uy.record.dao.UserDao;
import org.uy.record.entity.UserDto;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public UserDto login(String username, String password) {
        password = Base64Encode.encode(password);
        return userDao.findUser(username,password);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
