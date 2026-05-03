/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.crtfmng.crtfissu.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : CrtfiSsuMapper.java
 * @프로그램 설명 : - -
 * @작성자 : Song.Young.Il
 * @작성일 : 2022. 8. 12.
 * @수정자 : Song.Young.Il
 * @수정일 : 2022. 8. 12.
 * @수정내용 : - -
 */
@Mapper("crtfiSsuMapper")
public interface CrtfiSsuMapper {

	/**
	 * @Method명 : insertCrtfi
	 * @param map
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 8. 18.
	 * @Method설명 :
	 */
	public void insertCrtfi(Map<String, String> map) throws Exception;

	/**
	 * @Method명 : insertCrtfiDtl
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 8. 18.
	 * @Method설명 :
	 */
	public void insertCrtfiDtl(Map<String, String> map) throws Exception;

	/**
	 * @Method명 : selectCrtfssuList
	 * @param paramMap2
	 * @return
	 * @작성자 : Park.Seong.Won
	 * @작성일 : 2022. 8. 17.
	 * @Method설명 : 증명서발급 목록(리스트)
	 */
	public List<Map<String, Object>> selectCrtfssuList(Map<String, Object> paramMap2) throws Exception;

	/**
	 * @Method명 : selectlvngCrtf
	 * @param instMap
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 8. 17.
	 * @Method설명 : 입퇴소확인서 등록
	 */
	public List<Map<String, Object>> selectlvngCrtf(Map<String, Object> instMap) throws Exception;

	/**
	 * @Method명 : selectsrvcCrtf
	 * @param instMap
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 8. 19.
	 * @Method설명 : 서비스이용확인서(기간별) 등록
	 */
	public List<Map<String, Object>> selectsrvcCrtf(Map<String, Object> instMap) throws Exception;

	/**
	 * @Method명 : selectsrvcDtlCrtf
	 * @param instMap
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 8. 19.
	 * @Method설명 : 서비스이용상세확인서(건별) 등록
	 */
	public List<Map<String, Object>> selectsrvcDtlCrtf(Map<String, Object> instMap) throws Exception;

	/**
	 * @Method명 : selectrthousCrtf
	 * @param instMap
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 8. 22.
	 * @Method설명 : 청소년쉼터 입소기간 확인서(임대주택 신청용) 등록
	 */
	public List<Map<String, Object>> selectrthousCrtf(Map<String, Object> instMap) throws Exception;

	/**
	 * @Method명 : selectpensnCrtf
	 * @param instMap
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 8. 22.
	 * @Method설명 : 청소년쉼터 입소기간 확인서(자립지원수당 신청용) 등록
	 */
	public List<Map<String, Object>> selectpensnCrtf(Map<String, Object> instMap) throws Exception;

	/**
	 * @Method명 : selectcareerCrtf
	 * @param instMap
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 8. 22.
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectcareerCrtf(Map<String, Object> instMap) throws Exception;

	/**
	 * @Method명 : selectslctnCrtf
	 * @param instMap
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 8. 23.
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectslctnCrtf(Map<String, Object> instMap) throws Exception;

	/**
	 * @Method명 : selectcrdlsCrtf
	 * @param instMap
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 8. 24.
	 * @Method설명 : 학업중단 숙려제 실시 결과서 등록
	 */
	public List<Map<String, Object>> selectcrdlsCrtf(Map<String, Object> instMap) throws Exception;

	/**
	 * @Method명 : selectfnshCrtf
	 * @param instMap
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 8. 24.
	 * @Method설명 : 입교수료확인서 등록
	 */
	public List<Map<String, Object>> selectfnshCrtf(Map<String, Object> instMap) throws Exception;

	/**
	 * @Method명 : selectListDtlsrvcCrtf
	 * @param map
	 * @return
	 * @작성자 : Park.Seong.Won
	 * @작성일 : 2022. 8. 22.
	 * @Method설명 : 서비스이용확인서(기간별) 리스트_상세조회
	 */
	public List<Map<String, Object>> selectListDtlsrvcCrtf(Map<String, Object> dmRenuMap) throws Exception;

	/**
	 * @Method명 : selectListDtlsrvcDtlCrtf
	 * @param map
	 * @return
	 * @작성자 : Park.Seong.Won
	 * @작성일 : 2022. 8. 24.
	 * @Method설명 : 서비스이용확인서(건별) 리스트_상세조회
	 */
	public List<Map<String, Object>> selectListDtlsrvcDtlCrtf(Map<String, Object> dmRenuMap) throws Exception;

