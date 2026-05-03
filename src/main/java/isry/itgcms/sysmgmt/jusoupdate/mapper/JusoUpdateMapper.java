/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부 및 위기 청소년 통합 지원 시스템 관리팀의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/

package isry.itgcms.sysmgmt.jusoupdate.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 
 * @파일명        : JusoUpdateMapper.java
 * @프로그램 설명 : 도로명 주소 업데이트
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 29. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 29.
 * @수정내용      : 
 * -                
 * -
 */
@Mapper("jusoUpdateMapper")
public interface JusoUpdateMapper {
	
	// 데이터 수신 결과 기록
	public void jusoSetJusoUpdateResult(Map<String, String> map) throws Exception;
	
	// 데이터 처리 결과 기록
	public void jusoSetJusoUpdateDataResult(Map<String, String> map) throws Exception;
	
	// 관련 지번
	public void insertSccoMvmn(Map<String, String> map) throws Exception;
	public void updateSccoMvmn(Map<String, String> map) throws Exception;
	public void deleteSccoMvmn(Map<String, String> map) throws Exception;
	
	// 건물
	public void insertSpbdBuld(Map<String, String> map) throws Exception;
	public void updateSpbdBuld(Map<String, String> map) throws Exception;
	public void deleteSpbdBuld(Map<String, String> map) throws Exception;
	
	// 도로명 코드
	public void deleteSprdStret(Map<String, String> map) throws Exception;
	public void mergeSprdStret(Map<String, String> map) throws Exception;

	// 초기화 1
	public void truncateSccoMvmn() throws Exception;
	public void truncateSpbdBuld() throws Exception;
	public void truncateSprdStret() throws Exception;
	
	// 초기화 2
	public void copySccoMvmn() throws Exception;
	public void copySpbdBuld() throws Exception;
	public void copySprdStret() throws Exception;


	// 테이블 비우기
	public void truncateSymAddr2I() throws Exception;
	// 조인 데이터 생성
	public void initSymAddr2I() throws Exception;
	// 테이블 비우기
	public void truncateSymAddrI() throws Exception;
	// 조인 데이터 입력
	public void insertSymAddrI() throws Exception;
	// 삭제할 데이터 선택
	public List<Map<String, String>> selectDeleteSymAddrIData() throws Exception;
	// 차이분 삭제
	public void deleteSymAddrIData(Map<String, String> map) throws Exception;
	
	// 변경분 데이터 적용
	public void truncateBuilding() throws Exception;
	public void truncateJibeon() throws Exception;
	public void truncateDoroCode() throws Exception;
	public void copyBuilding3() throws Exception;
	public void copyJibeon3() throws Exception;
	public void copyDoroCode3() throws Exception;

	
	// 데이터 수신 결과 기록 조회
	public Map<String, String> jusoGetJusoUpdateResult(Map<String, String> map) throws Exception;
	public List<Map<String, String>> jusoGetJusoUpdateResults() throws Exception;
	
	// 인덱스 삭제
	public void deleteIdx1() throws Exception;
	public void deleteIdx2() throws Exception;
	public void deleteIdx3() throws Exception;
	public void deleteIdx4() throws Exception;
	public void deleteIdx5() throws Exception;
	public void deleteIdx6() throws Exception;
	
	// 인덱스 생성
	public void createIdx1App() throws Exception;
	public void createIdx2App() throws Exception;
	public void createIdx3App() throws Exception;
	public void createIdx4App() throws Exception;
	public void createIdx5App() throws Exception;
	public void createIdx6App() throws Exception;
	
	// 지역 정보 개발원으로 부터 업데이트 실행 회수 기록
	public void jusoSetUpdateCount(String currentDate) throws Exception;
	
	public void jusoProcessSetEmdTruncate() throws Exception;
	public void jusoProcessSetEmd() throws Exception;
	public void jusoProcessSetEmdRegional(String region) throws Exception;
	
	// 첨부파일 개발서버 파일 경로 업데이트
	public void updateAttachment() throws Exception;

	// PRIMARY KEY 인덱스 생성
	public void createIndexBuilding() throws Exception;
	public void createIndexJibeon() throws Exception;
	public void createIndexBuilding3() throws Exception;
	public void createIndexJibeon3() throws Exception;

	// 코멘트
	public void commentSAD600_0() throws Exception;
	public void commentSAD600_1() throws Exception;
	public void commentSAD600_2() throws Exception;
	public void commentSAD600_3() throws Exception;
	public void commentSAD600_4() throws Exception;
	public void commentSAD600_5() throws Exception;
	public void commentSAD600_6() throws Exception;
	public void commentSAD600_7() throws Exception;
	public void commentSAD600_8() throws Exception;
	public void commentSAD600_9() throws Exception;
	public void commentSAD600_10() throws Exception;
	public void commentSAD600_11() throws Exception;
	public void commentSAD600_12() throws Exception;
	public void commentSAD600_13() throws Exception;
	public void commentSAD600_14() throws Exception;
	public void commentSAD600_15() throws Exception;
	public void commentSAD600_16() throws Exception;
	public void commentSAD600_17() throws Exception;
	public void commentSAD600_18() throws Exception;
	public void commentSAD600_19() throws Exception;
	
