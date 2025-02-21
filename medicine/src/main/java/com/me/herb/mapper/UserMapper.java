package com.me.herb.mapper;

import com.me.herb.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    // 添加用户信息
    int add(User user);

    // 删除用户信息
    void delete(Integer id);

    // 修改用户信息
    void update(User user);

    // 根据id查找用户
    User queryById(Integer id);

    // 查询全部用户信息
    List<User> queryAll();

    // 通过用户名、手机号、邮箱查找用户
    User findUserByIdentity(String identity);

    // 通过用户名、手机号、邮箱和密码查找用户
    User findUserByIdentityAndPassword(@Param("identity") String identity, @Param("password") String password);

    // 修改密码
    int updateUserPassword(Map<String, Object> params);
}
