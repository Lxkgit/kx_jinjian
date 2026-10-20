package com.kx.system;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.kx.common.security.annotation.EnableCustomConfig;
import com.kx.common.security.annotation.EnableRyFeignClients;
import com.kx.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 系统模块
 *
 * @author kangxu
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
@MapperScan("com.kx.system.mapper")
@EnableScheduling
@EnableAsync
@EnableCaching   // 开启缓存支持
public class KangXuSystemApplication
{
    private static final Logger logger = LoggerFactory.getLogger(KangXuSystemApplication.class);
    public static void main(String[] args)
    {
        try {
            SpringApplication.run(KangXuSystemApplication.class, args);
            System.out.println("(♥◠‿◠)ﾉﾞ  系统模块启动成功   ლ(´ڡ`ლ)ﾞ  \n");
        }catch (Exception ex) {
            logger.error("KangXuSystemApplication run error:",ex);
        }
    }
}
