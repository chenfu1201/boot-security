package top.chenfu.demo.config;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import top.chenfu.demo.service.UserService;
import top.chenfu.demo.vo.ResponseData;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * @Auther: kkz
 * @Date: 2020/1/15 14:44
 * @Desc: BlogSecurityConfigurerManager
 */
@SpringBootConfiguration
public class BlogSecurityConfigurerManager extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private BlogSecurityMetadataSource securityMetadataSource;

    @Autowired
    private BlogUriAccessDecisionManager uriAccessDecisionManager;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("123");
        System.out.println(encode);
    }

    /**
     * 认证
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    /**
     * 授权
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                自定义动态权限配置
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O filterSecurityInterceptor) {
                        filterSecurityInterceptor.setAccessDecisionManager(uriAccessDecisionManager);
                        filterSecurityInterceptor.setSecurityMetadataSource(securityMetadataSource);
                        return filterSecurityInterceptor;
                    }
                })
                .and().formLogin().loginProcessingUrl("/login").usernameParameter("username").passwordParameter("password")
                .successHandler((request, response, authentication) -> {
                    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    PrintWriter responseWriter = response.getWriter();
                    ResponseData defaultSuccess = ResponseData.defaultSuccess(authentication.getAuthorities());
                    responseWriter.write(JSON.toJSONString(defaultSuccess));
                    responseWriter.flush();
                    responseWriter.close();
                })
                .failureHandler((request, response, e) -> {
                    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    HashMap<String, Object> ResponseData = new HashMap<>();
                    if (e instanceof BadCredentialsException ||
                            e instanceof UsernameNotFoundException) {
                        ResponseData.put("msg", "账户名或者密码输入错误!");
                    } else if (e instanceof LockedException) {
                        ResponseData.put("msg", "账户被锁定，请联系管理员!");
                    } else if (e instanceof CredentialsExpiredException) {
                        ResponseData.put("msg", "密码过期，请联系管理员!");
                    } else if (e instanceof AccountExpiredException) {
                        ResponseData.put("msg", "账户过期，请联系管理员!");
                    } else if (e instanceof DisabledException) {
                        ResponseData.put("msg", "账户被禁用，请联系管理员!");
                    } else {
                        ResponseData.put("msg", "登录失败!");
                    }
                    response.setStatus(401);
                    PrintWriter out = response.getWriter();
                    out.write(JSON.toJSONString(ResponseData));
                    out.flush();
                    out.close();
                }).permitAll()
                .and().csrf().disable()
                .exceptionHandling().accessDeniedHandler((httpServletRequest, httpServletResponse, e) -> {
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            HashMap<String, Object> responseData = new HashMap<>();
            responseData.put("msg", "权限不足，请联系管理员");
            PrintWriter writer = httpServletResponse.getWriter();
            writer.write(JSON.toJSONString(responseData));
            writer.flush();
            writer.close();
        });
    }

}
