package tech.pcloud.nnts.core.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.pcloud.framework.utility.common.Cache;

@Configuration
public class CacheConfig {
    @Value("${nnts.cache.name}")
    private String cacheName;

    @Bean
    public Cache getCache(){
        return new Cache(cacheName);
    }
}