	/**
	 * @Method명 : selectListlvngCrtf
	 * @param map
	 * @return
	 * @작성자 : Park.Seong.Won
	 * @작성일 : 2022. 8. 25.
	 * @Method설명 : 입퇴소 확인서 리스트_상세조회
	 */
	public List<Map<String, Object>> selectListlvngCrtf(Map<String, Object> dmRenuMap) throws Exception;

	/**
	 * @Method명 : selectListDtlhouseCrtf
	 * @param map
	 * @return
	 * @작성자 : Park.Seong.Won
	 * @작성일 : 2022. 8. 25.
	 * @Method설명 : 청소년쉼터 입소기간 확인서(임대주택 신청용) 리스트_상세조회
	 */
	public List<Map<String, Object>> selectListDtlRthouseCrtf(Map<String, Object> dmRenuMap) throws Exception;
	
	/**
	 * @Method명 : selectLastTrmnYmd
	 * @param map
	 * @return
	 * @작성자 : Park.Seong.Won
	 * @작성일 : 2022. 8. 25.
	 * @Method설명 : 자립지원수당 신청용 마지막퇴소일
	 */
	public List<Map<String, String>> selectLastTrmnYmd(Map<String, Object> dmRenuMap) throws Exception;

	/**
	 * @Method명 : selectcTtnutnEntrnc
	 * @param map
	 * @return
	 * @작성자 : Park.Seong.Won
	 * @작성일 : 2022. 8. 25.
	 * @Method설명 : 자립지원수당 신청용 연속입소 여부
	 */
	public List<Map<String, String>> selectcTtnutnEntrnc(Map<String, Object> dmRenuMap) throws Exception;

	/**
	 * @Method명 : selectListDtlpensnCrtf
	 * @param map
	 * @return
	 * @작성자 : Park.Seong.Won
	 * @작성일 : 2022. 8. 25.
	 * @Method설명 : 청소년 입소기간 확인서(자립지원수당 신청용) 리스트_상세조회
	 */
	public List<Map<String, Object>> selectListDtlpensnCrtf(Map<String, Object> dmRenuMap) throws Exception;

	/**
	 * @Method명 : selectListDtlslctnCrtf
	 * @param map
	 * @return
	 * @작성자 : Park.Seong.Won
	 * @작성일 : 2022. 8. 25.
	 * @Method설명 : 입교대상자 선정 통지서 리스트_상세조회
	 */
	public List<Map<String, Object>> selectListDtlslctnCrtf(Map<String, Object> dmRenuMap) throws Exception;

	/**
	 * @Method명 : selectListDtfnshCrtf
	 * @param map
	 * @return
	 * @작성자 : Park.Seong.Won
	 * @작성일 : 2022. 8. 25.
	 * @Method설명 : 입교, 수료 확인서 리스트_상세조회
	 */
	public List<Map<String, Object>> selectListDtfnshCrtf(Map<String, Object> dmRenuMap) throws Exception;

	/**
	 * @Method명 : selectListDtlcareerCrtf
	 * @param map
	 * @return
	 * @작성자 : Park.Seong.Won
	 * @작성일 : 2022. 8. 25.
	 * @Method설명 : 종사자 상담경력 확인서 리스트_상세조회
	 */
	public List<Map<String, Object>> selectListDtlcareerCrtf(Map<String, Object> dmRenuMap) throws Exception;

	/**
	 * @Method명 : selectListDtlcrdlsCrtf
	 * @param map
	 * @return
	 * @작성자 : Park.Seong.Won
	 * @작성일 : 2022. 8. 25.
	 * @Method설명 : 학업중단 숙려제 실시 결과서 리스트_상세조회
	 */
	public List<Map<String, Object>> selectListDtlcrdlsCrtf(Map<String, Object> dmRenuMap) throws Exception;

	/**
	 * @Method명 : selectatendDtlCrft
	 * @param instMap
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 8. 29.
	 * @Method설명 : 교육 참가자 출석 확인서(건별) 등록
	 */
	public List<Map<String, Object>> selectatendDtlCrft(Map<String, Object> instMap) throws Exception;

	/**
	 * @Method명 : selectDtlAtendCrft
	 * @param dmRenuMap
	 * @return
	 * @throws Exceptiom
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 9. 1.
	 * @Method설명 : 교육참가자 출석 확인서(건) 리스트_ 상세조회
	 */
	public List<Map<String, Object>> selectDtlAtendCrft(Map<String, Object> dmRenuMap) throws Exception;
	
