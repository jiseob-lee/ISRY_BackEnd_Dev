/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.stats.dscsnstats.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseServiceImpl;
import isry.couns.stats.dscsnstats.mapper.ConQuestionStatsMapper;
import isry.couns.stats.dscsnstats.service.ConQuestionStatsService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;



@Service("conQuestionStatsService")
public class ConQuestionStatsServiceimpl extends IsryBaseServiceImpl implements ConQuestionStatsService   {

	@Resource(name = "conQuestionStatsMapper")
	private ConQuestionStatsMapper conQuestionStatsMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);


	/**
	 * @Method명   : selectType1Comb
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectType1Comb(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectType1Comb(mapParam);
	}


	/**
	 * @Method명   : selectType2Comb
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectType2Comb(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectType2Comb(mapParam);
	}


	/**
	 * @Method명   : selectconQuestionStats
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectconQuestionStats(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectconQuestionStats(mapParam);
	}


	/**
	 * @Method명   : selectconQuestionStats1
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectconQuestionStats1(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectconQuestionStats1(mapParam);
	}


	/**
	 * @Method명   : selectconQuestionStats2
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectconQuestionStats2(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectconQuestionStats2(mapParam);
	}


	/**
	 * @Method명   : selectconQuestionStats3
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectconQuestionStats3(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectconQuestionStats3(mapParam);
	}


	/**
	 * @Method명   : selectconQuestionStats4
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectconQuestionStats4(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectconQuestionStats4(mapParam);
	}


	/**
	 * @Method명   : selectconQuestionStats5
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectconQuestionStats5(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectconQuestionStats5(mapParam);
	}


	/**
	 * @Method명   : selectconQuestionStats6
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectconQuestionStats6(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectconQuestionStats6(mapParam);
	}


	/**
	 * @Method명   : selectconQuestionStats7
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectconQuestionStats7(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectconQuestionStats7(mapParam);
	}


	/**
	 * @Method명   : selectconQuestionStats8
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectconQuestionStats8(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectconQuestionStats8(mapParam);
	}


	/**
	 * @Method명   : selectconQuestionStatsChatWaitAvg
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectconQuestionStatsChatWaitAvg(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectconQuestionStatsChatWaitAvg(mapParam);
	}


	/**
	 * @Method명   : selectconQuestionStatsChatWait
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectconQuestionStatsChatWait(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectconQuestionStatsChatWait(mapParam);
	}
	
	/**
	 * @Method명   : selectSrvyExmnStatsList1
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 12. 
	 * @Method설명 : 설문조사 통계 목록 조회_접근경로
	 */
	@Override
	public List<Map<String, Object>> selectSrvyExmnStatsList1(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectSrvyExmnStatsList1(mapParam);
	}
	
	/**
	 * @Method명   : selectSrvyExmnStatsList2
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 12. 
	 * @Method설명 : 설문조사 통계 목록 조회_지역
	 */
	@Override
	public List<Map<String, Object>> selectSrvyExmnStatsList2(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectSrvyExmnStatsList2(mapParam);
	}
	
	/**
	 * @Method명   : selectSrvyExmnStatsList3
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 12. 
	 * @Method설명 : 설문조사 통계 목록 조회_서비스개선
	 */
	@Override
	public List<Map<String, Object>> selectSrvyExmnStatsList3(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectSrvyExmnStatsList3(mapParam);
	}
	
	/**
	 * @Method명   : selectSrvyExmnStatsList4
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 12. 
	 * @Method설명 : 설문조사 통계 목록 조회_성별
	 */
	@Override
	public List<Map<String, Object>> selectSrvyExmnStatsList4(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectSrvyExmnStatsList4(mapParam);
	}
	
	/**
	 * @Method명   : selectSrvyExmnStatsList5
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 12. 
	 * @Method설명 : 설문조사 통계 목록 조회_대상
	 */
	@Override
	public List<Map<String, Object>> selectSrvyExmnStatsList5(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectSrvyExmnStatsList5(mapParam);
	}
	
	/**
	 * @Method명   : selectCnctDscsnStatsList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 12. 
	 * @Method설명 : 이음-e 상담통계 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectCnctDscsnStatsList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectCnctDscsnStatsList(mapParam);
	}
	
	/**
	 * @Method명   : selectConsttSrvyStatsList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 11. 03. 
	 * @Method설명 : 상담자별 설문통계 조회
	 */
	@Override
	public List<Map<String, Object>> selectConsttSrvyStatsList(Map<String, Object> mapParam, HttpServletRequest request) throws Exception {
		
		String sEnfsnRoleSeCd = ""; // 종사자의 역할구분코드		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sEnfsnRoleSeCd = loginVO.getEnfsnRoleSeCd();
		} 
		//System.out.println("종사자의 역할구분코드 :::::::::::::: "+ sEnfsnRoleSeCd);
		//System.out.println("mapParam :::::::::::::: "+ mapParam.toString());
		
		if("3".equals(sEnfsnRoleSeCd)) { // 3:종사자
			return conQuestionStatsMapper.selectConsttSrvyStatsList1(mapParam);
		}else {			
			return conQuestionStatsMapper.selectConsttSrvyStatsList(mapParam);
		}		
				
	}

	/**
	 * @Method명   : selectCnctDgstfnStatsList
	 * @param 	   : mapParam
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 7. 
	 * @Method설명 : 이음게시판카테고리구분코드에 따른 이음-e 만족도 통계 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectCnctDgstfnStatsList(Map<String, String> mapParam) throws Exception {
		
		return conQuestionStatsMapper.selectCnctDgstfnStatsList(mapParam);
	}

	/**
	 * @Method명   : selectCnctDgstfnStatsList1
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 12. 
	 * @Method설명 : 이음-e 만족도 통계 목록 조회_자녀와 함께 성장하는 부모
	 */
	@Override
	public List<Map<String, Object>> selectCnctDgstfnStatsList1(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectCnctDgstfnStatsList1(mapParam);
	}

	/**
	 * @Method명   : selectCnctDgstfnStatsList2
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 12. 
	 * @Method설명 : 이음-e 만족도 통계 목록 조회_학교폭력예방
	 */
	@Override
	public List<Map<String, Object>> selectCnctDgstfnStatsList2(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectCnctDgstfnStatsList2(mapParam);
	}
	
	/**
	 * @Method명   : selectCnctDgstfnStatsList3
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 12. 
	 * @Method설명 : 이음-e 만족도 통계 목록 조회_다문화가족
	 */
	@Override
	public List<Map<String, Object>> selectCnctDgstfnStatsList3(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectCnctDgstfnStatsList3(mapParam);
	}
	
	/**
	 * @Method명   : selectCnctDgstfnStatsList4
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 12. 
	 * @Method설명 : 이음-e 만족도 통계 목록 조회_이혼가정 부모교육
	 */
	@Override
	public List<Map<String, Object>> selectCnctDgstfnStatsList4(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectCnctDgstfnStatsList4(mapParam);
	}
	
	/**
	 * @Method명   : selectCnctChngList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 12. 
	 * @Method설명 : 이음-e 변화도 조회
	 */
	@Override
	public List<Map<String, Object>> selectCnctChngList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectCnctChngList(mapParam);
	}
	
	/**
	 * @Method명   : selectNtabrdDscsnStatsList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 11. 01. 
	 * @Method설명 : 게시판상담통계 조회_상담자글
	 */
	@Override
	public List<Map<String, Object>> selectNtabrdDscsnStatsList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectNtabrdDscsnStatsList(mapParam);
	}
	
	/**
	 * @Method명   : selectNtabrdDscsnStatsList2
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 11. 01. 
	 * @Method설명 : 게시판상담통계 조회_내담자글
	 */
	@Override
	public List<Map<String, Object>> selectNtabrdDscsnStatsList2(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectNtabrdDscsnStatsList2(mapParam);
	}
	
	/**
	 * @Method명   : selectSlrbyDscsnStatsList1
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 12. 
	 * @Method설명 : 솔로봇 상담 통계 조회_채팅상담(성별)
	 */
	@Override
	public List<Map<String, Object>> selectSlrbyDscsnStatsList1(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectSlrbyDscsnStatsList1(mapParam);
	}

	/**
	 * @Method명   : selectSlrbyDscsnStatsList2
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 12. 
	 * @Method설명 : 솔로봇 상담 통계 조회_채팅상담(청소년상태)
	 */
	@Override
	public List<Map<String, Object>> selectSlrbyDscsnStatsList2(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectSlrbyDscsnStatsList2(mapParam);
	}
	
	/**
	 * @Method명   : selectSlrbyDscsnStatsList3
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 12. 
	 * @Method설명 : 솔로봇 상담 통계 조회_채팅상담(나이:실시건수)
	 */
	@Override
	public List<Map<String, Object>> selectSlrbyDscsnStatsList3(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectSlrbyDscsnStatsList3(mapParam);
	}
	
	/**
	 * @Method명   : selectSlrbyDscsnStatsList4
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 12. 
	 * @Method설명 : 솔로봇 상담 통계 조회_채팅상담(나이:게시판건수)
	 */
	@Override
	public List<Map<String, Object>> selectSlrbyDscsnStatsList4(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectSlrbyDscsnStatsList4(mapParam);
	}

	/**
	 * @Method명   : selectSlrbyDscsnStatsList5
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 12. 
	 * @Method설명 : 솔로봇 상담 통계 조회_미디어상담
	 */
	@Override
	public List<Map<String, Object>> selectSlrbyDscsnStatsList5(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectSlrbyDscsnStatsList5(mapParam);
	}
	
	/**
	 * @Method명   : selectSlrbyDgstfnStatsNewList1
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 12. 
	 * @Method설명 : 솔로봇 만족도 통계(신 2109~) 조회_게임상담
	 */
	@Override
	public List<Map<String, Object>> selectSlrbyDgstfnStatsNewList1(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectSlrbyDgstfnStatsNewList1(mapParam);
	}
	
	/**
	 * @Method명   : selectSlrbyDgstfnStatsNewList2
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 12. 
	 * @Method설명 : 솔로봇 만족도 통계(신 2109~) 조회_영상상담
	 */
	@Override
	public List<Map<String, Object>> selectSlrbyDgstfnStatsNewList2(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectSlrbyDgstfnStatsNewList2(mapParam);
	}
	
	/**
	 * @Method명   : selectGriefSltnAlkiolDgstfnStatsList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 12. 
	 * @Method설명 : 고민해결백과 만족도 통계 조회
	 */
	@Override
	public List<Map<String, Object>> selectGriefSltnAlkiolDgstfnStatsList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectGriefSltnAlkiolDgstfnStatsList(mapParam);
	}
	
	/**
	 * @Method명   : selectWeposInspStatsList1
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 13. 
	 * @Method설명 : 웹심리검사 통계 조회_검사결과현황
	 */
	@Override
	public List<Map<String, Object>> selectWeposInspStatsList1(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectWeposInspStatsList1(mapParam);
	}
	
	/**
	 * @Method명   : selectWeposInspStatsList2
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 13. 
	 * @Method설명 : 웹심리검사 통계 조회_검사결과
	 */
	@Override
	public List<Map<String, Object>> selectWeposInspStatsList2(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectWeposInspStatsList2(mapParam);
	}
	
	/**
	 * @Method명   : selectLinkPrfmncStatsList1
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 13. 
	 * @Method설명 : 연계실적 통계 조회_연계기관별실적
	 */
	@Override
	public List<Map<String, Object>> selectLinkPrfmncStatsList1(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectLinkPrfmncStatsList1(mapParam);
	}
	
	/**
	 * @Method명   : selectLinkPrfmncStatsList2
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 13. 
	 * @Method설명 : 연계실적 통계 조회_연계방법별실적
	 */
	@Override
	public List<Map<String, Object>> selectLinkPrfmncStatsList2(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectLinkPrfmncStatsList2(mapParam);
	}
	
	/**
	 * @Method명   : selectLinkPrfmncStatsList3
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 13. 
	 * @Method설명 : 연계실적 통계 조회_기타전문기관보기
	 */
	@Override
	public List<Map<String, Object>> selectLinkPrfmncStatsList3(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectLinkPrfmncStatsList3(mapParam);
	}
	
	/**
	 * @Method명   : selectOutrcStatsList1
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 13. 
	 * @Method설명 : 아웃리치 통계 조회_방법별 실적
	 */
	@Override
	public List<Map<String, Object>> selectOutrcStatsList1(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectOutrcStatsList1(mapParam);
	}
	
	/**
	 * @Method명   : selectOutrcStatsList2
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 13. 
	 * @Method설명 : 아웃리치 통계 조회_메신저상담 영역별 실적
	 */
	@Override
	public List<Map<String, Object>> selectOutrcStatsList2(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectOutrcStatsList2(mapParam);
	}
	
	/**
	 * @Method명   : selectOutrcStatsList3
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 13. 
	 * @Method설명 : 아웃리치 통계 조회_댓글상담 실적
	 */
	@Override
	public List<Map<String, Object>> selectOutrcStatsList3(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectOutrcStatsList3(mapParam);
	}
	
	/**
	 * @Method명   : selectOutrcStatsList4
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 13. 
	 * @Method설명 : 아웃리치 통계 조회_홍보 영역별 실적
	 */
	@Override
	public List<Map<String, Object>> selectOutrcStatsList4(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectOutrcStatsList4(mapParam);
	}
	
	/**
	 * @Method명   : selectOutrcStatsList5
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 13. 
	 * @Method설명 : 아웃리치 통계 조회_상담사별 실적
	 */
	@Override
	public List<Map<String, Object>> selectOutrcStatsList5(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectOutrcStatsList5(mapParam);
	}
	
	/**
	 * @Method명   : selectMblaLinkPrfmncStatsList1
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 13. 
	 * @Method설명 : 모바일 연계실적 통계 조회_연계기관별실적
	 */
	@Override
	public List<Map<String, Object>> selectMblaLinkPrfmncStatsList1(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectMblaLinkPrfmncStatsList1(mapParam);
	}
	
	/**
	 * @Method명   : selectMblaLinkPrfmncStatsListCHTT1
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 26. 
	 * @Method설명 : 모바일 연계실적 통계 조회_연계기관별실적_채팅
	 */
	@Override
	public List<Map<String, Object>> selectMblaLinkPrfmncStatsListCHTT1(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectMblaLinkPrfmncStatsListCHTT1(mapParam);
	}
	
	/**
	 * @Method명   : selectMblaLinkPrfmncStatsListMBLA1
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 26. 
	 * @Method설명 : 모바일 연계실적 통계 조회_연계기관별실적_모바일
	 */
	@Override
	public List<Map<String, Object>> selectMblaLinkPrfmncStatsListMBLA1(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectMblaLinkPrfmncStatsListMBLA1(mapParam);
	}
	
	/**
	 * @Method명   : selectMblaLinkPrfmncStatsList2
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 13. 
	 * @Method설명 : 모바일 연계실적 통계 조회_연계방법별실적
	 */
	@Override
	public List<Map<String, Object>> selectMblaLinkPrfmncStatsList2(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectMblaLinkPrfmncStatsList2(mapParam);
	}
	
	/**
	 * @Method명   : selectMblaLinkPrfmncStatsListCHTT2
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 26. 
	 * @Method설명 : 모바일 연계실적 통계 조회_연계방법별실적_채팅
	 */
	@Override
	public List<Map<String, Object>> selectMblaLinkPrfmncStatsListCHTT2(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectMblaLinkPrfmncStatsListCHTT2(mapParam);
	}
	
	/**
	 * @Method명   : selectMblaLinkPrfmncStatsListMBLA2
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 26. 
	 * @Method설명 : 모바일 연계실적 통계 조회_연계방법별실적_모바일
	 */
	@Override
	public List<Map<String, Object>> selectMblaLinkPrfmncStatsListMBLA2(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectMblaLinkPrfmncStatsListMBLA2(mapParam);
	}
	
	/**
	 * @Method명   : selectMblaLinkPrfmncStatsList3
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 13. 
	 * @Method설명 : 모바일 연계실적 통계 조회_기타전문기관보기
	 */
	@Override
	public List<Map<String, Object>> selectMblaLinkPrfmncStatsList3(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectMblaLinkPrfmncStatsList3(mapParam);
	}
	
	/**
	 * @Method명   : selectMblaLinkPrfmncStatsListCHTT3
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 26. 
	 * @Method설명 : 모바일 연계실적 통계 조회_기타전문기관보기_채팅
	 */
	@Override
	public List<Map<String, Object>> selectMblaLinkPrfmncStatsListCHTT3(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectMblaLinkPrfmncStatsListCHTT3(mapParam);
	}
	
	/**
	 * @Method명   : selectMblaLinkPrfmncStatsListMBLA3
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 26. 
	 * @Method설명 : 모바일 연계실적 통계 조회_기타전문기관보기_모바일
	 */
	@Override
	public List<Map<String, Object>> selectMblaLinkPrfmncStatsListMBLA3(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectMblaLinkPrfmncStatsListMBLA3(mapParam);
	}
	
	/**
	 * @Method명   : selectWeposInspStatsList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 18. 
	 * @Method설명 : 웹심리검사 통계 조회
	 */
	@Override
	public List<Map<String, Object>> selectWeposInspStatsList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectWeposInspStatsList(mapParam);
	}
	
	/**
	 * @Method명   : selectWeposInspStatsListSe2
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 19. 
	 * @Method설명 : 웹심리검사 통계 조회
	 */
	@Override
	public List<Map<String, Object>> selectWeposInspStatsListSe2(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectWeposInspStatsListSe2(mapParam);
	}
	
	/**
	 * @Method명   : selectWeposInspStatsList05
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 19. 
	 * @Method설명 : 웹심리검사 통계 조회05
	 */
	@Override
	public List<Map<String, Object>> selectWeposInspStatsList05(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectWeposInspStatsList05(mapParam);
	}
	
	/**
	 * @Method명   : selectWeposInspStatsList09
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 19. 
	 * @Method설명 : 웹심리검사 통계 조회09
	 */
	@Override
	public List<Map<String, Object>> selectWeposInspStatsList09(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectWeposInspStatsList09(mapParam);
	}
	
	/**
	 * @Method명   : selectWeposInspStatsList11
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 19. 
	 * @Method설명 : 웹심리검사 통계 조회11
	 */
	@Override
	public List<Map<String, Object>> selectWeposInspStatsList11(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectWeposInspStatsList11(mapParam);
	}
	
	/**
	 * @Method명   : selectWeposInspStatsList17
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 19. 
	 * @Method설명 : 웹심리검사 통계 조회17
	 */
	@Override
	public List<Map<String, Object>> selectWeposInspStatsList17(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectWeposInspStatsList17(mapParam);
	}
	
	/**
	 * @Method명   : selectWeposInspStatsList07
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 19. 
	 * @Method설명 : 웹심리검사 통계 조회07
	 */
	@Override
	public List<Map<String, Object>> selectWeposInspStatsList07(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectWeposInspStatsList07(mapParam);
	}
	
	/**
	 * @Method명   : selectWeposInspStatsListKK
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 19. 
	 * @Method설명 : 웹심리검사 통계 조회KK
	 */
	@Override
	public List<Map<String, Object>> selectWeposInspStatsListKK(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectWeposInspStatsListKK(mapParam);
	}
	
	/**
	 * @Method명   : selectWeposInspStatsListSS
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 19. 
	 * @Method설명 : 웹심리검사 통계 조회KK
	 */
	@Override
	public List<Map<String, Object>> selectWeposInspStatsListSS(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectWeposInspStatsListSS(mapParam);
	}
	
	/**
	 * @Method명   : selectWeposInspStatsListKP
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 19. 
	 * @Method설명 : 웹심리검사 통계 조회KP
	 */
	@Override
	public List<Map<String, Object>> selectWeposInspStatsListKP(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectWeposInspStatsListKP(mapParam);
	}
	
	/**
	 * @Method명   : selectWeposInspStatsListCAGI
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 19. 
	 * @Method설명 : 웹심리검사 통계 조회CAGI
	 */
	@Override
	public List<Map<String, Object>> selectWeposInspStatsListCAGI(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectWeposInspStatsListCAGI(mapParam);
	}
	
	/**
	 * @Method명   : selectWeposInspStatsList01
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 19. 
	 * @Method설명 : 웹심리검사 통계 조회01
	 */
	@Override
	public List<Map<String, Object>> selectWeposInspStatsList01(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectWeposInspStatsList01(mapParam);
	}
	
	/**
	 * @Method명   : selectWeposInspStatsList02
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 19. 
	 * @Method설명 : 웹심리검사 통계 조회02
	 */
	@Override
	public List<Map<String, Object>> selectWeposInspStatsList02(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectWeposInspStatsList02(mapParam);
	}
	
	/**
	 * @Method명   : selectWeposInspStatsList03
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 19. 
	 * @Method설명 : 웹심리검사 통계 조회03
	 */
	@Override
	public List<Map<String, Object>> selectWeposInspStatsList03(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectWeposInspStatsList03(mapParam);
	}
	
	/**
	 * @Method명   : selectWeposInspStatsList04
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 19. 
	 * @Method설명 : 웹심리검사 통계 조회04
	 */
	@Override
	public List<Map<String, Object>> selectWeposInspStatsList04(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectWeposInspStatsList04(mapParam);
	}
	
	/**
	 * @Method명   : selectWeposInspStatsListKC
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 19. 
	 * @Method설명 : 웹심리검사 통계 조회KC
	 */
	@Override
	public List<Map<String, Object>> selectWeposInspStatsListKC(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectWeposInspStatsListKC(mapParam);
	}
	
	/**
	 * @Method명   : selectWeposInspStatsListSC
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 19. 
	 * @Method설명 : 웹심리검사 통계 조회SC
	 */
	@Override
	public List<Map<String, Object>> selectWeposInspStatsListSC(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectWeposInspStatsListSC(mapParam);
	}
	
	/**
	 * @Method명   : selectWeposInspStatsListST
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 19. 
	 * @Method설명 : 웹심리검사 통계 조회ST
	 */
	@Override
	public List<Map<String, Object>> selectWeposInspStatsListST(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectWeposInspStatsListST(mapParam);
	}
	
	/**
	 * @Method명   : selectWeposInspStatsListANGR
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 19. 
	 * @Method설명 : 웹심리검사 통계 조회ANGR
	 */
	@Override
	public List<Map<String, Object>> selectWeposInspStatsListANGR(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectWeposInspStatsListANGR(mapParam);
	}
	
	/**
	 * @Method명   : selectWeposInspStatsListLIFE
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 19. 
	 * @Method설명 : 웹심리검사 통계 조회LIFE
	 */
	@Override
	public List<Map<String, Object>> selectWeposInspStatsListLIFE(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectWeposInspStatsListLIFE(mapParam);
	}
	
	/**
	 * @Method명   : selectWeposInspStatsListUR
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 19. 
	 * @Method설명 : 웹심리검사 통계 조회UR
	 */
	@Override
	public List<Map<String, Object>> selectWeposInspStatsListUR(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectWeposInspStatsListUR(mapParam);
	}
	
	/**
	 * @Method명   : selectWeposInspStatsListMOM
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 19. 
	 * @Method설명 : 웹심리검사 통계 조회MOM
	 */
	@Override
	public List<Map<String, Object>> selectWeposInspStatsListMOM(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectWeposInspStatsListMOM(mapParam);
	}
	
	/**
	 * @Method명   : selectWeposInspStatsDetailList1
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 19. 
	 * @Method설명 : 웹심리검사 통계 조회DetailList1
	 */
	@Override
	public List<Map<String, Object>> selectWeposInspStatsDetailList1(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectWeposInspStatsDetailList1(mapParam);
	}
	
	/**
	 * @Method명   : selectWeposInspStatsDetailList2
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 19. 
	 * @Method설명 : 웹심리검사 통계 조회DetailList2
	 */
	@Override
	public List<Map<String, Object>> selectWeposInspStatsDetailList2(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectWeposInspStatsDetailList2(mapParam);
	}
	
	/**
	 * @Method명   : selectSlrbyDgstfnStatsNewDetailList1
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 25. 
	 * @Method설명 : 솔로복만족도 통계 신_디테일_게임상담
	 */
	@Override
	public List<Map<String, Object>> selectSlrbyDgstfnStatsNewDetailList1(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectSlrbyDgstfnStatsNewDetailList1(mapParam);
	}
	
	/**
	 * @Method명   : selectSlrbyDgstfnStatsNewDetailList1
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 25. 
	 * @Method설명 : 솔로복만족도 통계 신_디테일_영상상담
	 */
	@Override
	public List<Map<String, Object>> selectSlrbyDgstfnStatsNewDetailList2(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectSlrbyDgstfnStatsNewDetailList2(mapParam);
	}
	
	/**
	 * @Method명   : selectWeposInspStatsDetailResultList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 19. 
	 * @Method설명 : 웹심리검사 통계 조회_결과
	 */
	@Override
	public List<Map<String, Object>> selectWeposInspStatsDetailResultList(DataRequest dataRequest) throws Exception {	

		List<Map<String, Object>> rtn = new ArrayList<>();
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		LOGGER.debug("paramMap 000::::::::::::" + paramMap.toString());
		LOGGER.debug("paramMap 111::::::::::::" + paramMap.get("WEPOS_MLSFC_SN"));
		
		if("05".equals(paramMap.get("WEPOS_MLSFC_SN"))) {
			rtn = conQuestionStatsMapper.selectWeposInspStatsDetailResultList05(paramMap);
		}else if("ANGR".equals(paramMap.get("WEPOS_MLSFC_SN"))) {
			rtn = conQuestionStatsMapper.selectWeposInspStatsDetailResultListANGR(paramMap);
		}else if("UR".equals(paramMap.get("WEPOS_MLSFC_SN"))) {
			rtn = conQuestionStatsMapper.selectWeposInspStatsDetailResultListUR(paramMap);
		}else if("MOM".equals(paramMap.get("WEPOS_MLSFC_SN"))) {
			rtn = conQuestionStatsMapper.selectWeposInspStatsDetailResultListMOM(paramMap);
		}else if("CAGI".equals(paramMap.get("WEPOS_MLSFC_SN")) || "ST".equals(paramMap.get("WEPOS_MLSFC_SN"))) {
			rtn = conQuestionStatsMapper.selectWeposInspStatsDetailResultListCAGIST(paramMap);
		}else if("01".equals(paramMap.get("WEPOS_MLSFC_SN"))) {
			rtn = conQuestionStatsMapper.selectWeposInspStatsDetailResultList01(paramMap);
		}else if("02".equals(paramMap.get("WEPOS_MLSFC_SN"))) {
			rtn = conQuestionStatsMapper.selectWeposInspStatsDetailResultList02(paramMap);
		}else if("03".equals(paramMap.get("WEPOS_MLSFC_SN"))) {
			rtn = conQuestionStatsMapper.selectWeposInspStatsDetailResultList03(paramMap);
		}else if("04".equals(paramMap.get("WEPOS_MLSFC_SN"))) {
			rtn = conQuestionStatsMapper.selectWeposInspStatsDetailResultList04(paramMap);
		}else if("11".equals(paramMap.get("WEPOS_MLSFC_SN")) || "17".equals(paramMap.get("WEPOS_MLSFC_SN"))) {
			rtn = conQuestionStatsMapper.selectWeposInspStatsDetailResultList1117(paramMap);
		}else if("KK".equals(paramMap.get("WEPOS_MLSFC_SN")) || "KP".equals(paramMap.get("WEPOS_MLSFC_SN"))) {
			rtn = conQuestionStatsMapper.selectWeposInspStatsDetailResultListKKKP(paramMap);
		}else if("SS".equals(paramMap.get("WEPOS_MLSFC_SN"))) {
			rtn = conQuestionStatsMapper.selectWeposInspStatsDetailResultListSS(paramMap);
		}else if("KC".equals(paramMap.get("WEPOS_MLSFC_SN"))) {
			rtn = conQuestionStatsMapper.selectWeposInspStatsDetailResultListKC(paramMap);
		}else if("SC".equals(paramMap.get("WEPOS_MLSFC_SN"))) {
			rtn = conQuestionStatsMapper.selectWeposInspStatsDetailResultListSC(paramMap);
		}else if("LIFE".equals(paramMap.get("WEPOS_MLSFC_SN"))) {
			rtn = conQuestionStatsMapper.selectWeposInspStatsDetailResultListLIFE(paramMap);
		}else if("1".equals(paramMap.get("WEPOS_MLSFC_SN"))) {
			rtn = conQuestionStatsMapper.selectWeposInspStatsDetailResultList1(paramMap);
		}else if("2".equals(paramMap.get("WEPOS_MLSFC_SN"))) {
			rtn = conQuestionStatsMapper.selectWeposInspStatsDetailResultList2(paramMap);
		}else {
			rtn = conQuestionStatsMapper.selectWeposInspStatsDetailResultListElse(paramMap);
		}
		
		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtn.toArray().length; i++) {
			map = rtn.get(i);
			
			rtn.set(i, map);
		}
		
		LOGGER.debug("rtn ::::::::::::" + rtn.toString());
		
		return rtn;
	}
	
	/**
	 * @Method명   : selectGriefSltnAlkiolDgstfnStatsDetailList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 11. 02. 
	 * @Method설명 : 고민해결백과 만족도 통계  조회_Detail
	 */
	@Override
	public List<Map<String, Object>> selectGriefSltnAlkiolDgstfnStatsDetailList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectGriefSltnAlkiolDgstfnStatsDetailList(mapParam);
	}
	
	/**
	 * @Method명   : selectLinkPrfmncStatsOnload
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 26. 
	 * @Method설명 : 연계실적 통계 Onload 조회
	 */
	@Override
	public List<Map<String, Object>> selectLinkPrfmncStatsOnload(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectLinkPrfmncStatsOnload(mapParam);
	}
	
	/**
	 * @Method명   : selectMobileLinkPrfmncStatsOnload
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 26. 
	 * @Method설명 : (모바일)연계실적 통계 Onload 조회
	 */
	@Override
	public List<Map<String, Object>> selectMobileLinkPrfmncStatsOnload(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectMobileLinkPrfmncStatsOnload(mapParam);
	}
	
	/**
	 * @Method명   : selectLinkPrfmncStatsListCHTT2
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 26. 
	 * @Method설명 : 연계실적 통계 조회_연계방법별실적_채팅
	 */
	@Override
	public List<Map<String, Object>> selectLinkPrfmncStatsListCHTT2(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectLinkPrfmncStatsListCHTT2(mapParam);
	}
	
	/**
	 * @Method명   : selectLinkPrfmncStatsListSECRE2
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 26. 
	 * @Method설명 : 연계실적 통계 조회_연계방법별실적_비밀
	 */
	@Override
	public List<Map<String, Object>> selectLinkPrfmncStatsListSECRE2(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectLinkPrfmncStatsListSECRE2(mapParam);
	}
	
	/**
	 * @Method명   : selectLinkPrfmncStatsListLINK2
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 26. 
	 * @Method설명 : 연계실적 통계 조회_연계방법별실적_연계
	 */
	@Override
	public List<Map<String, Object>> selectLinkPrfmncStatsListLINK2(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectLinkPrfmncStatsListLINK2(mapParam);
	}
	
	/**
	 * @Method명   : selectLinkPrfmncStatsListSLRBT2
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 26. 
	 * @Method설명 : 연계실적 통계 조회_연계방법별실적_솔로봇
	 */
	@Override
	public List<Map<String, Object>> selectLinkPrfmncStatsListSLRBT2(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectLinkPrfmncStatsListSLRBT2(mapParam);
	}
	
	/**
	 * @Method명   : selectLinkPrfmncStatsListOUTRC2
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 26. 
	 * @Method설명 : 연계실적 통계 조회_연계방법별실적_아웃리치
	 */
	@Override
	public List<Map<String, Object>> selectLinkPrfmncStatsListOUTRC2(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectLinkPrfmncStatsListOUTRC2(mapParam);
	}
	
	/**
	 * @Method명   : selectLinkPrfmncStatsListCHTT3
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 26. 
	 * @Method설명 : 연계실적 통계 조회_기타전문기관보기_채팅
	 */
	@Override
	public List<Map<String, Object>> selectLinkPrfmncStatsListCHTT3(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectLinkPrfmncStatsListCHTT3(mapParam);
	}
	
	/**
	 * @Method명   : selectLinkPrfmncStatsListSECRE3
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 26. 
	 * @Method설명 : 연계실적 통계 조회_기타전문기관보기_비밀
	 */
	@Override
	public List<Map<String, Object>> selectLinkPrfmncStatsListSECRE3(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectLinkPrfmncStatsListSECRE3(mapParam);
	}
	
	/**
	 * @Method명   : selectLinkPrfmncStatsListLINK3
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 26. 
	 * @Method설명 : 연계실적 통계 조회_기타전문기관보기_연계
	 */
	@Override
	public List<Map<String, Object>> selectLinkPrfmncStatsListLINK3(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectLinkPrfmncStatsListLINK3(mapParam);
	}
	
	/**
	 * @Method명   : selectLinkPrfmncStatsListSLRBT3
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 26. 
	 * @Method설명 : 연계실적 통계 조회_기타전문기관보기_솔로봇
	 */
	@Override
	public List<Map<String, Object>> selectLinkPrfmncStatsListSLRBT3(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectLinkPrfmncStatsListSLRBT3(mapParam);
	}
	
	/**
	 * @Method명   : selectLinkPrfmncStatsListOUTRC3
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 26. 
	 * @Method설명 : 연계실적 통계 조회_기타전문기관보기_아웃리치
	 */
	@Override
	public List<Map<String, Object>> selectLinkPrfmncStatsListOUTRC3(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectLinkPrfmncStatsListOUTRC3(mapParam);
	}
	
	/**
	 * @Method명   : selectLinkPrfmncStatsList2
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Sin.Hyun.Jin
	 * @작성일     : 2023. 01. 16.
	 * @Method설명 : 연계실적 통계 조회_연계방법별실적_오픈채팅
	 */
	@Override
	public List<Map<String, Object>> selectLinkPrfmncStatsListOpenChtt2(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectLinkPrfmncStatsListOpenChtt2(mapParam);
	}
	
	/**
	 * @Method명   : selectLinkPrfmncStatsList3
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Sin.Hyun.Jin
	 * @작성일     : 2023. 01. 16.
	 * @Method설명 : 연계실적 통계 조회_기타전문기관보기_오픈채팅
	 */
	@Override
	public List<Map<String, Object>> selectLinkPrfmncStatsListOpenChtt3(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionStatsMapper.selectLinkPrfmncStatsListOpenChtt3(mapParam);
	}
	
	/**
	 * @Method명   : selectLinkPrfmncStatsList3
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2023. 01. 19.
	 * @Method설명 : 연계실적 통계 조회_매체별*기관별 실적
	 */
	@Override
	public List<Map<String, Object>> selectMediaList(Map<String, Object> mapParam) throws Exception {

		return conQuestionStatsMapper.selectMediaList(mapParam);
	}
	
	/**
	 * @Method명   : selectInstList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee TaeHo
	 * @작성일     : 2023. 1. 19. 
	 * @Method설명 : 연계실적 통계 조회_월별*기관별 실적
	 */
	@Override
	public Map<String, Object> selectInstList(Map<String, Object> mapParam) throws Exception {
		
		Map<String, Object> result = new HashMap<>();
		
        String startDate = String.valueOf(mapParam.get("startDate"));
        String endDate = String.valueOf(mapParam.get("endDate"));
//        String startYear = startDate.substring(0, 4);
//        String endYear = endDate.substring(0, 4);
        String startYM = startDate.substring(0, 6);
        String endYM = endDate.substring(0, 6);
//        String writeType = String.valueOf(mapParam.get("writeType"));
        
        ArrayList<String> month =  new ArrayList<String>();
//        if(startYear.equals(endYear)) {
//        	int startYM = Integer.parseInt(startDate.substring(0, 6));
//        	int endYM = Integer.parseInt(endDate.substring(0, 6));
//        	for(int i=startYM;i<=endYM;i++) {
//        		month.add("A"+String.valueOf(i));
//        	}       	
//        }else {
//        	int mm = Integer.parseInt(startDate.substring(4, 6));
//        	
//        	for(int i=mm;i<=12;i++) {
//        		String k = "0"+String.valueOf(i);
//        		month.add("A"+startYear+k.substring(k.length()-2, k.length()));
//        	}  
//        	mm = Integer.parseInt(endDate.substring(4, 6));
//        	for(int i=1;i<=mm;i++) {
//        		String k = "0"+String.valueOf(i);
//        		month.add("A"+endYear+k.substring(k.length()-2, k.length()));
//        	}         	
//        }
        
        for (int i= Integer.valueOf(startYM); i <= Integer.valueOf(endYM); i++) {
            log.debug("for startYM ::: " + i);
            if (String.valueOf(i).substring(4, 6) == "13") {
                i += 88;
                log.debug("String.valueOf(i).substring(4, 2) + 1 ::: " + i);
            }
            month.add("A"+String.valueOf(i));
        }
        
        mapParam.put("month", month);
        
        List<Map<String, Object>> dsInstList = new ArrayList<Map<String,Object>>();

        dsInstList = conQuestionStatsMapper.selectInstList(mapParam);	
		for (Map<String, Object> map : dsInstList) {
			int ssum = 0;
			for(int i=0; i < month.size(); i++) {
				if (map.get(month.get(i)) == null ) {
					map.put(month.get(i), 0);
					continue;
				}
				ssum += Integer.parseInt(map.get(month.get(i)).toString());
			}
			map.put("SSUM", ssum);
		}

        result.put("dsInstList", dsInstList);
        
        return result;        
		
	}

}