	public void commentSAD200_0() throws Exception;
	public void commentSAD200_1() throws Exception;
	public void commentSAD200_2() throws Exception;
	public void commentSAD200_3() throws Exception;
	public void commentSAD200_4() throws Exception;
	public void commentSAD200_5() throws Exception;
	public void commentSAD200_6() throws Exception;
	public void commentSAD200_7() throws Exception;
	public void commentSAD200_8() throws Exception;
	public void commentSAD200_9() throws Exception;
	public void commentSAD200_10() throws Exception;
	public void commentSAD200_11() throws Exception;
	public void commentSAD200_12() throws Exception;
	public void commentSAD200_13() throws Exception;
	public void commentSAD200_14() throws Exception;
	public void commentSAD200_15() throws Exception;
	public void commentSAD200_16() throws Exception;
	public void commentSAD200_17() throws Exception;
	public void commentSAD200_18() throws Exception;
	public void commentSAD200_19() throws Exception;
	
	public void commentSAD500_0() throws Exception;
	public void commentSAD500_1() throws Exception;
	public void commentSAD500_2() throws Exception;
	public void commentSAD500_3() throws Exception;
	public void commentSAD500_4() throws Exception;
	public void commentSAD500_5() throws Exception;
	public void commentSAD500_6() throws Exception;
	public void commentSAD500_7() throws Exception;
	public void commentSAD500_8() throws Exception;
	public void commentSAD500_9() throws Exception;
	public void commentSAD500_10() throws Exception;
	public void commentSAD500_11() throws Exception;
	public void commentSAD500_12() throws Exception;
	public void commentSAD500_13() throws Exception;
	public void commentSAD500_14() throws Exception;
	public void commentSAD500_15() throws Exception;
	public void commentSAD500_16() throws Exception;
	public void commentSAD500_17() throws Exception;
	public void commentSAD500_18() throws Exception;
	public void commentSAD500_19() throws Exception;
	public void commentSAD500_20() throws Exception;
	public void commentSAD500_21() throws Exception;
	public void commentSAD500_22() throws Exception;
	public void commentSAD500_23() throws Exception;
	public void commentSAD500_24() throws Exception;
	public void commentSAD500_25() throws Exception;
	public void commentSAD500_26() throws Exception;
	public void commentSAD500_27() throws Exception;
	public void commentSAD500_28() throws Exception;
	public void commentSAD500_29() throws Exception;
	public void commentSAD500_30() throws Exception;
	public void commentSAD500_31() throws Exception;
	public void commentSAD500_32() throws Exception;
	public void commentSAD500_33() throws Exception;
	public void commentSAD500_34() throws Exception;
	public void commentSAD500_35() throws Exception;
	public void commentSAD500_36() throws Exception;
	
	public void commentSAD100_0() throws Exception;
	public void commentSAD100_1() throws Exception;
	public void commentSAD100_2() throws Exception;
	public void commentSAD100_3() throws Exception;
	public void commentSAD100_4() throws Exception;
	public void commentSAD100_5() throws Exception;
	public void commentSAD100_6() throws Exception;
	public void commentSAD100_7() throws Exception;
	public void commentSAD100_8() throws Exception;
	public void commentSAD100_9() throws Exception;
	public void commentSAD100_10() throws Exception;
	public void commentSAD100_11() throws Exception;
	public void commentSAD100_12() throws Exception;
	public void commentSAD100_13() throws Exception;
	public void commentSAD100_14() throws Exception;
	public void commentSAD100_15() throws Exception;
	public void commentSAD100_16() throws Exception;
	public void commentSAD100_17() throws Exception;
	public void commentSAD100_18() throws Exception;
	public void commentSAD100_19() throws Exception;
	public void commentSAD100_20() throws Exception;
	public void commentSAD100_21() throws Exception;
	public void commentSAD100_22() throws Exception;
	public void commentSAD100_23() throws Exception;
	public void commentSAD100_24() throws Exception;
	public void commentSAD100_25() throws Exception;
	public void commentSAD100_26() throws Exception;
	public void commentSAD100_27() throws Exception;
	public void commentSAD100_28() throws Exception;
	public void commentSAD100_29() throws Exception;
	public void commentSAD100_30() throws Exception;
	public void commentSAD100_31() throws Exception;
	public void commentSAD100_32() throws Exception;
	public void commentSAD100_33() throws Exception;
	public void commentSAD100_34() throws Exception;
	public void commentSAD100_35() throws Exception;
	public void commentSAD100_36() throws Exception;
	
}
