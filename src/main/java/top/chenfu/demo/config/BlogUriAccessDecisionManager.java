package top.chenfu.demo.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import top.chenfu.demo.util.Constant;

import java.util.Collection;

/**
 * @Auther: kkz
 * @Date: 2020/1/15 14:07
 * @Desc: BlogUriAccessDecisionManager
 */
@Component
public class BlogUriAccessDecisionManager implements AccessDecisionManager {

    private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

    /**
     * 校验是否具备访问权限
     *
     * @param authentication 当前用户凭证
     * @param o
     * @param collection     访问当前资源需要的权限列表【在这里是角色名称，BlogSecurityMetadataSource处理得到的】
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute configAttribute : collection) {
            if (StringUtils.equalsIgnoreCase(Constant.ROLE_ANONTMOUS, configAttribute.getAttribute())) {
//                校验是否是匿名用户
                if (authentication instanceof AnonymousAuthenticationToken) {
                    throw new BadCredentialsException("匿名用户，请先登录！");
                } else {
//                    说明登录过了
                    return;
                }
            }
//            开始正式的权限验证阶段，取用户具备的角色列表和访问该资源的角色做匹配
            for (GrantedAuthority authority : authentication.getAuthorities()) {
//                匹配，通过！
//                if (StringUtils.equalsIgnoreCase(configAttribute.getAttribute(), authority.getAuthority())) {
                if (ANT_PATH_MATCHER.match(configAttribute.getAttribute(), authority.getAuthority())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足，请联系管理员");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
