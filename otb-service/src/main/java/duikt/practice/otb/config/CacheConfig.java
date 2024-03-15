package duikt.practice.otb.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder key = new StringBuilder();
            key.append(target.getClass().getSimpleName());
            key.append(method.getName());
            for (Object param : params) {
                key.append(param.toString());
            }
            return key.toString();
        };
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("sorted_bus_tickets");
    }

}