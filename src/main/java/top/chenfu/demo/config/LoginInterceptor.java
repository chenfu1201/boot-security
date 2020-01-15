package top.chenfu.demo.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import top.chenfu.demo.util.Constant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("请求路径uri:{}", request.getRequestURI());
        String token = request.getHeader(Constant.TOKEN);
        if (StringUtils.isNotBlank(token) && Objects.isNull(request.getSession().getAttribute(token))) {
            log.info("未失败,需要先登录request:{}", JSON.toJSONString(request.getRequestURL()));
            return false;
        }
//        token不存在的情况可以认为是swagger2访问或者本地测试
        return true;
    }

}
