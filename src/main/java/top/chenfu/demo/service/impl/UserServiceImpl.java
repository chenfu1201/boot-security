package top.chenfu.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import top.chenfu.demo.dao.RoleMapper;
import top.chenfu.demo.dao.UserMapper;
import top.chenfu.demo.model.Role;
import top.chenfu.demo.model.User;
import top.chenfu.demo.service.UserService;

import java.util.List;
import java.util.Objects;

/****
 * @Author:chenfu
 * @Description:User业务层接口实现类
 * @Date 2019/6/14 0:16
 *****/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    /**
     * User条件+分页查询
     *
     * @param user 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<User> findPage(User user, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        //搜索条件构建
        Example example = createExample(user);
        //执行搜索
        return new PageInfo<User>(userMapper.selectByExample(example));
    }

    /**
     * User分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<User> findPage(int page, int size) {
        //静态分页
        PageHelper.startPage(page, size);
        //分页查询
        return new PageInfo<User>(userMapper.selectAll());
    }

    /**
     * User条件查询
     *
     * @param user
     * @return
     */
    @Override
    public List<User> findList(User user) {
        //构建查询条件
        Example example = createExample(user);
        //根据构建的条件查询数据
        return userMapper.selectByExample(example);
    }


    /**
     * User构建查询对象
     *
     * @param user
     * @return
     */
    public Example createExample(User user) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        if (user != null) {
            // 
            if (!StringUtils.isEmpty(user.getId())) {
                criteria.andEqualTo("id", user.getId());
            }
            // 
            if (!StringUtils.isEmpty(user.getPassword())) {
                criteria.andEqualTo("password", user.getPassword());
            }
            // 
            if (!StringUtils.isEmpty(user.getUsername())) {
                criteria.andLike("username", "%" + user.getUsername() + "%");
            }
            // 
            if (!StringUtils.isEmpty(user.isAccountNonExpired())) {
                criteria.andEqualTo("accountNonExpired", user.isAccountNonExpired());
            }
            // 
            if (!StringUtils.isEmpty(user.isAccountNonLocked())) {
                criteria.andEqualTo("accountNonLocked", user.isAccountNonLocked());
            }
            // 
            if (!StringUtils.isEmpty(user.isCredentialsNonExpired())) {
                criteria.andEqualTo("credentialsNonExpired", user.isCredentialsNonExpired());
            }
            // 
            if (!StringUtils.isEmpty(user.isEnabled())) {
                criteria.andEqualTo("enabled", user.isEnabled());
            }
        }
        return example;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        userMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改User
     *
     * @param user
     */
    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKey(user);
    }

    /**
     * 增加User
     *
     * @param user
     */
    @Override
    public void add(User user) {
        userMapper.insert(user);
    }

    /**
     * 根据ID查询User
     *
     * @param id
     * @return
     */
    @Override
    public User findById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询User全部数据
     *
     * @return
     */
    @Override
    public List<User> findAll() {
        return userMapper.selectAll();
    }

    /**
     * 认证方法
     *
     * @param username 用户名
     * @return 用户
     * @throws UsernameNotFoundException 异常信息
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectUserByUsername(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        List<Role> roles = roleMapper.selectRolesByUserId(user.getId());
        user.setRoles(roles);
        return user;
    }

}
