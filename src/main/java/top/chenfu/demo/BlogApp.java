package top.chenfu.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Auther: kkz
 * @Date: 2020/1/15 2020
 * @Desc: BlogApp
 */
@SpringBootApplication
@MapperScan(basePackages = "top.chenfu.demo.dao")
public class BlogApp {

    public static void main(String[] args) {
        SpringApplication.run(BlogApp.class, args);
    }

}
