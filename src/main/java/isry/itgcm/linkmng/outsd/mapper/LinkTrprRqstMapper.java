/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.linkmng.outsd.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : LinkTrprRqstMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : TaesooSong
 * @작성일        : 2022. 8. 2. 
 * @수정자        : TaesooSong
 * @수정일        : 2022. 8. 2.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("linkTrprRqstMapper")
public interface LinkTrprRqstMapper {
	public Map<String, Object> getTrprEnfsnInfo(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> getRcptHisList(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> selectCaseMngHisList(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectSrvcPvsnHisList(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectSrvcPvsnRqstList(Map<String, Object> map) throws Exception;

	public String selectSysSeCd(Map<String, String> map) throws Exception;
	
	public void insertSrvcPvsnRqst(Map<String, Object> map) throws Exception;
	
	public String selectFrstRqstNo(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectLinkTrprRqstList(Map<String, Object> map) throws Exception;
	
	public Map<String, Object> getSrvcPvsnRqstData(Map<String, Object> map) throws Exception;

	public Map<String, Object> getTrprUserInfo(Map<String, Object> map) throws Exception;

	public Map<String, Object> getUserTrprInfo(Map<String, Object> map) throws Exception;
	
	public Map<String, Object> getYngbgsPrtcrInfo(Map<String, Object> map) throws Exception;
	
	public Map<String, Object> selectSrvcPvsnRqstData(Map<String, Object> map) throws Exception;
	
	public Map<String, Object> getRqstTrprData(Map<String, Object> map) throws Exception;

	public void insertSrvcPvsnRqstHistory(Map<String, Object> map ) throws Exception;

	public void updateSrvcPvsnRqst(Map<String, Object> map ) throws Exception;
	
	public String getTrprInfoData(Map<String, Object> map ) throws Exception;

	public List<Map<String, Object>> selectTrprInfoChk(Map<String, Object> map) throws Exception;

	public String getRqstTrprInfoExistingCheck(Map<String, Object> map ) throws Exception;

	public void insertLinkTrprInfo(Map<String, Object> map ) throws Exception;
	
	public Map<String, Object> selectIndvIdntfcInfo(Map<String, Object> map) throws Exception;
	
	public int insertSrvcPvsnRqstRcpt(Map<String, Object> map) throws Exception;

	public void updateSrvcPvsnRqstAplyYn(Map<String, Object> map ) throws Exception;

	public void insertSrvcPvsnRqstRcptHistory(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> selectLinkTrprRcptList(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectLinkTrprRcpt(Map<String, Object> map) throws Exception;
	
	public void updateSrvcPvsnRcpt(Map<String, Object> map) throws Exception;

	public Map<String, Object> selectLinkTrprRcptHist(Map<String, Object> map) throws Exception;

	public void updateLinkTrprRcptSeCd(Map<String, Object> map) throws Exception;
	
	public Map<String, Object> getIndvIdntfcInfo(Map<String, Object> map) throws Exception;
	
	public String insertTrprInfoData(Map<String, Object> map) throws Exception;
	
	public Map<String, Object> selectTrprInfoData(Map<String, Object> map) throws Exception;
	
	public void updateBbscttEsntalNo(Map<String, Object> map) throws Exception;
	
	public void updateBbscttEsntalNo2(Map<String, Object> map) throws Exception;
	
	public void updateBbscttEsntalNo3(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> selectLinkTrprRcptHistory(Map<String, Object> map) throws Exception;
	
	//병무청 연계목록
//	public List<Map<String, Object>> selectLinkMmatList(Map<String, String> map) throws Exception;
	
	public void updateTrprCaseMngInfo(Map<String, Object> map) throws Exception;
	
	public Map<String, Object> selectTrprSrvcInfo(Map<String, Object> map) throws Exception;
	
	public void updateAplyYn(Map<String, Object> map) throws Exception;
	
	public void deleteLinkTrprRqst(Map<String, Object> map) throws Exception;
	
	public void deleteLinkTrprRqstHis(Map<String, Object> map) throws Exception;

	public Map<String, Object> selectLinkBbsctt(Map<String, Object> map) throws Exception;
	
	public String getCaseYn(Map<String, Object> map) throws Exception;
	
	public void updateRcptDtlCn(Map<String, Object> map) throws Exception;
	
	public Map<String, String> selectSEB420(Map<String, Object> map) throws Exception;
	
	public void updateSEB420(Map<String, String> map) throws Exception;
	
	public String selectLinkTrprRcptCnt(Map<String, Object> map) throws Exception;
}
