package org.uy.record.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.uy.record.entity.UserDto;

@Repository
public interface UserDao extends BaseDao{

    UserDto findUser(@Param("username") String username, @Param("password") String password);
}
