package org.uy.record.service;

import org.uy.record.entity.UserDto;

public interface UserService {

    UserDto login(String username,String password);

}
