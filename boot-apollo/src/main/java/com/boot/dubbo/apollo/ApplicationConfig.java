package com.boot.dubbo.apollo;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.context.annotation.Configuration;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description :
 * @since : 2020-01-17 16:30
 */
@Slf4j
@Configuration
@EnableApolloConfig
public class ApplicationConfig {

    @ApolloConfig
    private Config config;

    @Value("${redis.hostAndPort:localhost}")
    private String host;

    @Value("${redis.password:1234}")
    private String password;

    @Resource
    private LoggingSystem loggingSystem;

    public String getHost() {
        return this.host;
    }

    public String getPassword() {
        return this.password;
    }

    @ApolloConfigChangeListener()
    private void someOnChange(ConfigChangeEvent changeEvent) {

        log.info("before update {} ", this.toString());
        if (changeEvent.isChanged("redis.hostAndPort")) {
            this.host = this.config.getProperty("redis.hostAndPort", "defaultValue");
        }
        if (changeEvent.isChanged("redis.password")) {
            this.password = this.config.getProperty("redis.password", "1234");
        }

        log.info("after update {} ", this.toString());
    }

    @Override
    public String toString() {
        return String.format("demo configuration--- host: %s  password: %s", this.host, this.password);
    }

}
