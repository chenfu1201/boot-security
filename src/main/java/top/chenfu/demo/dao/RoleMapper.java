package top.chenfu.demo.dao;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import top.chenfu.demo.model.Role;

import java.util.List;

public interface RoleMapper extends Mapper<Role> {

    List<Role> selectRolesByUserId(@Param(value = "userId") Integer userId);

}