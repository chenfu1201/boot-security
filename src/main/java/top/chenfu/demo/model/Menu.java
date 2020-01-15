package top.chenfu.demo.model;

import javax.persistence.*;
import java.util.List;

@Table(name = "menu")
public class Menu {
    /**
     * 菜单ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 访问URI
     */
    private String uri;

    /**
     * 菜单名称
     */
    @Column(name = "menu_name")
    private String menuName;

    /**
     * 菜单等级-0，1，2分别对应三级菜单
     */
    @Column(name = "menu_level")
    private Boolean menuLevel;

    /**
     * 注释
     */
    private String comment;

    /**
     * 父目录
     */
    @Column(name = "parent_id")
    private String parentId;

    /**
     * 访问当前资源需要的权限列表【角色名称】
     */
    @Transient
    private List<Role> roles;
    /**
     * 获取菜单ID
     *
     * @return id - 菜单ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置菜单ID
     *
     * @param id 菜单ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取访问URI
     *
     * @return uri - 访问URI
     */
    public String getUri() {
        return uri;
    }

    /**
     * 设置访问URI
     *
     * @param uri 访问URI
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * 获取菜单名称
     *
     * @return menu_name - 菜单名称
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * 设置菜单名称
     *
     * @param menuName 菜单名称
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    /**
     * 获取菜单等级-0，1，2分别对应三级菜单
     *
     * @return menu_level - 菜单等级-0，1，2分别对应三级菜单
     */
    public Boolean getMenuLevel() {
        return menuLevel;
    }

    /**
     * 设置菜单等级-0，1，2分别对应三级菜单
     *
     * @param menuLevel 菜单等级-0，1，2分别对应三级菜单
     */
    public void setMenuLevel(Boolean menuLevel) {
        this.menuLevel = menuLevel;
    }

    /**
     * 获取注释
     *
     * @return comment - 注释
     */
    public String getComment() {
        return comment;
    }

    /**
     * 设置注释
     *
     * @param comment 注释
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * 获取父目录
     *
     * @return parent_id - 父目录
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 设置父目录
     *
     * @param parentId 父目录
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}