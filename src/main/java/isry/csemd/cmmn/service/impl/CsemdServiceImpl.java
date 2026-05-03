/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.cmmn.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import isry.csemd.cmmn.mapper.CsemdMapper;
import isry.csemd.cmmn.service.CsemdService;
import isry.itgcms.util.DateUtil;

/**
 * @파일명 : CsemdServiceImpl.java
 * @프로그램 설명 : - -
 * @작성자 : Lee.Hye.Sun
 * @작성일 : 2022. 9. 29.
 * @수정자 : Lee.Hye.Sun
 * @수정일 : 2022. 9. 29.
 * @수정내용 : - -
 */
@Service("csemdService")
public class CsemdServiceImpl implements CsemdService {

	@Resource(name = "csemdMapper")
	private CsemdMapper csemdMapper;

	/**
	 * @Method명 : selectBizYrCmb
	 * @param requestMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 10. 18.
	 * @Method설명 : 사업연도 콤보데이터 조회
	 */
	@Override
	public List<Map<String, Object>> selectBizYrCmb(Map<String, String> requestMap) throws Exception {
		return csemdMapper.selectBizYr(requestMap);
	}

	/**
	 * @Method명 : selectInstCmb
	 * @param requestMap
	 * @return
	 * @throws Exception
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 10. 14.
	 * @Method설명 :자원제공주체(교육기관) select box list
	 */
	@Override
	public List<Map<String, Object>> selectInstCmb(Map<String, String> requestMap) throws Exception {
		List<Map<String, Object>> resultMap = csemdMapper.selectInstCmb(requestMap);
		for (Map<String, Object> map : resultMap) {
			map.put("RSFR_INST_NO", map.get("INST_NO"));
			map.put("RSFR_INST_NM", map.get("INST_NM"));
		}
		return resultMap;
	}

	/**
	 * @Method명 : selectSrvcExcnBizCmb
	 * @param requestMap
	 * @return
	 * @throws Exception
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 10. 14.
	 * @Method설명 :과정(서비스실행사업) select box list
	 */
	@Override
	public List<Map<String, Object>> selectSrvcExcnBizCmb(Map<String, String> requestMap) throws Exception {
		List<Map<String, Object>> rsfrSrvcCrseCmbList = csemdMapper.selectSrvcExcnBizCmb(requestMap);

		for (Map<String, Object> map : rsfrSrvcCrseCmbList) {

			map.put("SRVC_EXCN_BIZ_NM",
					map.get("SRVC_EXCN_BIZ_NM").toString() + "("
							+ DateUtil.formatDate(map.get("SRVC_EXCN_BIZ_BGNG_YMD").toString(), ".") + "~"
							+ DateUtil.formatDate(map.get("SRVC_EXCN_BIZ_END_YMD").toString(), ".") + ")");

		}

		return rsfrSrvcCrseCmbList;
	}

	/**
	 * @Method명 : selectViolnc
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 9. 29.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectViolnc() {
		return csemdMapper.selectViolnc();
	}

	/**
	 * @Method명 : selectDiss
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 9. 29.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectDiss() {
		return csemdMapper.selectDiss();
	}

	/**
	 * @Method명 : selectLgsltn
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 9. 29.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectLgsltn() {
		return csemdMapper.selectLgsltn();
	}

	/**
	 * @Method명 : selectDscsn
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 9. 29.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectDscsn() {
		return csemdMapper.selectDscsn();
	}

	/**
	 * @Method명 : selectMaap
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 9. 29.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectMaap() {
		return csemdMapper.selectMaap();
	}

	/**
	 * @Method명 : selectEtc
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 9. 29.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectEtc() {
		return csemdMapper.selectEtc();
	}

	/**
	 * @Method명 : selectGhvrLatent
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 9. 29.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectGhvrLatent() {
		return csemdMapper.selectGhvrLatent();
	}

	/**
	 * @Method명 : selectOmen
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 9. 29.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectOmen() {
		return csemdMapper.selectOmen();
	}

	/**
	 * @Method명 : selectProbmGhvr
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 9. 29.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectProbmGhvr() {
		return csemdMapper.selectProbmGhvr();
	}

	/**
	 * @Method명 : selectdsViolncYn
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 5.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectViolncYn() {
		return csemdMapper.selectViolncYn();
	}

	/**
	 * @Method명 : selectdsSlfijr
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 5.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectSlfijr() {
		return csemdMapper.selectSlfijr();
	}

	/**
	 * @Method명 : selectSucde
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 5.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectSucde() {
		return csemdMapper.selectSucde();
	}

	/**
	 * @Method명 : selectBrhs
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 5.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectBrhs() {
		return csemdMapper.selectBrhs();
	}

	/**
	 * @Method명 : selectNowTakng
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 5.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectNowTakng() {
		return csemdMapper.selectNowTakng();
	}

	/**
	 * @Method명 : selectMece
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 5.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectMece() {
		return csemdMapper.selectMece();
	}

	/**
	 * @Method명 : selectRprsMaap
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 5.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectRprsMaap() {
		return csemdMapper.selectRprsMaap();
	}

	/**
	 * @Method명   : selectPblast
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 1. 19. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectPblast() {
		return csemdMapper.selectPblast();
	}

	/**
	 * @Method명   : selectReside
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 1. 19. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectReside() {
		return csemdMapper.selectReside();
	}

	/**
	 * @Method명   : selectFam
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 1. 19. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectFam() {
		return csemdMapper.selectFam();
	}

}
