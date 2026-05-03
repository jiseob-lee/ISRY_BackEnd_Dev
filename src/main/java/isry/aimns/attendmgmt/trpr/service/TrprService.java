/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.aimns.attendmgmt.trpr.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명 : TrprService.java
 * @프로그램 설명 :
 * @작성자 : Lee.Hye.Sun
 * @작성일 : 2022. 6. 7.
 * @수정자 : Lee.Hye.Sun
 * @수정일 : 2022. 6. 7.
 * @수정내용 :
 */

public interface TrprService {

	/** 수료자 명단 */
	public List<Map<String, String>> selectTrprFnsh(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;

	/** 조기취업자명단 */
	public List<Map<String, String>> selectTrprEmpymn(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;

	/** 중도탈락자 명단 */
	public List<Map<String, String>> selectTrprMdstrmFailr(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;

	/** 자격증취득자 명단 */
	public List<Map<String, String>> selectTrprCertiAcqs(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;

	/** 학력취득자명단 */
	public List<Map<String, String>> selectTrprAcbgAcqs(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;

	/** 학력취득자명단 상세 */
	public Map<String, List<Map<String, String>>> selectTrprAcbgAcqsDtl(DataRequest dataRequest) throws Exception;

	/** 기타자명단 */
	public List<Map<String, String>> selectTrprEtc(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;

	/** 대상자이력목록 */
	public List<Map<String, Object>> selectTrprHstrList(HttpServletRequest request, Map<String, String> paramMap)
			throws Exception;

}
