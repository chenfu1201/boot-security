package top.chenfu.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import top.chenfu.demo.dao.UserRoleMapper;
import top.chenfu.demo.model.UserRole;
import top.chenfu.demo.service.UserRoleService;

import java.util.List;

/****
 * @Author: chenfu
 * @Description:UserRole业务层接口实现类
 * @Date 2019/6/14 0:16
 *****/
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;


    /**
     * UserRole条件+分页查询
     * @param userRole 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<UserRole> findPage(UserRole userRole, int page, int size){
        //分页
        PageHelper.startPage(page,size);
        //搜索条件构建
        Example example = createExample(userRole);
        //执行搜索
        return new PageInfo<UserRole>(userRoleMapper.selectByExample(example));
    }

    /**
     * UserRole分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<UserRole> findPage(int page, int size){
        //静态分页
        PageHelper.startPage(page,size);
        //分页查询
        return new PageInfo<UserRole>(userRoleMapper.selectAll());
    }

    /**
     * UserRole条件查询
     * @param userRole
     * @return
     */
    @Override
    public List<UserRole> findList(UserRole userRole){
        //构建查询条件
        Example example = createExample(userRole);
        //根据构建的条件查询数据
        return userRoleMapper.selectByExample(example);
    }


    /**
     * UserRole构建查询对象
     * @param userRole
     * @return
     */
    public Example createExample(UserRole userRole){
        Example example=new Example(UserRole.class);
        Example.Criteria criteria = example.createCriteria();
        if(userRole!=null){
            // 主键ID
            if(!StringUtils.isEmpty(userRole.getId())){
                    criteria.andEqualTo("id",userRole.getId());
            }
            // 用户ID
            if(!StringUtils.isEmpty(userRole.getUserId())){
                    criteria.andEqualTo("userId",userRole.getUserId());
            }
            // 角色ID
            if(!StringUtils.isEmpty(userRole.getRoleId())){
                    criteria.andEqualTo("roleId",userRole.getRoleId());
            }
        }
        return example;
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Integer id){
        userRoleMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改UserRole
     * @param userRole
     */
    @Override
    public void update(UserRole userRole){
        userRoleMapper.updateByPrimaryKey(userRole);
    }

    /**
     * 增加UserRole
     * @param userRole
     */
    @Override
    public void add(UserRole userRole){
        userRoleMapper.insert(userRole);
    }

    /**
     * 根据ID查询UserRole
     * @param id
     * @return
     */
    @Override
    public UserRole findById(Integer id){
        return  userRoleMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询UserRole全部数据
     * @return
     */
    @Override
    public List<UserRole> findAll() {
        return userRoleMapper.selectAll();
    }
}
