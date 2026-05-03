/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : MgmtOrgDtlService.java
 * @프로그램 설명 : 기관 상세 정보 관리
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 3. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 3.
 * @수정내용      : 
 * -                
 * -                
 */
public interface MgmtOrgDtlService {
	
	public Map<String, String> saveOrgDtl(HttpServletRequest request, DataRequest dataRequest, List<Map<String, String>> fileInfoList) throws Exception;
	public void deleteOrganization(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	// 추가기본정보TAP 저장
	public int saveAddtngBassInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	// 설치및위탁정보TAP 저장
	public int saveInstlCnsgnInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	// 운영정보TAP 저장
	public int saveOperInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	// 청소년상담전화1388TAP 저장
	public int saveYngbgs1388(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	// 시설정보TAP 저장
	public int saveFcltyInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception;	
	// 추가정보 조회
	public Map<String, Object> selectYngbgsSheltr(DataRequest dataRequest) throws Exception;	
	// 운영정보 조회_AKA510 /* 센터현황-운영시간 */
	public List<Map<String, String>> selectOperHour(DataRequest dataRequest) throws Exception;
	// 운영정보 조회_AKA520 /* 센터현황-분소운영 */
	public List<Map<String, String>> selectBrofaOper(DataRequest dataRequest) throws Exception;
	// 청소년상담전화1388 조회
	public Map<String, Object> selectYngbgs1388(DataRequest dataRequest) throws Exception;		
	// AKA530_센터현황-1388전화운영시간
	public List<Map<String, String>> selectOperHour1388(DataRequest dataRequest) throws Exception;
	// AKA540_센터현황-1388전화근무현황
	public List<Map<String, String>> selectTpriRcvr1388(DataRequest dataRequest) throws Exception;
	// AKA540_센터현황-1388전화근무현황
	public List<Map<String, String>> selectEcshgStaff1388(DataRequest dataRequest) throws Exception;
	// AKA550_센터현황-1388운영인력
	public List<Map<String, String>> selectOperHnf1388(DataRequest dataRequest) throws Exception;	
	// 시설정보 조회 
	public Map<String, Object> selectFcltyInfo(DataRequest dataRequest) throws Exception;	
	// AKA570_센터현황-사용공간세부
	public List<Map<String, String>> selectUseSpce(DataRequest dataRequest) throws Exception;
	// AKA580_센터현황-이동형일시쉼터용차량
	public List<Map<String, String>> selectUseSpceInfo(DataRequest dataRequest) throws Exception;
	// AKA590_센터현황-학교밖청소년전용공간
	public List<Map<String, String>> selectOschlYngbgsPrvuseSpace(DataRequest dataRequest) throws Exception;
	// 청소년시설 조회 
	public List<Map<String, String>> selectYngbgsFclty(DataRequest dataRequest) throws Exception;	
	// 설치및위탁정보 조회
	public Map<String, Object> selectInstlCnsgnInfo(DataRequest dataRequest) throws Exception;	
}
