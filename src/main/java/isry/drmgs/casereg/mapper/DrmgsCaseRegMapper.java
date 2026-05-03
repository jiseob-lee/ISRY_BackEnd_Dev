/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.casereg.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : DrmgsCaseRegMapper.java
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
@Mapper("drmgsCaseRegMapper")
public interface DrmgsCaseRegMapper {

	public Map<String, String> outcomeDetail(Map<String, String> map) throws Exception;
	
	public List<Map<String, String>> outcomeList(Map<String, String> map) throws Exception;
	
	public List<Map<String, String>> dsOccpOutList(Map<String, String> map) throws Exception;
	
	public int insertOutcomeDetail(Map<String, String> map) throws Exception;
	
	public int updateOutcomeDetail(Map<String, String> map) throws Exception;
	
	public int insertOutcomeAllDetail(Map<String, String> map) throws Exception;
	
	public int updateOutcomeAllDetail(Map<String, String> map) throws Exception;
	
	public int insertOccpDetail(Map<String, String> map) throws Exception;
	
	public int updateOccpDetail(Map<String, String> map) throws Exception;
	
	public Map<String, String> outcomeCnDetail(Map<String, String> map) throws Exception;
	
	public int deleteOutcomeCnDetailAll(Map<String, String> map) throws Exception;
	
	public int insertOutcomeCnDetail(Map<String, String> map) throws Exception;
	
	public int updateOutcomeCnDetail(Map<String, String> map) throws Exception;
	
	public int deleteOutcomeCnDetail(Map<String, String> map) throws Exception;
	
	public List<Map<String, String>> dsProgrmList(Map<String, String> map) throws Exception;
	
	public int dsProgrmInsert(Map<String, String> map) throws Exception;
	
	public int dsProgrmUpdate(Map<String, String> map) throws Exception;
	
	public int dsProgrmDelete(Map<String, String> map) throws Exception;
	
	public int dsOccpOutInsert(Map<String, String> map) throws Exception;
	
	public int dsOccpOutUpdate(Map<String, String> map) throws Exception;
	
	public int dsOccpOutDelete(Map<String, String> map) throws Exception;
	
	public List<Map<String, String>> dsSchulwList(Map<String, String> map) throws Exception;
	
	public Map<String, String> dsSchulwDetail(Map<String, String> map) throws Exception;
	
	public int dsSchulwInsert(Map<String, String> map) throws Exception;
	
	public int dsSchulwUpdate(Map<String, String> map) throws Exception;
	
	public List<Map<String, String>> onSchulwDscntcList(Map<String, String> map) throws Exception;
	
	public List<Map<String, Object>> selectPreSurvshtList(Map<String, Object> map) throws Exception;	
	
	public List<Map<String, Object>> selectOccpAbilitInsertList(Map<String, Object> map) throws Exception;	
	
	public int mergeSBB100(Map<String, String> map) throws Exception;
	
	public int mergeSBB500(Map<String, String> map) throws Exception;
	
	public int deleteSBB220(Map<String, String> map) throws Exception;
	
	public int insertSBB220(Map<String, String> map) throws Exception;
	
	public int mergeSBB510(Map<String, String> map) throws Exception;
	
	public void deleteAKA011(Map<String, String> map) throws Exception;
	
	public void insertAKA011(Map<String, String> map) throws Exception;
	
	public int updateChupAKA000(Map<String, String> map) throws Exception;
	
	public int insertChupAKA001(Map<String, String> map) throws Exception;
	
	public int updateChupAKA001(Map<String, String> map) throws Exception;
	
	public int deleteChupAKA001(Map<String, String> map) throws Exception;
	
	public List<Map<String, Object>> selectChupInfo(Map<String, String> map) throws Exception;
//	public List<Map<String, Object>> selectChupList(Map<String, String> map) throws Exception;

	public List<Map<String, String>> selectQusList(Map<String, String> map) throws Exception;
	public List<Map<String, String>> selectInspSrvyList(Map<String, String> map) throws Exception;

/***********************************************************************************************************/

	//사례상세조회 - 검사/설문
	public List<Map<String, Object>> selectCaseInspSrvyList(Map<String, String> map) throws Exception;

	//검사/설문 등록
	public int insertAKA010(Map<String, String> map) throws Exception;

	//검사/설문 수정
	public int updateAKA010(Map<String, String> map) throws Exception;

	//검사/설문 삭제
	public int deleteAKA010(Map<String, String> map) throws Exception;

	//사례건강검진 조회(AKA000)
	public List<Map<String, Object>> selectCaseChupList(Map<String, String> map) throws Exception;

	//건강검진조회(AKA001)
	public List<Map<String, Object>> selectChupList(Map<String, String> map) throws Exception;

	//활동안전공제회조회(AKA002)
	public List<Map<String, Object>> selectActvtSafetyMuaiasList(Map<String, String> map) throws Exception;
	
	//위기스크리닝(AKA000)
	public List<Map<String, Object>> selectCrisisScrenn(Map<String, String> map) throws Exception;
	
	//희망서비스 조사표(AKA012)
	public List<Map<String, Object>> selectHpeSrvc(Map<String, String> map) throws Exception;
	
	//사례건강검진 등록
	public int insertAKA000(Map<String, String> map) throws Exception;
	
	//사례건강검진 수정
	public int updateAKA000(Map<String, String> map) throws Exception;
	
	//건강검진 등록
	public int insertAKA001(Map<String, String> map) throws Exception;
	
	//건강검진 수정
	public int updateAKA001(Map<String, String> map) throws Exception;
	
	//건강검진 삭제
	public int deleteAKA001(Map<String, String> map) throws Exception;
	
	//활동안전공제회 등록
	public int insertAKA002(Map<String, String> map) throws Exception;
	
	//활동안전공제회 수정
	public int updateAKA002(Map<String, String> map) throws Exception;
	
	//활동안전공제회 삭제
	public int deleteAKA002(Map<String, String> map) throws Exception;
	
	public int outcomeTrmninsert(Map<String, String> map) throws Exception;
	
	public int outcomeTrmnupdate(Map<String, String> map) throws Exception;
	
	public int updateAKA100(Map<String, String> map) throws Exception;
	
	public int updateSEB510(Map<String, String> map) throws Exception;
	
	// 직업역량강화 최근 이력 조회
	public Map<String, String> selectStgHis(Map<String, String> map) throws Exception;
	
	// 자격증 대상자자격정보 조회
	public List<Map<String, String>> selectCertiTrprQlfcInfo(Map<String, String> map) throws Exception;
	
	// 대상자정보번호 검색
	public String selectTrprInfoNo(Map<String, String> map) throws Exception;
	
	// 개인식별번호 검색
	public String selectIndvIdntfcNo(String str) throws Exception;
	
	public List<Map<String, Object>> selectOutcMainList(Map<String, Object> map) throws Exception;
	
	public String selectOutcMainListCount(Map<String, Object> map) throws Exception;
	
	public List<Map<String, String>> selectSec330(Map<String, Object> map) throws Exception;
	
	public void updateCrisisScrenn(Map<String, String> map) throws Exception;
	
	// 희망서비스 저장
	public void mergeHpeSrvc(Map<String, String> map) throws Exception;
	
}
