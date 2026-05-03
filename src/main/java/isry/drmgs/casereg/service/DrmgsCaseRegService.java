/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.casereg.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : DrmgsCaseRegService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kang.Hwa.Young
 * @작성일        : 2022. 7. 8. 
 * @수정자        : Kang.Hwa.Young
 * @수정일        : 2022. 7. 8.
 * @수정내용      : 
 * -                
 * -                
 */
public interface DrmgsCaseRegService {

	public Map<String, String> outcomeDetail(DataRequest dataRequest) throws Exception;
	public List<Map<String, String>> outcomeList(DataRequest dataRequest) throws Exception;
	public List<Map<String, String>> dsOccpOutList(DataRequest dataRequest) throws Exception;
	public Map<String, String> onOutcSave(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	public Map<String, String> onOutcExcnSave(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	public Map<String, String> onOutcAllSave(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	public List<Map<String, String>> dsProgrmList(DataRequest dataRequest) throws Exception;
	public Map<String, String> onOccpAbilitSave(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	public List<Map<String, String>> dsSchulwList(DataRequest dataRequest) throws Exception;
	public Map<String, String> onSchulwDscntcSave(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	public List<Map<String, String>> onSchulwDscntcList(DataRequest dataRequest) throws Exception;
	public List<Map<String, Object>> selectOccpAbilitInsertList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public Map<String, Object> onOccpSurvshtDtl(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	public Map<String, String> onOccpSurvshtSave(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public List<Map<String, String>> outcomeAllList(DataRequest dataRequest) throws Exception;
	public Map<String, String> outcomeAllDetail(DataRequest dataRequest) throws Exception;
	public List<Map<String, String>> dsSEC330(DataRequest dataRequest) throws Exception;
	
	public List<Map<String, String>> selectQusList(DataRequest dataRequest) throws Exception;
	public List<Map<String, String>> selectInspSrvyList(DataRequest dataRequest) throws Exception;

	/**
	 * 사례관리-등록-건강검진조회
	 * @Method명   : selectChupList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2022. 7. 26. 
	 * @Method설명 :
	 */
	public Map<String, Object> selectChupList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * 사례관리 상세조회
	 * @Method명   : selectCaseMngDetail
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seung.Yeon
	 * @작성일     : 2022. 8. 12. 
	 * @Method설명 :
	 */
	public Map<String, Object> selectCaseMngDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * 사례관리 상세정보 저장
	 * @Method명   : saveCaseRegDetail
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2022. 7. 26. 
	 * @Method설명 :
	 */
	public Map<String, String> saveCaseRegDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	
	/**
	 * 사례관리 상세정보 저장
	 * @Method명   : saveCaseTrmnDetail
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2022. 7. 26. 
	 * @Method설명 :
	 */
	public Map<String, String> saveCaseTrmnDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * 직업역량강화 최근 이력 조회
	 * @Method명   : outStgHis
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2022. 9. 3. 
	 * @Method설명 :
	 */
	public Map<String, String> outStgHis(DataRequest dataRequest) throws Exception;
	
	public List<Map<String, Object>> selectOutcMainList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> selectOutcPagingList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public Map<String, Object> selectOutcTrprList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
}
