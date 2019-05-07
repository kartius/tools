package statemachine.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
public class BeansConfig {

    @Value("${redis.url}")
    private String redisUrl;
    @Value("${redis.port}")
    private String redisPort;
    @Value("${redis.expiration}")
    private String redisExpiration;

    public String getRedisUrl() {
        return redisUrl;
    }

    public void setRedisUrl(String redisUrl) {
        this.redisUrl = redisUrl;
    }

    public String getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(String redisPort) {
        this.redisPort = redisPort;
    }

    public String getRedisExpiration() {
        return redisExpiration;
    }

    public void setRedisExpiration(String redisExpiration) {
        this.redisExpiration = redisExpiration;
    }

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
        // Defaults
        redisConnectionFactory.setHostName(redisUrl);
        redisConnectionFactory.setPort(Integer.valueOf(redisPort));
        return redisConnectionFactory;
    }

    @Bean
    RedisRepository redisRepo() {
        return new RedisRepository(redisConnectionFactory());
    }

    @Bean
    RedisRuntimePersister redisRuntimePersister(){
        return new RedisRuntimePersister();
    }
}
