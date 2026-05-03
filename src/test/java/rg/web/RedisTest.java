package rg.web;

/*
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import egovframework.com.cmm.ApplicationConfig;
import isry.redis.service.RedisConfig;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class, RedisConfig.class})
*/
public class RedisTest {
	
	/*
	private static final Logger logger = LoggerFactory.getLogger(RedisTest.class);
	
	@Autowired
	StringRedisTemplate redisTemplate;
	
	@Test
	public void testString() {
		final String key = "rg";
		
		final ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();
		
		stringStringValueOperations.set(key, "1");
		final String result_1 = stringStringValueOperations.get(key);
		
		logger.debug("result_1 = " + result_1);
		
		stringStringValueOperations.increment(key);
		final String result_2 = stringStringValueOperations.get(key);
		
		logger.debug("result_2 = " + result_2);
	}
	
	@Test
	public void testOut() {
		logger.debug("1234567890");
	}
	
	@Test
	public void testList() {
		final String key = "rg1";
		
		final ListOperations<String, String> stringStringListOperations = redisTemplate.opsForList();
		
		stringStringListOperations.rightPush(key, "H");
		stringStringListOperations.rightPush(key, "e");
		stringStringListOperations.rightPush(key, "l");
		stringStringListOperations.rightPush(key, "l");
		stringStringListOperations.rightPush(key, "o");
		
		stringStringListOperations.rightPushAll(key, " ", "r", "g");
		
		final String character_1 = stringStringListOperations.index(key, 1);
		
		logger.debug("character_1 = " + character_1);
		
		final Long size = stringStringListOperations.size(key);
		
		logger.debug("size = " + size);
		
		final List<String> ResultRange = stringStringListOperations.range(key, 0, 7);
		
		logger.debug("ResultRange = " + Arrays.toString(ResultRange.toArray()));
	}
	
	@Test
	public void testSet() {
		String key = "rg2";
		
		SetOperations<String, String> stringStringSetOperations = redisTemplate.opsForSet();
		
		stringStringSetOperations.add(key, "H");
		stringStringSetOperations.add(key, "e");
		stringStringSetOperations.add(key, "l");
		stringStringSetOperations.add(key, "l");
		stringStringSetOperations.add(key, "o");
		
		Set<String> rg2 = stringStringSetOperations.members(key);
		
		logger.debug("members = " + Arrays.toString(rg2.toArray()));
		
		Long size = stringStringSetOperations.size(key);
		
		logger.debug("size = " + size);
		
		Cursor<String> cursor = stringStringSetOperations.scan(key, ScanOptions.scanOptions().match("*l*").count(1).build());
		
		while (cursor.hasNext()) {
			logger.debug("cursor = " + cursor.next());
		}
	}
	
	@Test
	public void testSortedSet() {
		String key = "rg3";
		
		ZSetOperations<String, String> stringStringZSetOperations = redisTemplate.opsForZSet();
		
		stringStringZSetOperations.add(key, "H", 1);
		stringStringZSetOperations.add(key, "e", 5);
		stringStringZSetOperations.add(key, "l", 10);
		stringStringZSetOperations.add(key, "l", 15);
		stringStringZSetOperations.add(key, "o", 20);
		
		Set<String> range = stringStringZSetOperations.range(key, 0, 5);
		
		logger.debug("range = " + Arrays.toString(range.toArray()));
		
		Long size = stringStringZSetOperations.size(key);
		
		logger.debug("size = " + size);
		
		Set<String> scoreRange = stringStringZSetOperations.rangeByScore(key, 0, 13);
		
		// l 이 두번 입력되면서 score 가 10 에서 15 로 바뀜.
		
		logger.debug("scoreRange = " + Arrays.toString(scoreRange.toArray()));
	}
	
	@Test
	public void testHash() {
		String key = "rg4";
		
		HashOperations<String, Object, Object> stringObjectObjectHashOperations = redisTemplate.opsForHash();
		
		stringObjectObjectHashOperations.put(key, "Hello", "rg1");
		stringObjectObjectHashOperations.put(key, "Hello2", "rg2");
		stringObjectObjectHashOperations.put(key, "Hello3", "rg3");
		
		Object hello = stringObjectObjectHashOperations.get(key, "Hello");
		
		logger.debug("hello = " + hello);
		
		Map<Object, Object> entries = stringObjectObjectHashOperations.entries(key);
		
		logger.debug("entries = " + entries.get("Hello2"));
		
		Long size = stringObjectObjectHashOperations.size(key);
		
		logger.debug("size = " + size);
	}
	*/
}
