package top.chenfu.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import top.chenfu.demo.model.Menu;
import top.chenfu.demo.model.Role;
import top.chenfu.demo.service.MenuService;
import top.chenfu.demo.util.Constant;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Component
public class BlogSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private MenuService menuService;

    private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

    /**
     * @param invocation 过滤器
     * @return 访问该资源需要具备的权限【角色名称】
     * @throws IllegalArgumentException 参数不合法
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object invocation) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) invocation).getRequestUrl();
        List<Menu> allMenu = menuService.findAll();
        for (Menu menu : allMenu) {
            if (ANT_PATH_MATCHER.match(menu.getUri(), requestUrl)) {
                String[] roleNames = menu.getRoles().stream()
                        .filter(Objects::nonNull).map(Role::getRoleName).distinct().toArray(String[]::new);
                return SecurityConfig.createList(roleNames);
            }
        }
        /**
         * 该请求在库中没有配置权限，设置为匿名访问，在之后的权限匹配极端做处理
         */
        return SecurityConfig.createList(Constant.ROLE_ANONTMOUS);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }

}
