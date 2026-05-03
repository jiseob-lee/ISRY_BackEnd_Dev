/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : MgmtOrgDtlMapper.java
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
@Mapper("mgmtOrgDtlMapper")
public interface MgmtOrgDtlMapper {
	
	public void saveOrgDtl(Map<String, String> map) throws Exception;
	
	public void updateOrgSequence() throws Exception;
	
	public void saveOrgDtlHistory(Map<String, String> map) throws Exception;
	
	public int selectInstNoCnt(Map<String, String> map) throws Exception;

	public void deleteOrganization(Map<String, Object> map) throws Exception;
	
	public void saveOrgRestArea(Map<String, String> mapRestArea) throws Exception;
	
	// 센터현황T(AKA500)_추가기본정보
	public void saveCnterPreconAddingBassInfo(Map<String, String> map) throws Exception;
		
	// 센터현황T(AKA500)_설치및위탁정보
	public void saveCnterPreconInstlCnsgnInfo(Map<String, String> map) throws Exception;
	
	// 센터현황T(AKA500)_청소년상담전화1388
	public void saveCnterPreconYngbgsDscsnTlphon1388(Map<String, String> map) throws Exception;
	
	// 센터현황T(AKA500)_시설정보
	public void saveCnterPreconFcltyInfo(Map<String, String> map) throws Exception;
	
	// 센터현황-운영시간T(AKA510)_insert
	public void insertCnterPreconOperHour(Map<String, String> map) throws Exception;
	
	// 센터현황-운영시간T(AKA510)_update
	public void updateCnterPreconOperHour(Map<String, String> map) throws Exception;
	
	// 센터현황-분소운영T(AKA520)_insert
	public void insertBrofaOper(Map<String, String> map) throws Exception;
	
	// 센터현황-분소운영T(AKA520)_update
	public void updateBrofaOper(Map<String, String> map) throws Exception;
	
	// 센터현황-1388전화운영시간T(AKA530)_insert
	public void insertCnterPrecon1388(Map<String, String> map) throws Exception;
	
	// 센터현황-1388전화운영시간T(AKA530)_update
	public void updateCnterPrecon1388(Map<String, String> map) throws Exception;
	
	// 센터현황-1388전화근무현황T(AKA540)_insert
	public void insertCnterPreconTelephone1388(Map<String, String> map) throws Exception;
	
	// 센터현황-1388전화근무현황T(AKA540)_update
	public void updateCnterPreconTelephone1388(Map<String, String> map) throws Exception;
	
	// 센터현황-1388전담요원현황T(AKA630)_insert
	public void insertCnterPreconTelephoneStaff1388(Map<String, String> map) throws Exception;
	
	// 센터현황-1388전담요원현황T(AKA630)_update
	public void updateCnterPreconTelephoneStaff1388(Map<String, String> map) throws Exception;
	
	// 센터현황-1388운영인력T(AKA550)_insert
	public void insertCnterPreconOperHnf1388(Map<String, String> map) throws Exception;
	
	// 센터현황-1388운영인력T(AKA550)_update
	public void updateCnterPreconOperHnf1388(Map<String, String> map) throws Exception;
	
	// 센터현황-청소년시설T(AKA560)
	public void saveCnterPreconYngbgsFclty(Map<String, String> map) throws Exception;
	
	// 센터현황-사용공간세부T(AKA570)_insert
	public void insertCnterPreconUseSpce(Map<String, String> map) throws Exception;
	
	// 센터현황-사용공간세부T(AKA570)_update
	public void updateCnterPreconUseSpce(Map<String, String> map) throws Exception;
	
	// 센터현황-이동형일시쉼터용차량T(AKA580)_insert
	public void insertCnterPreconMvmnSheltrCar(Map<String, String> map) throws Exception;
	
	// 센터현황-이동형일시쉼터용차량T(AKA580)_update
	public void updateCnterPreconMvmnSheltrCar(Map<String, String> map) throws Exception;
	
	// 센터현황-학교밖청소년전용공간T(AKA590)_insert
	public void insertCnterPreconOschlYngbgsPrvuseSpace(Map<String, String> map) throws Exception;
	
	// 센터현황-학교밖청소년전용공간T(AKA590)_update
	public void updateCnterPreconOschlYngbgsPrvuseSpace(Map<String, String> map) throws Exception;
	
	// 추가정보 조회
	public Map<String, Object> selectYngbgsSheltr(Map<String, String> map) throws Exception;
	
	// 운영정보 조회_AKA510_센터현황-운영시간
	public List<Map<String, String>> selectOperHour(Map<String, String> map) throws Exception;
	
	// 운영정보 조회_AKA520_센터현황-분소운영
	public List<Map<String, String>> selectBrofaOper(Map<String, String> map) throws Exception;
	
	// 청소년상담전화1388 조회
	public Map<String, Object> selectYngbgs1388(Map<String, String> map) throws Exception;
	
	// AKA530_센터현황-1388전화운영시간
	public List<Map<String, String>> selectOperHour1388(Map<String, String> map) throws Exception;
	
	// AKA540_센터현황-1388전화근무현황
	public List<Map<String, String>> selectTpriRcvr1388(Map<String, String> map) throws Exception;
	
	// AKA540_센터현황-1388전화근무현황
	public List<Map<String, String>> selectEcshgStaff1388(Map<String, String> map) throws Exception;
	
	// AKA550_센터현황-1388운영인력
	public List<Map<String, String>> selectOperHnf1388(Map<String, String> map) throws Exception;
	
	// 시설정보 조회
	public Map<String, Object> selectFcltyInfo(Map<String, String> map) throws Exception;
	
	// AKA570_센터현황-사용공간세부
	public List<Map<String, String>> selectUseSpce(Map<String, String> map) throws Exception;
	
	// AKA580_센터현황-이동형일시쉼터용차량
	public List<Map<String, String>> selectUseSpceInfo(Map<String, String> map) throws Exception;
	
	// AKA590_센터현황-학교밖청소년전용공간
	public List<Map<String, String>> selectOschlYngbgsPrvuseSpace(Map<String, String> map) throws Exception;
	
	// 청소년시설 조회
	public List<Map<String, String>> selectYngbgsFclty(Map<String, String> map) throws Exception;
	
	// 청소년시설 수정
	public void updateCnterPreconYngbgsFclty(Map<String, String> map) throws Exception;
	
	// 청소년시설 삭제
	public void deleteCnterPreconYngbgsFclty(Map<String, String> map) throws Exception;
	
	// 설치및위탁정보 조회
	public Map<String, Object> selectInstlCnsgnInfo(Map<String, String> map) throws Exception;
	
	// 통합기관신청 인서트
	public void insertAprvOrgDtl(Map<String, String> map) throws Exception;
	
	public Map<String, Object> selectExistingOrgData(Map<String, String> map) throws Exception;

	public Map<String, Object> selectAprvOrgData(Map<String, String> map) throws Exception;
	
}
