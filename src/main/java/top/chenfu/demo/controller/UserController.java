package top.chenfu.demo.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.chenfu.demo.util.Constant;
import top.chenfu.demo.model.User;
import top.chenfu.demo.service.UserService;
import top.chenfu.demo.vo.ResponseData;

import java.util.List;

/****
 * @Author:chenfu
 * @Description:
 * @Date 2019/6/14 0:18
 *****/
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    /***
     * User分页条件搜索实现
     * @param user
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}")
    public ResponseData<PageInfo> findPage(@RequestBody(required = false) User user, @PathVariable int page, @PathVariable int size) {
        //调用UserService实现分页条件查询User
        PageInfo<User> pageInfo = userService.findPage(user, page, size);
        return new ResponseData(true, Constant.OK, "查询成功", pageInfo);
    }

    /***
     * User分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}")
    public ResponseData<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        //调用UserService实现分页查询User
        PageInfo<User> pageInfo = userService.findPage(page, size);
        return new ResponseData<PageInfo>(true, Constant.OK, "查询成功", pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param user
     * @return
     */
    @PostMapping(value = "/search")
    public ResponseData<List<User>> findList(@RequestBody(required = false) User user) {
        //调用UserService实现条件查询User
        List<User> list = userService.findList(user);
        return new ResponseData<List<User>>(true, Constant.OK, "查询成功", list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public ResponseData delete(@PathVariable Integer id) {
        //调用UserService实现根据主键删除
        userService.delete(id);
        return new ResponseData(true, Constant.OK, "删除成功");
    }

    /***
     * 修改User数据
     * @param user
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    public ResponseData update(@RequestBody User user, @PathVariable Integer id) {
        //设置主键值
        user.setId(id);
        //调用UserService实现修改User
        userService.update(user);
        return new ResponseData(true, Constant.OK, "修改成功");
    }

    /***
     * 新增User数据
     * @param user
     * @return
     */
    @PostMapping
    public ResponseData add(@RequestBody User user) {
        //调用UserService实现添加User
        userService.add(user);
        return new ResponseData(true, Constant.OK, "添加成功");
    }

    /***
     * 根据ID查询User数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseData<User> findById(@PathVariable Integer id) {
        //调用UserService实现根据主键查询User
        User user = userService.findById(id);
        return new ResponseData<User>(true, Constant.OK, "查询成功", user);
    }

    /***
     * 查询User全部数据
     * @return
     */
    @GetMapping
    public ResponseData<List<User>> findAll() {
        //调用UserService实现查询所有User
        List<User> list = userService.findAll();
        return new ResponseData<List<User>>(true, Constant.OK, "查询成功", list);
    }
}
