/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.stats.dscsnstats.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import isry.base.IsryBaseServiceImpl;
import isry.couns.stats.dscsnstats.mapper.ConQuestionNewStatsMapper;
import isry.couns.stats.dscsnstats.service.ConQuestionNewStatsService;





@Service("conQuestionNewStatsService")
public class ConQuestionNewStatsServiceimpl extends IsryBaseServiceImpl implements ConQuestionNewStatsService   {

	@Resource(name = "conQuestionNewStatsMapper")
	private ConQuestionNewStatsMapper conQuestionNewStatsMapper;




	/**
	 * @Method명   : selectType1NewComb
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectType1NewComb(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionNewStatsMapper.selectType1NewComb(mapParam);
	}


	/**
	 * @Method명   : selectType2NewComb
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectType2NewComb(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionNewStatsMapper.selectType2NewComb(mapParam);
	}


	/**
	 * @Method명   : selectconQuestionNewStats
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectconQuestionNewStats(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionNewStatsMapper.selectconQuestionNewStats(mapParam);
	}

	/**
	 * @Method명   : selectconQuestionNewStats1
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectconQuestionNewStats1(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionNewStatsMapper.selectconQuestionNewStats1(mapParam);
	}


	/**
	 * @Method명   : selectconQuestionNewStats2
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectconQuestionNewStats2(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionNewStatsMapper.selectconQuestionNewStats2(mapParam);
	}


	/**
	 * @Method명   : selectconQuestionNewStats3
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectconQuestionNewStats3(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionNewStatsMapper.selectconQuestionNewStats3(mapParam);
	}


	/**
	 * @Method명   : selectconQuestionNewStats4
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectconQuestionNewStats4(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionNewStatsMapper.selectconQuestionNewStats4(mapParam);
	}


	/**
	 * @Method명   : selectconQuestionNewStats5
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectconQuestionNewStats5(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionNewStatsMapper.selectconQuestionNewStats5(mapParam);
	}


	/**
	 * @Method명   : selectconQuestionNewStats6
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectconQuestionNewStats6(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionNewStatsMapper.selectconQuestionNewStats6(mapParam);
	}


	/**
	 * @Method명   : selectconQuestionNewStats7
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectconQuestionNewStats7(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionNewStatsMapper.selectconQuestionNewStats7(mapParam);
	}


	/**
	 * @Method명   : selectconQuestionNewStats8
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectconQuestionNewStats8(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionNewStatsMapper.selectconQuestionNewStats8(mapParam);
	}


	/**
	 * @Method명   : selectconQuestionNewStatsChatWaitAvg
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectconQuestionNewStatsChatWaitAvg(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionNewStatsMapper.selectconQuestionNewStatsChatWaitAvg(mapParam);
	}


	/**
	 * @Method명   : selectconQuestionNewStatsChatWait
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectconQuestionNewStatsChatWait(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionNewStatsMapper.selectconQuestionNewStatsChatWait(mapParam);
	}

	/**
	 * @Method명		: selectSuryScoreStatsAll
	 * @param		: mapParam
	 * @return		: List<Map<String, Object>>
	 * @throws		: Exception
	 * @작성자     	: Sin.Hyun.Jin
	 * @작성일     	: 2023. 01. 16. 
	 * @Method설명	: 상담설문 통계 조회
	 */
	@Override
	public List<Map<String, Object>> selectSuryScoreStats(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionNewStatsMapper.selectSuryScoreStats(mapParam);
	}
	
	/**
	 * @Method명		: selectSuryScoreStatsAll
	 * @param		: mapParam
	 * @return		: List<Map<String, Object>>
	 * @throws		: Exception
	 * @작성자     	: Sin.Hyun.Jin
	 * @작성일     	: 2023. 01. 16. 
	 * @Method설명	: 상담설문 통계 조회 평균
	 */
	@Override
	public List<Map<String, Object>> selectSuryScoreStatsAvgr(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return conQuestionNewStatsMapper.selectSuryScoreStatsAvgr(mapParam);
	}

	/**
	 * @Method명		: selectSuryChttWaitAvrg
	 * @param		: mapParam
	 * @return		: List<Map<String, Object>>
	 * @throws		: Exception
	 * @작성자     	: Sin.Hyun.Jin
	 * @작성일     	: 2023. 01. 16. 
	 * @Method설명	: 상담설문 통계 채팅 평균 만족도, 평균 채팅 대기 시간
	 */
	@Override
	public List<Map<String, Object>> selectSuryChttWaitAvrg(Map<String, Object> mapParam) throws Exception {
		return conQuestionNewStatsMapper.selectSuryChttWaitAvrg(mapParam);
	}
	
	/**
	 * @Method명		: selectSuryChttWait
	 * @param		: mapParam
	 * @return		: List<Map<String, Object>>
	 * @throws		: Exception
	 * @작성자     	: Sin.Hyun.Jin
	 * @작성일     	: 2023. 01. 16. 
	 * @Method설명	: 상담설문 통계 채팅 대기 시간별 건수
	 */
	@Override
	public List<Map<String, Object>> selectSuryChttWait(Map<String, Object> mapParam) throws Exception {
		return conQuestionNewStatsMapper.selectSuryChttWait(mapParam);
	}
}
