package top.chenfu.demo.dao;

import tk.mybatis.mapper.common.Mapper;
import top.chenfu.demo.model.User;

public interface UserMapper extends Mapper<User> {
    User selectUserByUsername(String username);
}