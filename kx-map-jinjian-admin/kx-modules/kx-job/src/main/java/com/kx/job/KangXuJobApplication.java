package com.kx.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.kx.common.security.annotation.EnableCustomConfig;
import com.kx.common.security.annotation.EnableRyFeignClients;
import com.kx.common.swagger.annotation.EnableCustomSwagger2;

/**
 * 定时任务
 *
 * @author kangxu
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
public class KangXuJobApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(KangXuJobApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  定时任务模块启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}
