/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.crtrinfo.resrce.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;


/**
* @Class Name  : SrvcResrceMapper.java
* @Description : 자원정보 Mapper Class
*
* @author  : Kwon.Min.Seo
* @since   : 2022. 06. 24.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 06. 24.  Kwon.Min.Seo    최초작성
* </pre>
*/
@Mapper("srvcResrceMapper")
public interface SrvcResrceMapper {

	/**
	 * 
	 * @Method명   : selectResrceCount
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Song.Tae.Soo
	 * @작성일     : 2023. 6. 9. 
	 * @Method설명 :
	 */
	public String selectResrceCount(Map<String, Object> paramMap) throws Exception;
	
	public List<Map<String, Object>> selectResrcePagingList(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * @Method     : selectResrceList
	 * @Method설명 : 자원 목록조회
	 * @param      : paramMap
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public List<Map<String, Object>> selectResrceList(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : selectResrceDetail
	 * @Method설명 : 자원 상세조회 
	 * @param      : paramMap
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public List<Map<String, Object>> selectResrceDetail(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : selectResrceProgrmList
	 * @Method설명 : 자원 프로그램조회
	 * @param      : paramMap
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public List<Map<String, Object>> selectResrceProgrmList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectResrceProgrmHistoryList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 16. 
	 * @Method설명 : 자원 프로그램이력조회
	 */
	public List<Map<String, Object>> selectResrceProgrmHistoryList(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : selectResrceProgrmSchdlList
	 * @Method설명 : 자원 프로그램상세일정
	 * @param      : paramMap
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public List<Map<String, Object>> selectResrceProgrmSchdlList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectResrceProgrmSchdlHistoryList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 16. 
 	 * @Method설명 : 자원 프로그램상세일정이력
	 */
	public List<Map<String, Object>> selectResrceProgrmSchdlHistoryList(Map<String, String> paramMap) throws Exception;


	/**
	 * @Method     : selectResrceProgrmInstrList
	 * @Method설명 : 자원 프로그램강사
	 * @param      : paramMap
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public List<Map<String, Object>> selectResrceProgrmInstrList(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : selectResrcePicList
	 * @Method설명 : 자원 담당자조회
	 * @param      : paramMap
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public List<Map<String, Object>> selectResrcePicList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectResrcePicHistoryList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 16. 
	 * @Method설명 : 자원 담당자이력조회
	 */
	public List<Map<String, Object>> selectResrcePicHistoryList(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : selectResrceChgHstrList
	 * @Method설명 : 자원 변경이력
	 * @param      : paramMap
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public List<Map<String, Object>> selectResrceChgHstrList(Map<String, String> paramMap) throws Exception;


	/**
	 * @Method     : selectResrceDetailModChk
	 * @Method설명 : 자원 상세 변경체크 
	 * @param      : paramMap
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public String selectResrceDetailModChk(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectFileInfo
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 12. 
	 * @Method설명 : 첨부파일 정보조회
	 */
	public Map<String, Object> selectFileInfo(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : updateFileHstrRegYn
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 13. 
	 * @Method설명 : 자원이력 첨부파일 이력등록여부 수정
	 */
	public int updateFileHstrRegYn(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : updateResrceHistoryFileNo
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 13. 
	 * @Method설명 : 자원이력파일 첨부파일번호 수정
	 */
	public int updateResrceHistoryFileNo(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : insertResrceDetail
	 * @Method설명 : 자원 상세등록
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public int insertResrceDetail(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : updateResrceDetail
	 * @Method설명 : 자원 상세수정
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public int updateResrceDetail(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : deleteResrceDetail
	 * @Method설명 : 자원 상세삭제
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public int deleteResrceDetail(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : insertResrceHistory
	 * @Method설명 : 자원 이력등록
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public int insertResrceHistory(Map<String, String> paramMap) throws Exception;

	
	/**
	 * @Method     : insertResrcePic
	 * @Method설명 : 자원 담당자등록
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public int insertResrcePic(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : updateResrcePic
	 * @Method설명 : 자원 담당자수정
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public int updateResrcePic(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : deleteResrcePic
	 * @Method설명 : 자원 담당자삭제
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public int deleteResrcePic(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : insertResrcePicHistory
	 * @Method설명 : 자원 담당자이력등록
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public int insertResrcePicHistory(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : insertResrceProgrm
	 * @Method설명 : 자원 프로그램등록
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public int insertResrceProgrm(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : updateResrceProgrm
	 * @Method설명 : 자원 프로그램수정
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public int updateResrceProgrm(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : deleteResrceProgrm
	 * @Method설명 : 자원 프로그램삭제
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public int deleteResrceProgrm(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : insertResrceProgrmHistory
	 * @Method설명 : 자원 프로그램이력등록
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public int insertResrceProgrmHistory(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : selectResrceProgrmLctreSn
	 * @Method설명 : 자원 강의일련번호 발번
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public int selectResrceProgrmLctreSn(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : insertResrceProgrmSchdl
	 * @Method설명 : 자원 프로그램상세일정등록
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public int insertResrceProgrmSchdl(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : updateResrceProgrmSchdl
	 * @Method설명 : 자원 프로그램상세일정수정
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public int updateResrceProgrmSchdl(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : deleteResrceProgrmSchdl
	 * @Method설명 : 자원 프로그램상세일정삭제
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public int deleteResrceProgrmSchdl(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : insertResrceProgrmSchdlHistory
	 * @Method설명 : 자원 프로그램상세일정이력등록
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public int insertResrceProgrmSchdlHistory(Map<String, String> paramMap) throws Exception;

	
	/**
	 * @Method     : insertResrceProgrmInstr
	 * @Method설명 : 자원 프로그램강사등록
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public int insertResrceProgrmInstr(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : updateResrceProgrmInstr
	 * @Method설명 : 자원 프로그램강사수정
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public int updateResrceProgrmInstr(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : deleteResrceProgrmInstr
	 * @Method설명 : 자원 프로그램강사삭제
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public int deleteResrceProgrmInstr(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : updateAprvPrcs
	 * @Method설명 : 자원 승인(반려)처리
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public int updateAprvPrcs(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : selectBizYrCombo
	 * @Method설명 : 사업연도 콤보 데이터 조회
	 * @param      : paramMap
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Lee.Seung.Yeon
	 * @작성일     : 2022. 07. 28. 
 	 */	
	public List<Map<String, Object>> selectBizYrCombo(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : selectResrceNmCombo
	 * @Method설명 : 교육과정 콤보 데이터 조회
	 * @param      : paramMap
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Lee.Seung.Yeon
	 * @작성일     : 2022. 07. 25. 
 	 */	
	public List<Map<String, Object>> selectResrceNmCombo(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : selectInstNmCombo
	 * @Method설명 : 교육기관 콤보 데이터 조회
	 * @param      : paramMap
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Lee.Seung.Yeon
	 * @작성일     : 2022. 07. 25. 
 	 */	
	public List<Map<String, Object>> selectInstNmCombo(Map<String, String> paramMap) throws Exception;
	
	public List<Map<String, Object>> selectInstNmCombo1(Map<String, String> paramMap) throws Exception;
	
	public List<Map<String, Object>> selectInstNmCombo2(Map<String, String> paramMap) throws Exception;
	
	public List<Map<String, Object>> selectInstNmCombo3(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : selectResrcePicList
	 * @Method설명 : 교육시간표 상세 목록 조회
	 * @param      : paramMap
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Lee.Seung.Yeon
	 * @작성일     : 2022. 07. 25. 
 	 */	
	public List<Map<String, Object>> selectEduSchdlDtlList(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : selectEduHrDt
	 * @Method설명 : 교육시간표 상세 일괄등록 엑셀업로드 조회
	 * @param      : paramMap
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : 
	 * @작성일     : 2022. 07. 25. 
		 */	
	public List<Map<String, String>> selectEduHrDt(Map<String, String> map) throws Exception;
	
	/**
	 * @Method     : insertEduHrDtlRegExcelUpload
	 * @Method설명 : 교육시간표상세 일괄등록 엑셀업로드
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 07. 25. 
 	 */	
	public int insertEduHrDtlRegExcelUpload(Map<String, String> map) throws Exception;
	
	/**
	 * @Method     : insertEduHrDtlRegInStrExcelUpload
	 * @Method설명 : 교육시간표상세 일괄등록 엑셀업로드(강사)
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 07. 25. 
 	 */	
	public int insertEduHrDtlRegInStrExcelUpload(Map<String, String> map) throws Exception;
	
	/**
	 * @param paramMap 
	 * @Method     : deleteAllDel
	 * @Method설명 : 교육시간표상세 일괄등록 전체삭제
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 07. 25. 
 	 */	
	public int deleteAllDel(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * @param paramMap 
	 * @Method     : deleteInStrAllDel
	 * @Method설명 : 교육시간표상세 일괄등록 전체삭제_강사
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 07. 25. 
 	 */	
	public int deleteInStrAllDel(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * @Method     : insertResRceProgrm
	 * @Method설명 : 자원프로그램
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 07. 25. 
 	 */	
	public int insertResRceProgrm(Map<String, String> map) throws Exception;
	
	/**
	 * @Method     : insertResRceProgrmHstr
	 * @Method설명 : 자원프로그램이력
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 07. 25. 
 	 */	
	public int insertResRceProgrmHstr(Map<String, String> map) throws Exception;
	
	/**
	 * @Method     : insertResRceProgrmDtlSchdl
	 * @Method설명 : 자원프로그램상세일정
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 07. 25. 
 	 */	
	public int insertResRceProgrmDtlSchdl(Map<String, String> map) throws Exception;
	
	/**
	 * @Method     : insertResRceProgrmDtlSchdlHstr
	 * @Method설명 : 자원프로그램상세일정이력
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 07. 25. 
 	 */	
	public int insertResRceProgrmDtlSchdlHstr(Map<String, String> map) throws Exception;
	
	/**
	 * @Method     : insertResRceProgrmExcnHr
	 * @Method설명 : 자원프로그램실행시간
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 07. 25. 
 	 */	
	public int insertResRceProgrmExcnHr(Map<String, String> map) throws Exception;
	
	/**
	 * @Method     : insertResRceProgrmInstr
	 * @Method설명 : 자원프로그램강사
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 07. 25. 
 	 */	
	public int insertResRceProgrmInstr(Map<String, String> map) throws Exception;
	
	/**
	 * @Method     : updateResRceBass
	 * @Method설명 : 자원기본
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 07. 25. 
 	 */	
	public int updateResRceBass(Map<String, String> map) throws Exception;
	
	// 승인상태구분코드 가져오기
	public Map<String, Object> selectAprvSttsSeCd(Map<String, String> map) throws Exception;
		
	/**
	 * @Method     : selectResrcePicList
	 * @Method설명 : 스케쥴적용 자원프로그램 조회
	 * @param      : paramMap
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 07. 26. 
 	 */	
	public List<Map<String, Object>> selectEduSchdlResourceList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : selectEduSchdlResourceDtlList
	 * @Method설명 : 스케쥴적용 자원프로그램상세 조회
	 * @param      : paramMap
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 07. 26. 
 	 */	
	public List<Map<String, Object>> selectEduSchdlResourceDtlList(Map<String, String> paramMap) throws Exception;


	// 프로그램번호 가져오기
	public Map<String, Object> selectProgrmNo(Map<String, String> map) throws Exception;
	
	/**
	 * @Method     : selectEduSchdlExcvHrList
	 * @Method설명 : 스케쥴적용 실행시간 조회
	 * @param      : paramMap
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 07. 26. 
 	 */	
	public List<Map<String, Object>> selectEduSchdlExcvHrList(Map<String, String> paramMap) throws Exception;
	
	// 프로그램번호2 가져오기
	public Map<String, Object> selectProgrmNo2(Map<String, String> map) throws Exception;
	
	/**
	 * @Method     : insertExcvHr
	 * @Method설명 : 자원프로그램실행시간등록
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 07. 26.
 	 */	
	public int insertExcvHr(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : selectIntrList
	 * @Method설명 : 스케쥴적용 강사 조회
	 * @param      : paramMap
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 07. 26. 
 	 */	
	public List<Map<String, Object>> selectIntrList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : updateExcvHr
	 * @Method설명 : 자원프로그램실행시간 수정
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 07. 27. 
 	 */	
	public int updateExcvHr(Map<String, String> map) throws Exception;
	
	/**
	 * @Method     : updateExcvHr
	 * @Method설명 : 자원프로그램실행시간 강의시간 수정
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 07. 27. 
 	 */	
	public int updateLcreHr(Map<String, String> map) throws Exception;
	
	
	// 강의일련번호 320
	public Map<String, Object> selectLctreSn(Map<String, String> map) throws Exception;
	
	/**
	 * @Method     : selectInstr
	 * @Method설명 : 강사조회
	 * @param      : paramMap
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Choi.Doo.Il.
	 * @작성일     : 2022. 07. 28. 
		 */	
	public List<Map<String, String>> selectInstr(Map<String, String> map) throws Exception;
	
	// SDA300 삭제
	public int updateAplcnResrceProgrm(Map<String, String> map) throws Exception;
	
	// SDA340 삭제
	public int updateAplcnResrceProgrmSchdl(Map<String, String> map) throws Exception;
	
	// SDA342 삭제
	public int updateAplcnResrceProgrmInstr(Map<String, String> map) throws Exception;
	
	// SDA320 삭제
	public int updateAplcnExcvHr(Map<String, String> map) throws Exception;
	
	// SDA301 삭제
	public int updateAplcnResrceProgrmHstr(Map<String, String> map) throws Exception;
		
	// SDA341 삭제
	public int updateAplcnResrceProgrmSchdlHstr(Map<String, String> map) throws Exception;
	
	/**
	 * @Method     : selectEntsc
	 * @Method설명 : 입교일정 조회
	 * @param      : paramMap
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Choi.Doo.Il
	 * @작성일     : 2022. 08. 11.
 	 */	
	public List<Map<String, Object>> selectEntsc(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : insertEntsc
	 * @Method설명  : 입교일정 등록
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Choi.Doo.Il
	 * @작성일     : 2022. 08. 11.
 	 */	
	public int insertEntsc(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : updateEntsc
	 * @Method설명  : 입교일정 수정
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Choi.Doo.Il
	 * @작성일     : 2022. 08. 11.
 	 */	
	public int updateEntsc(Map<String, String> paramMap) throws Exception;
		
	public List<Map<String, Object>> selectCommonCodeUnit(Map<String, Object> map) throws Exception;

	//자원명 중복조회
	public int selectResrceNmChk(String sNm) throws Exception;	
	//자원제공주체 확인
	public int selectRsfrMbyInstChk(Integer iInstNo) throws Exception;	
	
	// 교육과정확인 조회
	public Map<String, Object> selectEduCrseChk(Map<String, String> paramMap) throws Exception;
	public Map<String, Object> selectEduCrseChk1(Map<String, String> paramMap) throws Exception;
	public Map<String, Object> selectEduCrseChk2(Map<String, String> paramMap) throws Exception;

	/**
	 * @param userInfoMap 
	 * @Method명   : selectInstNmCombo4
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 2. 27. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectInstNmCombo4(Map<String, String> userInfoMap) throws Exception;
	
	/**
	 * 파일 이력
	 * */
	public List<Map<String, Object>> selectFilesChgHstrList(Map<String, Object> userInfoMap) throws Exception;
	
	/**
	 * 업무 별 자원제공 주체로 선언되어있는 기관 리스트 가져오기.
	 * @Method명   : getRsfrUntTaskwkInstList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Taesoo Song
	 * @작성일     : 2023. 4. 19. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> getRsfrUntTaskwkInstList(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * 기관에 속한 종사자 목록 가져오기
	 * @Method명   : getRsfrInstPicMemberList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Taesoo Song
	 * @작성일     : 2023. 4. 19. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> getRsfrInstPicMemberList(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method명   : selectPK
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Sang.Hoon
	 * @작성일     : 2023. 5. 19. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectPK(Map<String, String> map) throws Exception;
}
