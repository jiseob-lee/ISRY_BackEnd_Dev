/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.redis.service;

/*
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import isry.itgcms.sysmgmt.userauth.vo.UnitSysAuthVO;
import isry.itgcms.sysmgmt.userauth.vo.UserInstAuthVO;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
*/
/**
 * &
 * 
 * @파일명 : RedisService.java
 * @프로그램 설명 : - Redis -
 * @작성자 : Song.Young.Il
 * @작성일 : 2022. 2. 15.
 * @수정자 : Song.Young.Il
 * @수정일 : 2022. 2. 15.
 * @수정내용 : - -
 */
//@Component("redisService")
public class RedisService {
	/*
	@Autowired
	public RedisTemplate<String, Object> redisDB;

	private Logger log = Logger.getLogger(this.getClass());

	public Set<String> selectKey(String Key) {

		if (Key == null || Key.trim().equals("")) {
			return null;
		}
		
		redisDB.setKeySerializer(new StringRedisSerializer());
		redisDB.setValueSerializer(new StringRedisSerializer());
		Set<String> rSet = (Set<String>) redisDB.keys(Key);
		return rSet;
	}
	

	// Redis Key에 해당하는 사이즈를 조회 한다.
	public Long getRedisSize(String Key) {

		if (Key == null || Key.trim().equals("")) {
			return null;
		}
		
		Long iSize ;  
		
		redisDB.setKeySerializer(new StringRedisSerializer());

		redisDB.setValueSerializer(new GenericJackson2JsonRedisSerializer());		
		
		iSize = redisDB.opsForList().size(Key) ; 
		
		return iSize;
	}

	
	// Map 기반으로 Redis 데이터를 저장한다.
	public int insertRedisMap(String Key, Map<String, Object> MapData) {

		if (Key == null || Key.trim().equals("")) {
			return 0;
		}
		
		int intTF = 0;

		redisDB.setKeySerializer(new StringRedisSerializer()); // String 타입
		redisDB.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		
		if (redisDB.hasKey(Key)) {
			log.info(this.getClass().getName() + "Key("+Key+")가 존재 합니다.");
			redisDB.delete(Key);
		}
		
		redisDB.opsForList().rightPush(Key, MapData);
		

		intTF = 1;

		return intTF;
	}

	
	public Map<String, Object> selectRedisMap(String Key) {

		if (Key == null || Key.trim().equals("")) {
			return null;
		}
		
		// Redis에서 가져온 결과 저장할 객체
		List rList = null;

		redisDB.setKeySerializer(new StringRedisSerializer());

		// 원본
		// redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(RedisVo.class));

		redisDB.setValueSerializer(new GenericJackson2JsonRedisSerializer());

		if (redisDB.hasKey(Key)) {

			// 저장된 전체 레코드 수
			rList = redisDB.opsForList().range(Key, 0, -1);
		}

		return rList == null || rList.size() == 0 ? null : (Map<String, Object>) rList.get(0);
	}
	
	public UserDetailsVO selectRedisSession(String Key) {

		if (Key == null || Key.trim().equals("")) {
			return null;
		}
		
		Map<String, Object> map = selectRedisMap(Key);
		
		if (map == null) {
			
			return null;
		
		} else {

			// 세션 시간 갱신하기
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
			HttpSession session = request.getSession();
			Integer sessionTime = session == null ? null : (Integer)session.getAttribute("sessionTime");
			if (sessionTime != null) {
				setTimeOutSecond(Key, sessionTime);
			}
			
			
			UserDetailsVO vo = new UserDetailsVO();
			
			vo.setId((String)map.get("id"));
			vo.setPass((String)map.get("pass"));
			vo.setUserName((String)map.get("userName"));
			
			vo.setOrgCode((Integer)map.get("orgCode"));
			vo.setOrgName((String)map.get("orgName"));
			vo.setEngCtpvNm((String)map.get("engCtpvNm"));
			vo.setCtpvNm((String)map.get("ctpvNm"));
			vo.setSggCd((String)map.get("sggCd"));
			vo.setSggNm((String)map.get("sggNm"));
			
			vo.setRgnSeCd((String)map.get("rgnSeCd"));
			
			vo.setIp((String)map.get("ip"));  // 접속 아이피
			
			vo.setBirthdate((String)map.get("birthdate"));  // 생년월일
			vo.setGender((String)map.get("gender"));  // 성별
			vo.setEmail((String)map.get("email"));  // 개인 이메일 주소
			vo.setMobile((String)map.get("mobile"));  // 개인 휴대폰 번호
			vo.setAge((String)map.get("age"));  // 나이
			vo.setMemberType((String)map.get("memberType"));  // 회원 종류 (종사자, 기관, 청소년, 학부모)
			vo.setAgencyContacts((String)map.get("agencyContacts"));  // 기관 연락처
			vo.setLastLoginTime((String)map.get("lastLoginTime"));  // 최근 로그인 일시
			vo.setUntTaskwkSeCd((String)map.get("untTaskwkSeCd"));  // 단위 업무 코드
			vo.setTopMenuNo((String)map.get("topMenuNo")); // 최상위 메뉴 번호
			vo.setCertificate((String)map.get("certificate"));  // 인증서 로그인 여부
			
			vo.setUntTaskwk((String)map.get("untTaskwk"));  // 현재 선택된 단위 시스템 코드
			
			vo.setLgnScsYn((String)map.get("lgnScsYn"));  // 로그인 성공 여부
			
			vo.setEnfsnNo((String)map.get("enfsnNo"));  // 종사자 번호
			vo.setEnfsnRoleSeCd((String)map.get("enfsnRoleSeCd"));  // 종사자의 역할구분코드
			
			vo.setUserInstNo((Integer)map.get("userInstNo"));  // 사용자 기관번호
			vo.setYngbgsPrtcrNo((String)map.get("yngbgsPrtcrNo"));  // 청소년보호자번호
			vo.setIndvIdntfcNo((String)map.get("indvIdntfcNo"));  // 개인식별번호
			
			vo.setInstTypeSeCd((String)map.get("instTypeSeCd"));  // 기관유형구분코드
			
			vo.setInstNo((Integer)map.get("instNo"));  //  기관 번호
			vo.setInstNm((String)map.get("instNm"));  //  기관명
			
			vo.setWrdTelno((String)map.get("wrdTelno"));  // 유선전화번호
			vo.setSidoNm((String)map.get("sidoNm"));  // 시도명
			vo.setSigunguNm((String)map.get("sigunguNm"));  // 시군구명
			
			vo.setDeptCd((String)map.get("deptCd"));  // 부서코드
			vo.setDeptNm((String)map.get("deptNm"));  // 부서명
			
			vo.setSessionId((String)map.get("sessionId"));
			
			vo.setManagerYn((String)map.get("managerYn"));
			
			vo.setGroupAuthrtSeCd((String) map.get("groupAuthrtSeCd"));	// 그룹권한구분코드
			
			vo.setAuthrtSeCd((String) map.get("authrtSeCd"));			// 권한구분코드
			
			vo.setInstAuthList((List<UserInstAuthVO>) map.get("instAuthList"));	// 기관 권한 목록
			
			return vo;
		}
	}

	public int insertRedisList(String Key, List<Map<String, Object>> listData) {

		if (Key == null || Key.trim().equals("")) {
			return 0;
		}
		
		int intTF = 0;

		redisDB.setKeySerializer(new StringRedisSerializer());

		redisDB.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		
		//기존 데이터가 있으면 삭제 처리 한다. 
		if (redisDB.hasKey(Key)) {
			
			log.info(this.getClass().getName() + "Key("+Key+")가 존재 합니다.");
			redisDB.delete(Key);
			
		}

		redisDB.opsForList().rightPush(Key, listData);

		intTF = 1;

		return intTF;
	}

	public List<Map<String, Object>> selectRedisList(String Key) {

		if (Key == null || Key.trim().equals("")) {
			return null;
		}
		
		// Redis에서 가져온 결과 저장할 객체
		List<Map<String, Object>> rList = null;

		redisDB.setKeySerializer(new StringRedisSerializer()); // String 타입

		redisDB.setValueSerializer(new GenericJackson2JsonRedisSerializer());

		if (redisDB.hasKey(Key)) {

			// 저장된 전체 레코드 수
			rList = (List) redisDB.opsForList().range(Key, 0, -1);

			return (List<Map<String, Object>>) (rList.get(0));

		} else {

			return rList;
		}
	}

	public List<Map<String, Object>> selectRedisListLike(String Key) {

		if (Key == null || Key.trim().equals("")) {
			return null;
		}
		
		// Redis에서 가져온 결과 저장할 객체
		List<Map<String, Object>> rList = new ArrayList<>();

		redisDB.setKeySerializer(new StringRedisSerializer()); // String 타입

		redisDB.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		
		Set<String> keys = redisDB.keys(Key);
		
		if (keys != null && keys.size() > 0) {
			
			Iterator<String> iter = keys.iterator();
			
			while (iter.hasNext()) {
				String keyStr = iter.next();
				log.debug("#### keyStr : " + keyStr);
				//redisDB.opsForList().rightPush("SESSION_LIST", keyStr);
				rList.add(selectRedisMap(keyStr));
			}
			
			// 저장된 전체 레코드 수
			//rList = (List) redisDB.opsForList().range("SESSION_LIST", 0, -1);

			return rList;

		} else {

			return rList;
		}
	}
	
	public List<UserDetailsVO> selectPrevLoginVOList(String key) {

		if (key == null || key.trim().equals("")) {
			return null;
		}
		
		// Redis에서 가져온 결과 저장할 객체
		List<UserDetailsVO> rList = new ArrayList<>();

		redisDB.setKeySerializer(new StringRedisSerializer()); // String 타입

		redisDB.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		
		Set<String> keys = redisDB.keys(key);
		
		if (keys != null && keys.size() > 0) {
			
			Iterator<String> iter = keys.iterator();
			
			while (iter.hasNext()) {
				String keyStr = iter.next();
				log.debug("#### keyStr : " + keyStr);
				//redisDB.opsForList().rightPush("SESSION_LIST", keyStr);
				rList.add(selectRedisSession(keyStr));
			}
			
			// 저장된 전체 레코드 수
			//rList = (List) redisDB.opsForList().range("SESSION_LIST", 0, -1);

			return rList;

		} else {

			return rList;
		}		
	}
	
	public void deleteRedisLikeSession(String Key) {

		if (Key == null || Key.trim().equals("")) {
			return;
		}
		
		redisDB.setKeySerializer(new StringRedisSerializer()); // String 타입

		redisDB.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		
		Set<String> keys = redisDB.keys(Key);
		
		if (keys != null && keys.size() > 0) {
			
			Iterator<String> iter = keys.iterator();
			
			while (iter.hasNext()) {
				String keyStr = iter.next();
				log.debug("#### keyStr : " + keyStr);
				redisDB.delete(keyStr);
			}
		}				
	}
	
	public int seleteRedisLikeSessionCount(String Key) {

		if (Key == null || Key.trim().equals("")) {
			return 0;
		}
		
		redisDB.setKeySerializer(new StringRedisSerializer()); // String 타입

		redisDB.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		
		Set<String> keys = redisDB.keys(Key);
		
		if (keys != null && keys.size() > 0) {
			return keys.size();
		} else {
			return 0;
		}
	}
	
	public void processRedisLogout(String Key) {

		if (Key == null || Key.trim().equals("")) {
			return;
		}
		
		redisDB.setKeySerializer(new StringRedisSerializer()); // String 타입

		redisDB.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		
		log.debug("#### keyStr : " + Key);

		redisDB.delete(Key);
	}
	
	public boolean setTimeOutHour(String roomKey, int hours) {

		if (roomKey == null || roomKey.trim().equals("")) {
			return false;
		}
		
		log.info(this.getClass().getName() + ".setTimeOutHour Start!");
		return redisDB.expire(roomKey, hours, TimeUnit.HOURS);
	}

	public boolean setTimeOutMinute(String roomKey, int minutes) {

		if (roomKey == null || roomKey.trim().equals("")) {
			return false;
		}
		
		log.info(this.getClass().getName() + ".setTimeOutMinute Start!");
		return redisDB.expire(roomKey, minutes, TimeUnit.MINUTES);
	}
	
	public boolean setTimeOutSecond(String roomKey, int seconds) {

		if (roomKey == null || roomKey.trim().equals("")) {
			return false;
		}
		
		log.info(this.getClass().getName() + ".setTimeOutSecond Start!");
		return redisDB.expire(roomKey, seconds, TimeUnit.SECONDS);
	}
	*/
}
