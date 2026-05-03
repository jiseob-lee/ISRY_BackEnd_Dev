/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.aimns.attendmgmt.trpr.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : TrprMapper.java
 * @프로그램 설명 : - -
 * @작성자 : Lee.Hye.Sun
 * @작성일 : 2022. 6. 7.
 * @수정자 : Lee.Hye.Sun
 * @수정일 : 2022. 6. 7.
 * @수정내용 : - -
 */
@Mapper("trprMapper")
public interface TrprMapper {

	/** 수료자 명단 */
	public List<Map<String, String>> selectTrprFnsh(Map<String, String> mapParam) throws Exception;

	/** 조기취업자명단 */
	public List<Map<String, String>> selectTrprEmpymn(Map<String, String> mapParam) throws Exception;

	/** 중도탈락자 명단 */
	public List<Map<String, String>> selectTrprMdstrmFailr(Map<String, String> mapParam) throws Exception;

	/** 자격증취득자 명단 */
	public List<Map<String, String>> selectTrprCertiAcqs(Map<String, String> mapParam) throws Exception;

	/** 기타자명단 */
	public List<Map<String, String>> selectTrprEtc(Map<String, String> mapParam) throws Exception;

	/** 학력취득자명단 */
	public List<Map<String, String>> selectTrprAcbgAcqs(Map<String, String> mapParam) throws Exception;

	/** 학력취득자명단 - 기본정보 */
	public List<Map<String, String>> selectTrprAcbgAcqsPrvc(Map<String, Object> mapParam) throws Exception;

	/** 학력취득자명단 - 자격증 정보 */
	public List<Map<String, String>> selectTrprCertiInfo(Map<String, Object> mapParam) throws Exception;

	/** 학력취득자명단 - 취업정보 */
	public List<Map<String, String>> selectTrprEmpymnInfo(Map<String, Object> mapParam) throws Exception;

	/** 학력취득자명단 - 학위취득정보 */
	public List<Map<String, String>> selectTrprAcbgInfo(Map<String, Object> mapParam) throws Exception;

	/** 학력취득자명단 - 최종학력 */
	public List<Map<String, String>> selectTrprLastAcbg(Map<String, Object> mapParam) throws Exception;

	/** 학력취득자명단 - 교육과정정보 */
	public List<Map<String, String>> selectTrprEduInfo(Map<String, Object> mapParam) throws Exception;

	/** 대상자이력 */
	public List<Map<String, Object>> selectTrprHstrList(Map<String, String> paramMap) throws Exception;

}
