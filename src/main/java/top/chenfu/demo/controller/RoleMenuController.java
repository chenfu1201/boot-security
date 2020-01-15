package top.chenfu.demo.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.chenfu.demo.util.Constant;
import top.chenfu.demo.model.RoleMenu;
import top.chenfu.demo.service.RoleMenuService;
import top.chenfu.demo.vo.ResponseData;

import java.util.List;

/****
 * @Author: chenfu
 * @Description:
 * @Date $DATE
 *****/
@RestController
@RequestMapping("/roleMenu")
@CrossOrigin
public class RoleMenuController {

    @Autowired
    private RoleMenuService roleMenuService;

    /***
     * RoleMenu分页条件搜索实现
     * @param roleMenu
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}")
    public ResponseData<PageInfo> findPage(@RequestBody(required = false) RoleMenu roleMenu, @PathVariable int page, @PathVariable int size) {
        //调用RoleMenuService实现分页条件查询RoleMenu
        PageInfo<RoleMenu> pageInfo = roleMenuService.findPage(roleMenu, page, size);
        return new ResponseData(true, Constant.OK, "查询成功", pageInfo);
    }

    /***
     * RoleMenu分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}")
    public ResponseData<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        //调用RoleMenuService实现分页查询RoleMenu
        PageInfo<RoleMenu> pageInfo = roleMenuService.findPage(page, size);
        return new ResponseData<PageInfo>(true, Constant.OK, "查询成功", pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param roleMenu
     * @return
     */
    @PostMapping(value = "/search")
    public ResponseData<List<RoleMenu>> findList(@RequestBody(required = false) RoleMenu roleMenu) {
        //调用RoleMenuService实现条件查询RoleMenu
        List<RoleMenu> list = roleMenuService.findList(roleMenu);
        return new ResponseData<List<RoleMenu>>(true, Constant.OK, "查询成功", list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public ResponseData delete(@PathVariable Integer id) {
        //调用RoleMenuService实现根据主键删除
        roleMenuService.delete(id);
        return new ResponseData(true, Constant.OK, "删除成功");
    }

    /***
     * 修改RoleMenu数据
     * @param roleMenu
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    public ResponseData update(@RequestBody RoleMenu roleMenu, @PathVariable Integer id) {
        //设置主键值
        roleMenu.setId(id);
        //调用RoleMenuService实现修改RoleMenu
        roleMenuService.update(roleMenu);
        return new ResponseData(true, Constant.OK, "修改成功");
    }

    /***
     * 新增RoleMenu数据
     * @param roleMenu
     * @return
     */
    @PostMapping
    public ResponseData add(@RequestBody RoleMenu roleMenu) {
        //调用RoleMenuService实现添加RoleMenu
        roleMenuService.add(roleMenu);
        return new ResponseData(true, Constant.OK, "添加成功");
    }

    /***
     * 根据ID查询RoleMenu数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseData<RoleMenu> findById(@PathVariable Integer id) {
        //调用RoleMenuService实现根据主键查询RoleMenu
        RoleMenu roleMenu = roleMenuService.findById(id);
        return new ResponseData<RoleMenu>(true, Constant.OK, "查询成功", roleMenu);
    }

    /***
     * 查询RoleMenu全部数据
     * @return
     */
    @GetMapping
    public ResponseData<List<RoleMenu>> findAll() {
        //调用RoleMenuService实现查询所有RoleMenu
        List<RoleMenu> list = roleMenuService.findAll();
        return new ResponseData<List<RoleMenu>>(true, Constant.OK, "查询成功", list);
    }
}
