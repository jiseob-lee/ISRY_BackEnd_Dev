/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.cmmn.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.EgovHttpRequestHelper;
import isry.itgcms.util.ScpDb;
import isry.itgcms.util.StringUtil;
import isry.redis.service.RedisService;
import lombok.experimental.UtilityClass;

/**
 * @파일명        : CounsUtils.java
 * @프로그램 설명 : 청소년상담 공통 Utility 클래스
 * - 
 * - 
 * @작성자        : Myeong.Jae.Cheol
 * @작성일        : 2022. 12. 30. 
 * @수정자        : Myeong.Jae.Cheol
 * @수정일        : 2022. 12. 30.
 * @수정내용      : 
 * -                
 * -                
 */
@UtilityClass
public final class CounsUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CounsUtils.class);

	//@Autowired
	//private RedisService3 redisService;

	/**
	 * DB 암복호화 모듈
	 */
	private static final ScpDb scpDb = new ScpDb();
	
	/**
	 * @Method명   : getUserSession
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 30. 
	 * @Method설명 : HttpSession 에서 로그인 VO 조회
	 */
	//public static UserDetailsVO getUserSession() throws Exception {
		//HttpSession session = EgovHttpRequestHelper.getCurrentSession();
    	//UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
    	//if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
    		//return loginVO;
		//} else {
			//throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		//}
    //}
	
	/**
	 * @Method명   : encodeColumns
	 * @param orgs		원본 데이터 목록
	 * @param keys		원본 데이터 Key 목록
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 30. 
	 * @Method설명 : 평문 Text 칼럼 (Column) 암호화 처리
	 */
	public static void encodeColumns(List<Map<String, Object>> orgs, String... keys) {
		LOGGER.info("encodeColumns :: 암호화 시작 !!!");
		if (orgs != null && ObjectUtils.isArray(keys)) {
			orgs.forEach(map -> {
				// 암호화 처리
				CounsUtils.encodeColumns(map, keys);
			});
		}
		LOGGER.info("encodeColumns :: 암호화 종료 !!!");
	}
	
	/**
	 * @Method명   : encodeColumns
	 * @param map		원본 데이터 맵
	 * @param keys		원본 데이터 Key 목록
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 1. 2. 
	 * @Method설명 : 평문 Text 칼럼 (Column) 암호화 처리
	 */
	public static void encodeColumns(Map<String, Object> map, String... keys) {
		if (map != null && ObjectUtils.isArray(keys)) {
			// Key 목록 List 형 객체로 변환
			List<String> keyList = Arrays.asList(keys);
			
			// 암호화 처리
			map.forEach((key, value) -> {
				String plainText = "";	// 평문 Text (원본)
				String encodeStr = "";
				
				if (keyList.contains(key)) {
					plainText = StringUtil.nullConvert(String.valueOf(value));
					encodeStr = scpDb.scpEncB64(plainText);
					LOGGER.debug("{} : {}", key, encodeStr);
					map.replace(key, encodeStr);
				}
			});
		}
	}
	
	/**
	 * @Method명   : decodeColumns
	 * @param orgs		원본 데이터 목록
	 * @param keys		원본 데이터 Key 목록
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 30. 
	 * @Method설명 : 암호화된 칼럼 (Column) 복호화 처리
	 */
	public static void decodeColumns(List<Map<String, Object>> orgs, String... keys) {
		LOGGER.info("decodeColumns :: 복호화 시작 !!!");
		if (orgs != null && ObjectUtils.isArray(keys)) {
			orgs.forEach(map -> {
				// 복호화 처리
				CounsUtils.decodeColumns(map, keys);
			});
		}
		LOGGER.info("decodeColumns :: 복호화 종료 !!!");
	}
	
	/**
	 * @Method명   : decodeColumns
	 * @param map		원본 데이터 맵
	 * @param keys		원본 데이터 Key 목록
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 1. 2. 
	 * @Method설명 : 암호화된 칼럼 (Column) 복호화 처리
	 */
	public static void decodeColumns(Map<String, Object> map, String... keys) {
		//LOGGER.info("decodeColumns :: 복호화 시작 !!!");
		if (map != null && ObjectUtils.isArray(keys)) {
			// Key 목록 List 형 객체로 변환
			List<String> keyList = Arrays.asList(keys);
			
			// 복호화 처리
			map.forEach((key, value) -> {
				String encodeStr = "";
				String decodeStr = "";
				
				if (keyList.contains(key)) {
					encodeStr = StringUtil.nullConvert(String.valueOf(value));
					decodeStr = scpDb.scpDecB64(encodeStr);
					LOGGER.debug("{} : {}", key, decodeStr);
					map.replace(key, decodeStr);
				}
			});
		}
		//LOGGER.info("decodeColumns :: 복호화 종료 !!!");
	}
}
