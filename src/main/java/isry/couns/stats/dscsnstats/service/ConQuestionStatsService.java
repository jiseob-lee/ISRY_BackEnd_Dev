/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/

package isry.couns.stats.dscsnstats.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

public interface ConQuestionStatsService   {
	 	
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
	List<Map<String, Object>> selectType1Comb(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectType2Comb(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectconQuestionStats(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectconQuestionStats1(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectconQuestionStats2(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectconQuestionStats3(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectconQuestionStats4(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectconQuestionStats5(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectconQuestionStats6(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectconQuestionStats7(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectconQuestionStats8(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectconQuestionStatsChatWaitAvg(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectconQuestionStatsChatWait(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectSrvyExmnStatsList1(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectSrvyExmnStatsList2(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectSrvyExmnStatsList3(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectSrvyExmnStatsList4(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectSrvyExmnStatsList5(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectCnctDscsnStatsList(Map<String, Object> mapParam) throws Exception;	
	
	List<Map<String, Object>> selectConsttSrvyStatsList(Map<String, Object> mapParam, HttpServletRequest request) throws Exception;
	
	/**
	 * @Method명   : selectCnctDgstfnStatsList
	 * @param 	   : mapParam
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 7. 
	 * @Method설명 : 이음게시판카테고리구분코드에 따른 이음-e 만족도 통계 목록 조회
	 */
	List<Map<String, Object>> selectCnctDgstfnStatsList(Map<String, String> mapParam) throws Exception;
	
	List<Map<String, Object>> selectCnctDgstfnStatsList1(Map<String, Object> mapParam) throws Exception;	
	
	List<Map<String, Object>> selectCnctDgstfnStatsList2(Map<String, Object> mapParam) throws Exception;	
	
	List<Map<String, Object>> selectCnctDgstfnStatsList3(Map<String, Object> mapParam) throws Exception;	
	
	List<Map<String, Object>> selectCnctDgstfnStatsList4(Map<String, Object> mapParam) throws Exception;	
	
	List<Map<String, Object>> selectCnctChngList(Map<String, Object> mapParam) throws Exception;	
	
	List<Map<String, Object>> selectNtabrdDscsnStatsList(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectNtabrdDscsnStatsList2(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectSlrbyDscsnStatsList1(Map<String, Object> mapParam) throws Exception;	
	
	List<Map<String, Object>> selectSlrbyDscsnStatsList2(Map<String, Object> mapParam) throws Exception;	
	
	List<Map<String, Object>> selectSlrbyDscsnStatsList3(Map<String, Object> mapParam) throws Exception;	
	
	List<Map<String, Object>> selectSlrbyDscsnStatsList4(Map<String, Object> mapParam) throws Exception;	
	
	List<Map<String, Object>> selectSlrbyDscsnStatsList5(Map<String, Object> mapParam) throws Exception;	
	
	List<Map<String, Object>> selectSlrbyDgstfnStatsNewList1(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectSlrbyDgstfnStatsNewList2(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectGriefSltnAlkiolDgstfnStatsList(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectWeposInspStatsList1(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectWeposInspStatsList2(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectLinkPrfmncStatsList1(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectLinkPrfmncStatsList2(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectLinkPrfmncStatsList3(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectOutrcStatsList1(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectOutrcStatsList2(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectOutrcStatsList3(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectOutrcStatsList4(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectOutrcStatsList5(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectMblaLinkPrfmncStatsList1(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectMblaLinkPrfmncStatsListCHTT1(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectMblaLinkPrfmncStatsListMBLA1(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectMblaLinkPrfmncStatsList2(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectMblaLinkPrfmncStatsListCHTT2(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectMblaLinkPrfmncStatsListMBLA2(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectMblaLinkPrfmncStatsList3(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectMblaLinkPrfmncStatsListCHTT3(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectMblaLinkPrfmncStatsListMBLA3(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectWeposInspStatsList(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectWeposInspStatsListSe2(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectWeposInspStatsList05(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectWeposInspStatsList09(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectWeposInspStatsList11(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectWeposInspStatsList17(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectWeposInspStatsList07(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectWeposInspStatsListKK(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectWeposInspStatsListSS(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectWeposInspStatsListKP(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectWeposInspStatsListCAGI(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectWeposInspStatsList01(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectWeposInspStatsList02(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectWeposInspStatsList03(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectWeposInspStatsList04(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectWeposInspStatsListKC(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectWeposInspStatsListSC(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectWeposInspStatsListST(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectWeposInspStatsListANGR(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectWeposInspStatsListLIFE(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectWeposInspStatsListUR(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectWeposInspStatsListMOM(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectWeposInspStatsDetailList1(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectWeposInspStatsDetailList2(Map<String, Object> mapParam) throws Exception;

	public List<Map<String, Object>> selectWeposInspStatsDetailResultList(DataRequest dataRequest) throws Exception;
	
	List<Map<String, Object>> selectGriefSltnAlkiolDgstfnStatsDetailList(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectSlrbyDgstfnStatsNewDetailList1(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectSlrbyDgstfnStatsNewDetailList2(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectLinkPrfmncStatsOnload(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectMobileLinkPrfmncStatsOnload(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectLinkPrfmncStatsListCHTT2(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectLinkPrfmncStatsListSECRE2(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectLinkPrfmncStatsListLINK2(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectLinkPrfmncStatsListSLRBT2(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectLinkPrfmncStatsListOUTRC2(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectLinkPrfmncStatsListCHTT3(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectLinkPrfmncStatsListSECRE3(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectLinkPrfmncStatsListLINK3(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectLinkPrfmncStatsListSLRBT3(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectLinkPrfmncStatsListOUTRC3(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectLinkPrfmncStatsListOpenChtt2(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectLinkPrfmncStatsListOpenChtt3(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectMediaList(Map<String, Object> mapParam) throws Exception;
	
	Map<String, Object> selectInstList(Map<String, Object> mapParam) throws Exception;
}
