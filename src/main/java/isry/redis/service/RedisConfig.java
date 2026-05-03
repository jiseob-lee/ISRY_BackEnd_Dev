package isry.redis.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

//@Configuration
//@EnableRedisRepositories
public class RedisConfig {
	
	private String redisHost = "127.0.0.1";
	
	private int redisPort = 6379;
	
	//private String redisPass = "";
	
	//@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory(redisHost, redisPort);
	}
	
	//@Bean
	public StringRedisTemplate redisTemplate() {
		//RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
		StringRedisTemplate redisTemplate = new StringRedisTemplate();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		return redisTemplate;
	}
}
