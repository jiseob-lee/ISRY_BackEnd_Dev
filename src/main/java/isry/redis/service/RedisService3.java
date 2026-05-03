package isry.redis.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import isry.itgcms.sysmgmt.userauth.vo.UserInstAuthVO;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

@Component
public class RedisService3 {

	private static final Logger logger = LoggerFactory.getLogger(RedisService3.class);
	
	@Autowired
	StringRedisTemplate redisTemplate;

	public void insertRedisMap(String redisKey, Map<String, Object> map) {

		HashOperations<String, Object, Object> stringObjectObjectHashOperations = redisTemplate.opsForHash();
		
		//Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			//System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			
			stringObjectObjectHashOperations.put(redisKey, entry.getKey(), entry.getValue());
		}

		//stringObjectObjectHashOperations.put(redisKey, "Hello", "rg1");
		//stringObjectObjectHashOperations.put(redisKey, "Hello2", "rg2");
		//stringObjectObjectHashOperations.put(redisKey, "Hello3", "rg3");
		
	}

	public void processRedisLogout(String redisKey) {
		Set<String> keys = redisTemplate.keys(redisKey + "*");
		for (String key : keys) {
			redisTemplate.delete(key);
		}
	}
	
	public int selectRedisLikeSessionCount(String redisKey) {
		int count = 0;
		Set<String> keys = redisTemplate.keys(redisKey + "*");
		for (String key : keys) {
			count++;
		}
		return count;
	}
	
	public List<UserDetailsVO> selectPrevLoginVOList(String redisKey) {
		
		List<UserDetailsVO> list = new ArrayList<>();
		
		if (redisKey == null || redisKey.trim().equals("")) {
			return null;
		}
		
		Set<String> keys = redisTemplate.keys(redisKey + "*");
		for (String key : keys) {
			UserDetailsVO vo = selectRedisSession(key);
			list.add(vo);
		}
		
		return list;
	}

	public List<String> selectKeys(String redisKey) {
		
		List<String> list = new ArrayList<>();
		
		if (redisKey == null || redisKey.trim().equals("")) {
			return null;
		}
		
		Set<String> keys = redisTemplate.keys(redisKey + "*");
		for (String key : keys) {
			list.add(key);
		}
		
		return list;
	}
	
	public void deleteRedisLikeSession(String redisKey) {
		Set<String> keys = redisTemplate.keys(redisKey + "*");
		for (String key : keys) {
			redisTemplate.delete(key);
		}
	}
	
	public void setTimeOutSecond(String key, Integer sessionTime) {
		redisTemplate.expire(key, sessionTime, TimeUnit.SECONDS);
	}
	
	@SuppressWarnings("unchecked")
	public UserDetailsVO selectRedisSession(String key) {
		
		HashOperations<String, Object, Object> stringObjectObjectHashOperations = redisTemplate.opsForHash();
		Map<Object, Object> entries = stringObjectObjectHashOperations.entries(key);
		
		UserDetailsVO vo = new UserDetailsVO();
		vo.setAge(String.valueOf(entries.get("age")));
		vo.setAgencyContacts(String.valueOf(entries.get("agencyContacts")));
		vo.setAuthrtSeCd(String.valueOf(entries.get("authrtSeCd")));
		vo.setBirthdate(String.valueOf(entries.get("birthdate")));
		vo.setCertificate(String.valueOf(entries.get("certificate")));
		vo.setCtpvNm(String.valueOf(entries.get("ctpvNm")));
		vo.setDeptCd(String.valueOf(entries.get("deptCd")));
		vo.setDeptNm(String.valueOf(entries.get("deptNm")));
		vo.setEmail(String.valueOf(entries.get("email")));
		vo.setEnfsnNo(String.valueOf(entries.get("enfsnNo")));
		vo.setEnfsnRoleSeCd(String.valueOf(entries.get("enfsnRoleSeCd")));
		vo.setEngCtpvNm(String.valueOf(entries.get("engCtpvNm")));
		vo.setGender(String.valueOf(entries.get("gender")));
		vo.setGroupAuthrtSeCd(String.valueOf(entries.get("groupAuthrtSeCd")));
		vo.setId(String.valueOf(entries.get("id")));
		vo.setIndvIdntfcNo(String.valueOf(entries.get("indvIdntfcNo")));
		vo.setInstAuthList((List<UserInstAuthVO>)entries.get("instAuthList"));
		vo.setInstNm(String.valueOf(entries.get("instNm")));
		vo.setInstNo(Integer.parseInt(String.valueOf(entries.get("instNo"))));
		vo.setInstTypeSeCd(String.valueOf(entries.get("instTypeSeCd")));
		vo.setIp(String.valueOf(entries.get("ip")));
		vo.setLastLoginTime(String.valueOf(entries.get("lastLoginTime")));
		vo.setLgnScsYn(String.valueOf(entries.get("lgnScsYn")));
		vo.setManagerYn(String.valueOf(entries.get("managerYn")));
		vo.setMemberType(String.valueOf(entries.get("memberType")));
		vo.setMobile(String.valueOf(entries.get("mobile")));
		vo.setOrgCode(Integer.parseInt(String.valueOf(entries.get("orgCode"))));
		vo.setOrgName(String.valueOf(entries.get("orgName")));
		vo.setPass(String.valueOf(entries.get("pass")));
		vo.setRgnSeCd(String.valueOf(entries.get("rgnSeCd")));
		vo.setSessionId(String.valueOf(entries.get("sessionId")));
		vo.setSggCd(String.valueOf(entries.get("sggCd")));
		vo.setSggNm(String.valueOf(entries.get("sggNm")));
		vo.setSidoNm(String.valueOf(entries.get("sidoNm")));
		vo.setSigunguNm(String.valueOf(entries.get("sigunguNm")));
		vo.setTopMenuNo(String.valueOf(entries.get("topMenuNo")));
		vo.setUntTaskwk(String.valueOf(entries.get("untTaskwk")));
		vo.setUntTaskwkSeCd(String.valueOf(entries.get("untTaskwkSeCd")));
		vo.setUserInstNo(Integer.parseInt(String.valueOf(entries.get("userInstNo"))));
		vo.setUserName(String.valueOf(entries.get("userName")));
		vo.setWrdTelno(String.valueOf(entries.get("wrdTelno")));
		vo.setYngbgsPrtcrNo(String.valueOf(entries.get("yngbgsPrtcrNo")));
		
		return vo;
	}
	
	public Set<String> selectKey(String key) {
		Set<String> set = new HashSet<>();
		
		SetOperations<String, String> stringStringSetOperations = redisTemplate.opsForSet();

		Cursor<String> cursor = stringStringSetOperations.scan(key, ScanOptions.scanOptions().match("*").build());

		while (cursor.hasNext()) {
			//logger.debug("cursor = " + cursor.next());
			set.add(cursor.next());
		}
		
		return set;
	}
}
