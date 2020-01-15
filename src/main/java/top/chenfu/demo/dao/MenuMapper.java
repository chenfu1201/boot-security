package top.chenfu.demo.dao;

import tk.mybatis.mapper.common.Mapper;
import top.chenfu.demo.model.Menu;

import java.util.List;

public interface MenuMapper extends Mapper<Menu> {

    List<Menu> selectAll();

}