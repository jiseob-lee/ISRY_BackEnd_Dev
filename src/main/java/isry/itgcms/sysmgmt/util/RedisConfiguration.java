/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.util;

//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.context.annotation.ComponentScan;

/**
 * @파일명        : RedisConfiguration.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2022. 12. 29. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2022. 12. 29.
 * @수정내용      : 
 * -                
 * -                
 */
//@Configuration
//@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800)
//@EnableRedisHttpSession
//@EnableJpaRepositories("isry.itgcms.sysmgmt.util")
//@SpringBootApplication
//@EnableAutoConfiguration
//@EnableJpaRepositories("isry.itgcms.sysmgmt.util")
//@EntityScan("isry.itgcms")
//@ComponentScan(basePackages = {"isry"})
public class RedisConfiguration {
	
	//@Bean
	//public RedisConnectionFactory redisConnectionFactory() {
		//RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		//return new LettuceConnectionFactory();
	//}
	
	//@Bean
	public CookieSerializer cookieSerializer() {
		DefaultCookieSerializer serializer = new DefaultCookieSerializer();
		serializer.setCookieName("JSESSIONID");
		serializer.setCookiePath("/");
		serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");
		return serializer;
	}
	
}
