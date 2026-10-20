package com.kx.gen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.kx.common.security.annotation.EnableCustomConfig;
import com.kx.common.security.annotation.EnableRyFeignClients;
import com.kx.common.swagger.annotation.EnableCustomSwagger2;

/**
 * 代码生成
 *
 * @author kangxu
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
@MapperScan("com.kx.gen.mapper")
public class KangXuGenApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(KangXuGenApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  代码生成模块启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}