	/**
	 * @Method명 : selectatendCrft
	 * @param instMap
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 8. 30.
	 * @Method설명 : 교육 참가자 출석 확인서 등록조회
	 */
	public List<Map<String, Object>> selectatendCrft(Map<String, Object> instMap) throws Exception;

	/**
	 * @Method명 : selectListAtendCrft
	 * @param dmRenuMap
	 * @return
	 * @throws Exceptiom
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 9. 1.
	 * @Method설명 : 교육참가자 출석 확인서 리스트_ 상세조회
	 */
	public List<Map<String, Object>> selectListAtendCrft(Map<String, Object> dmRenuMap) throws Exception;

	public List<Map<String, Object>> selectatendCrft2(Map<String, Object> instMap) throws Exception;

	public List<Map<String, Object>> selectListAtendCrft2(Map<String, String> map) throws Exception;
	
	/**
	 * @Method명   : selectdscsnCrtf
	 * @param dmRenuMap
	 * @return
	 * @throws Exceptiom
	 * @작성자     : Park.Seong.Won
	 * @작성일     : 2022. 9. 1. 
	 * @Method설명 : 청소년 1388 상담확인서 등록
	 */
	public List<Map<String, Object>> selectdscsnCrtf(Map<String, Object> dmRenuMap) throws Exception;
	
	/**
	 * @Method명   : selectListDtldscsnCrtf
	 * @param dmRenuMap
	 * @return
	 * @throws Exceptiom
	 * @작성자     : Park.Seong.Won
	 * @작성일     : 2022. 9. 1. 
	 * @Method설명 : 청소년 1388 상담확인서 상세조회
	 */
	public List<Map<String, Object>> selectListDtldscsnCrtf(Map<String, Object> dmRenuMap) throws Exception;

	/**
	 * @Method명   : insertCrtfOtpt
	 * @param map
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 9. 7. 
	 * @Method설명 :
	 */
	public void insertCrtfOtpt(Map<String, Object> map) throws Exception;

	/**
	 * @Method명   : selectCsemdPicList
	 * @param map
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 12. 23. 
	 * @Method설명 :
	 */
	public Map<String, String> selectCsemdPicList(Map<String, String> map);

	/**
	 * @Method명   : selectfnshCrtf2
	 * @param instMap
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 12. 28. 
	 * @Method설명 : 디딤/드림일 경우 입교 수료 확인서
	 */
	public List<Map<String, Object>> selectfnshCrtf2(Map<String, Object> instMap);

	/**
	 * @Method명   : selectListDtfnshCrtf2
	 * @param dmRenuMap
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 12. 28. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectListDtfnshCrtf2(Map<String, Object> dmRenuMap);

	/**
	 * @Method명   : updateCrtfi
	 * @param map
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 1. 2. 
	 * @Method설명 :
	 */
	public void updateCrtfi(Map<String, String> map);

	/**
	 * @Method명   : selectOffcs
	 * @param map
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 2. 7. 
	 * @Method설명 :
	 */
	public Map<String, String> selectOffcs(Map<String, String> map);

	/**
	 * @Method명   : selectOffcsPic
	 * @param map
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 3. 6. 
	 * @Method설명 :
	 */
	public Map<String, String> selectOffcsPic(Map<String, String> map);

	/**
	 * @Method명   : selectWorker
	 * @param map
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 6. 14. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectWorker(Map<String, Object> map) throws Exception;

	/**
	 * @Method명   : deleteLvngCrtf
	 * @param map
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 7. 19. 
	 * @Method설명 : 입퇴소확인서, 입소기간확인서(임대주택 신청용) 수정 시 701에 insert
	 */
	public void insertUpdatedLvngCrtfi(Map<String, String> map) throws Exception;

	/**
	 * @Method명   : updateLvngCrtfi
	 * @param map
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 7. 19. 
	 * @Method설명 : 입소기간확인서(자립지원수당 신청용) 수정 시 701에 insert
	 */
	public void insertUpdatedPensnCrtfi(Map<String, String> map) throws Exception;

	/**
	 * @Method명   : deleteLvngCrtf
	 * @param map
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 7. 19. 
	 * @Method설명 : 입퇴소확인서, 입소기간 확인서 삭제하는용도
	 */
	public void deleteLvngCrtf(Map<String, String> map) throws Exception;

}
