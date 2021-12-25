/**
 * Copyright ©2021-2025 tanagelee Corporation, All Rights Reserved
 */
package xyz.tanagelee.tanscheduler.common.config;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.netty.channel.epoll.Epoll;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.config.TransportMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import java.util.List;

/**
 * RedissonConfig
 *
 * @author liyunjun
 * @date 2021/12/25 21:52
 */
@Configuration
@ConditionalOnProperty(prefix = "redisson", name = "enable", havingValue = "true",
        matchIfMissing = true)
public class RedissonConfig {

    @Value("${redis.type:}")
    private String type;

    @Value("${redis.host:}")
    private String host;

    @Value("${redis.port:}")
    private Integer port;

    @Value("${redis.nodes:}")
    private List<String> nodes;

    @Value("${redis.username:}")
    private String username;

    @Value("${redis.password:}")
    private String password;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        if (Epoll.isAvailable()) {
            config.setTransportMode(TransportMode.EPOLL);
        } else {
            config.setTransportMode(TransportMode.NIO);
        }
        fillConfig(config);
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }

    private void fillConfig(Config config) {
        if ("single".equals(type)) {
            Assert.hasText(host, "host配置为空");
            Assert.notNull(port, "port配置为空");
            SingleServerConfig singleServerConfig = config.useSingleServer();
            singleServerConfig.setAddress(String.format("redis://%s:%s", host, port));
            if (StringUtils.isNotBlank(username)) {
                singleServerConfig.setUsername(username);
            }
            if (StringUtils.isNotBlank(password)) {
                singleServerConfig.setPassword(password);
            }
        } else if ("cluster".equals(type)) {
            Assert.notEmpty(nodes, "nodes配置为空");
            ClusterServersConfig clusterServersConfig = config.useClusterServers();
            for (String node : nodes) {
                clusterServersConfig.addNodeAddress(String.format("redis://%s", node));
            }
            if (StringUtils.isNotBlank(username)) {
                clusterServersConfig.setUsername(username);
            }
            if (StringUtils.isNotBlank(password)) {
                clusterServersConfig.setPassword(password);
            }
            clusterServersConfig.setScanInterval(2000);
        } else {
            throw new RuntimeException("type配置错误");
        }
    }
}