package top.chenfu.demo.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.chenfu.demo.util.Constant;
import top.chenfu.demo.model.UserRole;
import top.chenfu.demo.service.UserRoleService;
import top.chenfu.demo.vo.ResponseData;

import java.util.List;

/****
 * @Author: chenfu
 * @Description:
 * @Date $DATE
 *****/
@RestController
@RequestMapping("/userRole")
@CrossOrigin
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    /***
     * UserRole分页条件搜索实现
     * @param userRole
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}")
    public ResponseData<PageInfo> findPage(@RequestBody(required = false) UserRole userRole, @PathVariable int page, @PathVariable int size) {
        //调用UserRoleService实现分页条件查询UserRole
        PageInfo<UserRole> pageInfo = userRoleService.findPage(userRole, page, size);
        return new ResponseData(true, Constant.OK, "查询成功", pageInfo);
    }

    /***
     * UserRole分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}")
    public ResponseData<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        //调用UserRoleService实现分页查询UserRole
        PageInfo<UserRole> pageInfo = userRoleService.findPage(page, size);
        return new ResponseData<PageInfo>(true, Constant.OK, "查询成功", pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param userRole
     * @return
     */
    @PostMapping(value = "/search")
    public ResponseData<List<UserRole>> findList(@RequestBody(required = false) UserRole userRole) {
        //调用UserRoleService实现条件查询UserRole
        List<UserRole> list = userRoleService.findList(userRole);
        return new ResponseData<List<UserRole>>(true, Constant.OK, "查询成功", list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public ResponseData delete(@PathVariable Integer id) {
        //调用UserRoleService实现根据主键删除
        userRoleService.delete(id);
        return new ResponseData(true, Constant.OK, "删除成功");
    }

    /***
     * 修改UserRole数据
     * @param userRole
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    public ResponseData update(@RequestBody UserRole userRole, @PathVariable Integer id) {
        //设置主键值
        userRole.setId(id);
        //调用UserRoleService实现修改UserRole
        userRoleService.update(userRole);
        return new ResponseData(true, Constant.OK, "修改成功");
    }

    /***
     * 新增UserRole数据
     * @param userRole
     * @return
     */
    @PostMapping
    public ResponseData add(@RequestBody UserRole userRole) {
        //调用UserRoleService实现添加UserRole
        userRoleService.add(userRole);
        return new ResponseData(true, Constant.OK, "添加成功");
    }

    /***
     * 根据ID查询UserRole数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseData<UserRole> findById(@PathVariable Integer id) {
        //调用UserRoleService实现根据主键查询UserRole
        UserRole userRole = userRoleService.findById(id);
        return new ResponseData<UserRole>(true, Constant.OK, "查询成功", userRole);
    }

    /***
     * 查询UserRole全部数据
     * @return
     */
    @GetMapping
    public ResponseData<List<UserRole>> findAll() {
        //调用UserRoleService实现查询所有UserRole
        List<UserRole> list = userRoleService.findAll();
        return new ResponseData<List<UserRole>>(true, Constant.OK, "查询成功", list);
    }
}
