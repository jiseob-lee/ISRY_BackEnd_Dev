/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/

package isry.couns.stats.dscsnstats.service;

import java.util.List;
import java.util.Map;

public interface ConQuestionNewStatsService   {
	 	
	/**
	 * 
	 * @Method명   : list
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2022. 3. 22. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectType1NewComb(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectType2NewComb(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectconQuestionNewStats(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectconQuestionNewStats1(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectconQuestionNewStats2(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectconQuestionNewStats3(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectconQuestionNewStats4(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectconQuestionNewStats5(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectconQuestionNewStats6(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectconQuestionNewStats7(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectconQuestionNewStats8(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectconQuestionNewStatsChatWaitAvg(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectconQuestionNewStatsChatWait(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectSuryScoreStats(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectSuryScoreStatsAvgr(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectSuryChttWaitAvrg(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectSuryChttWait(Map<String, Object> mapParam) throws Exception;
}
