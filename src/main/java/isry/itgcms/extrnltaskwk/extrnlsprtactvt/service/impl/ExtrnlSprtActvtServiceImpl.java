/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.itgcms.extrnltaskwk.extrnlsprtactvt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import isry.base.IsryBaseServiceImpl;
import isry.itgcm.bizcmmns.cmmns.mapper.RenuNoMapper;
import isry.itgcms.extrnltaskwk.extrnlsprtactvt.mapper.ExtrnlSprtActvtMapper;
import isry.itgcms.extrnltaskwk.extrnlsprtactvt.service.ExtrnlSprtActvtService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.DateUtil;
import isry.itgcms.util.ScpDb;
import isry.redis.service.RedisService;

/**
 * @파일명        : ExtrnlSprtActvtServiceImpl.java
 * @프로그램 설명 :
 * -
 * -
 * @작성자        : Kim.Seong.Ok
 * @작성일        : 2022. 6. 15.
 * @수정자        : Kim.Seong.Ok
 * @수정일        : 2022. 6. 15.
 * @수정내용      :
 * -
 * -
 */
@Service("extrnlSprtActvtService")
public class ExtrnlSprtActvtServiceImpl extends IsryBaseServiceImpl implements ExtrnlSprtActvtService {


	@Resource(name = "extrnlSprtActvtMapper")
	private ExtrnlSprtActvtMapper extrnlSprtActvtMapper;

	@Resource(name="renuNoMapper")
    private RenuNoMapper renuNoMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	// 테스트용 로그인 사용자 아이디
	//private String testUserId = "SUBMS01";


	/**
	 * @Method명   : getTotalCount
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 6. 15.
	 * @Method설명 :
	 */
	@Override
	public int getTotalCount(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return extrnlSprtActvtMapper.getTotalCount(mapParam);
	}



	/**
	 * @Method명   : selectExtrnlSprtActvtList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : KIM.SEONG.OK
	 * @작성일     : 2022. 6. 15.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectExtrnlSprtActvtList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return extrnlSprtActvtMapper.selectExtrnlSprtActvtList(mapParam);
	}



	/**
	 * @Method명   : selectUserInfo
	 * @param loginMap
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 6. 21.
	 * @Method설명 :
	 */
	@Override
	public Map<String, String> selectUserInfo(Map<String, Object> loginMap) throws Exception {
		// TODO Auto-generated method stub

		// 사용자 정보 > 사용자 기관번호 필요.
		Map<String, String> userInfoMap = extrnlSprtActvtMapper.selectUserInfo(loginMap);

		return userInfoMap;
	}



	/**
	 * @Method명   : selectEnfsnList
	 * @param userInfoMap
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 6. 21.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectEnfsnList(Map<String, String> userInfoMap) {
		// TODO Auto-generated method stub

		List<Map<String, String>> mapList = extrnlSprtActvtMapper.selectEnfsnList(userInfoMap);


		for (Map<String, String> map : mapList) {

			// 화면용 사용자 이름 암복호화 + 아이디
			map.put("ENFSN", map.get("FLNM_ENCPT") + "(" + map.get("USER_ID") + ")");
		}

		return mapList;
	}



	/**
	 * @Method명   : insertExtrnlSprtActvtDetail
	 * @param saveMap
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 6. 22.
	 * @Method설명 :
	 */
	@Override
	public void insertExtrnlSprtActvtDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		// TODO Auto-generated method stub

		String sWprkSqn      = "";	// 채번번호


		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, Object> loginMap = new HashMap<>();
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {

			// 임시 테스트용 하드코딩
//			loginMap.put("USER_ID", testUserId);


			loginMap.put("USER_ID", loginVO.getId());

		} else {
			throw new AppWorksException("세션정보 자료가 없읍니다.", Alert.ERROR);
		}


		// 사용자 정보 > 기관번호 추출
		Map<String, String> userInfoMap = extrnlSprtActvtMapper.selectUserInfo(loginMap);

		// 사용자 기관정보 > 담당자 추출
		Map<String, Object> userInstInfoMap = extrnlSprtActvtMapper.selectUserInstInfo(userInfoMap);


		ParameterGroup paramDmDetail = dataRequest.getParameterGroup("dsForm");


		Map<String, Object> saveMap = new HashMap<>();


		// 업무구분(해당업무 진입 메뉴 구분) > 변경될 수 있음. 정책 최종 확인 필요.
        // 청소년상담복지센터(CYS-NET : U02), 학교밖청소년지원센터(꿈드림 : U03), 청소년쉼터(청소년쉼터 행정지원시스템 : U04)
		Integer authMenuNo = 0;
		authMenuNo = request.getParameter("_AUTH_MENU_NO") == null ? 0 : Integer.parseInt(request.getParameter("_AUTH_MENU_NO"));

		// 초기 집단연계지원관리번호 채번
		Map<String, String> seqMap = new HashMap<>();
		Map<String, Object> valMap = new HashMap<>();

		seqMap.put("USER_ID",       userInfoMap.get("USER_ID"));
		seqMap.put("RENU_NO_SE_CD", "LS");					// 초기 집단연계지원관리번호 채번코드
		seqMap.put("RENU_YMD",       DateUtil.getToday());	// 현재일자

		// 채번서비스 호출
		valMap   = renuNoMapper.selectCaseMngNoRenu(seqMap);
		sWprkSqn = String.valueOf(valMap.get("RENU_NO"));	// 초기 집단연계지원관리번호 채번 발번
		log.debug("ExtrnlSprtActvtServiceImpl.insertExtrnlSprtActvtDetail.sWprkSqn=[" + sWprkSqn + "]");


		saveMap.put("GR_LINK_SPRT_MNG_NO",   sWprkSqn);													// 집단연계지원관리번호
		saveMap.put("MENU_NO", authMenuNo);             												// TASKWK_SYS_SE_CD 업무시스템구분코드 ?
		saveMap.put("LINK_SPRT_NM", paramDmDetail.getValue("LINK_SPRT_NM"));   							// 연계지원명
		saveMap.put("LINK_SPRT_YMD", paramDmDetail.getValue("LINK_SPRT_YMD")); 							// 연계지원일자
		saveMap.put("ACTVT_SE_CD", paramDmDetail.getValue("ACTVT_SE_CD"));    							// 활동구분코드
		saveMap.put("LINK_VLNTR_ID", paramDmDetail.getValue("LINK_VLNTR_ID"));   						// 연계지원자아이디
		saveMap.put("LINK_SPRT_INST_SE_CD", paramDmDetail.getValue("LINK_SPRT_INST_SE_CD")); 			// 연계지원기관구분코드
		saveMap.put("LINK_SPRT_INST_NM", paramDmDetail.getValue("LINK_SPRT_INST_NM"));    				// 연계지원기관명
		saveMap.put("LINK_RESRCE_INST_NO", paramDmDetail.getValue("LINK_RESRCE_INST_NO"));  			// 연계자원기관번호
		saveMap.put("FAM_TRPR_NO", paramDmDetail.getValue("FAM_TRPR_NO"));     							// 가족대상자번호 ?
		saveMap.put("TRGT_NOPE", paramDmDetail.getValue("TRGT_NOPE"));    								// 대상인원수 ?
		saveMap.put("LINK_SPRT_MAIN_CN", paramDmDetail.getValue("LINK_SPRT_MAIN_CN"));   				// 연계지원주요내용
		saveMap.put("DSCSN_RGN_CD", paramDmDetail.getValue("DSCSN_RGN_CD"));   							// 상담지역코드 ?
		saveMap.put("DSCSN_INST_NO", paramDmDetail.getValue("DSCSN_INST_NO"));							// 상담기관번호 ?
		saveMap.put("USER_ID", userInfoMap.get("USER_ID"));      										// 사용자아이디
		saveMap.put("REG_YMD", paramDmDetail.getValue("REG_YMD"));   									// 등록일자
		saveMap.put("LINK_INST_SE_CD", paramDmDetail.getValue("LINK_INST_SE_CD"));  					// 연계기관구분코드 ?

		// 정책확인필요.로그인 사용자 기관번호 조회 해당 기관에 담당자 아이디는 없음. 담당자 이름만 있음.
//		saveMap.put("PIC_ID", userInstInfoMap.get("PIC_ID"));          									// 담당자아이디 ?
		saveMap.put("PIC_ID", paramDmDetail.getValue("PIC_ID"));          									// 담당자아이디 ?

		saveMap.put("PIC_NM_ENCPT", userInstInfoMap.get("PIC_NM_ENCPT"));          						// 담당자명암호화 ?
		saveMap.put("LINK_VLNTR_NM_ENCPT", paramDmDetail.getValue("LINK_VLNTR_NM_ENCPT"));          	// 연계지원자명암호화 ?
		saveMap.put("LINK_SPRT_INST_CHC_TYPE_CD", paramDmDetail.getValue("LINK_SPRT_INST_CHC_TYPE_CD"));// 연계지원기관선택유형코드 ?
		saveMap.put("ATFINO", paramDmDetail.getValue("ATFINO"));          								// 첨부파일번호 ?
//		saveMap.put("DEL_YN", paramDmDetail.getValue("DEL_YN"));          								// DEL_YN ?


		saveMap.put("FRST_RGTR_ID", userInfoMap.get("USER_ID"));   										// 최초등록자아이디
//		saveMap.put("FRST_REG_DT", paramDmDetail.getValue("FRST_REG_DT"));   							// 최초등록일시
		saveMap.put("LAST_MDFR_ID", userInfoMap.get("USER_ID"));      									// 최종수정자아이디
//		saveMap.put("LAST_MDFCN_DT", paramDmDetail.getValue("LAST_MDFCN_DT"));  						// 최종수정일시


		extrnlSprtActvtMapper.insertExtrnlSprtActvtDetail(saveMap);

	}



	/**
	 * @Method명   : selectExtrnlSprtActvtDetail
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 6. 23.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectExtrnlSprtActvtDetail(DataRequest dataRequest) throws Exception {
		// TODO Auto-generated method stub

		Map<String, Object> mapParam = new HashMap<String, Object>();

		// 상세 데이터  조회조건
		ParameterGroup dmDetailParam = dataRequest.getParameterGroup("dmDetail");

		mapParam.put("GR_LINK_SPRT_MNG_NO", dmDetailParam.getValue("GR_LINK_SPRT_MNG_NO")); // 집단연계지원관리번호

		return extrnlSprtActvtMapper.selectExtrnlSprtActvtDetail(mapParam);
	}



	/**
	 * @Method명   : updateExtrnlSprtActvtDetail
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 6. 23.
	 * @Method설명 :
	 */
	@Override
	public void updateExtrnlSprtActvtDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		// TODO Auto-generated method stub

		String sUserId       = "";	// 세션정보의 유저ID


		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			// 임시 테스트용
//			sUserId = testUserId;

			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없읍니다.", Alert.ERROR);
		}


		ParameterGroup paramDmDetail = dataRequest.getParameterGroup("dsForm");

		Map<String, String> updateMap = new HashMap<>();


		updateMap.put("GR_LINK_SPRT_MNG_NO", paramDmDetail.getValue("GR_LINK_SPRT_MNG_NO"));				// 집단연계지원관리번호
		updateMap.put("TASKWK_SYS_SE_CD", paramDmDetail.getValue("TASKWK_SYS_SE_CD"));             			// 업무시스템구분코드
		updateMap.put("LINK_SPRT_NM", paramDmDetail.getValue("LINK_SPRT_NM"));               				// 연계지원명
		updateMap.put("LINK_SPRT_YMD", paramDmDetail.getValue("LINK_SPRT_YMD"));              				// 연계지원일자
		updateMap.put("ACTVT_SE_CD", paramDmDetail.getValue("ACTVT_SE_CD"));                				// 활동구분코드
		updateMap.put("LINK_VLNTR_ID", paramDmDetail.getValue("LINK_VLNTR_ID"));              				// 연계지원자아이디
		updateMap.put("LINK_SPRT_INST_SE_CD", paramDmDetail.getValue("LINK_SPRT_INST_SE_CD"));       		// 연계지원기관구분코드
		updateMap.put("LINK_SPRT_INST_NM", paramDmDetail.getValue("LINK_SPRT_INST_NM"));          			// 연계지원기관명
		updateMap.put("LINK_RESRCE_INST_NO", paramDmDetail.getValue("LINK_RESRCE_INST_NO"));        		// 연계자원기관번호
		updateMap.put("FAM_TRPR_NO", paramDmDetail.getValue("FAM_TRPR_NO"));                				// 가족대상자번호
		updateMap.put("TRGT_NOPE", paramDmDetail.getValue("TRGT_NOPE"));                  					// 대상인원수
		updateMap.put("LINK_SPRT_MAIN_CN", paramDmDetail.getValue("LINK_SPRT_MAIN_CN"));          			// 연계지원주요내용
		updateMap.put("DSCSN_RGN_CD", paramDmDetail.getValue("DSCSN_RGN_CD"));               				// 상담지역코드
		updateMap.put("DSCSN_INST_NO", paramDmDetail.getValue("DSCSN_INST_NO"));              				// 상담기관번호
		updateMap.put("USER_ID", paramDmDetail.getValue("USER_ID"));                    					// 사용자아이디
		updateMap.put("REG_YMD", paramDmDetail.getValue("REG_YMD"));                    					// 등록일자
		updateMap.put("LINK_INST_SE_CD", paramDmDetail.getValue("LINK_INST_SE_CD"));            			// 연계기관구분코드
		updateMap.put("PIC_ID", paramDmDetail.getValue("PIC_ID"));                     						// 담당자아이디
		updateMap.put("PIC_NM_ENCPT", paramDmDetail.getValue("PIC_NM_ENCPT"));               				// 담당자명암호화
		updateMap.put("LINK_VLNTR_NM_ENCPT", paramDmDetail.getValue("LINK_VLNTR_NM_ENCPT"));        		// 연계지원자명암호화
		updateMap.put("LINK_SPRT_INST_CHC_TYPE_CD", paramDmDetail.getValue("LINK_SPRT_INST_CHC_TYPE_CD")); 	// 연계지원기관선택유형코드
		updateMap.put("ATFINO", paramDmDetail.getValue("ATFINO"));                     						// 첨부파일번호
		updateMap.put("DEL_YN", paramDmDetail.getValue("DEL_YN"));                     						// 삭제여부
//		updateMap.put("FRST_RGTR_ID", paramDmDetail.getValue("FRST_RGTR_ID"));               				// 최초등록자아이디
//		updateMap.put("FRST_REG_DT", paramDmDetail.getValue("FRST_REG_DT"));                				// 최초등록일시
		updateMap.put("LAST_MDFR_ID", sUserId);               												// 최종수정자아이디
		updateMap.put("LAST_MDFCN_DT", paramDmDetail.getValue("LAST_MDFCN_DT"));              				// 최종수정일시


		extrnlSprtActvtMapper.updateExtrnlSprtActvtDetail(updateMap);

	}



	/**
	 * @Method명   : selectLinkResrceInstChcList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 6. 24.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectLinkResrceInstChcList(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return extrnlSprtActvtMapper.selectLinkResrceInstChcList(mapParam);
	}



	/**
	 * @Method명   : getLinkResrceInstChcTotalCount
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 6. 24.
	 * @Method설명 :
	 */
	@Override
	public int getLinkResrceInstChcTotalCount(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return extrnlSprtActvtMapper.getLinkResrceInstChcTotalCount(mapParam);
	}



	/**
	 * @Method명   : selectTaskwkSeCd
	 * @param requestMap
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 6. 27.
	 * @Method설명 :
	 */
	@Override
	public String selectTaskwkSeCd(Map<String, Object> requestMap) {
		// TODO Auto-generated method stub
		return extrnlSprtActvtMapper.selectTaskwkSeCd(requestMap);
	}



	/**
	 * @Method명   : deleteExtrnlSprtActvtDetail
	 * @param dataRequest
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 6. 29.
	 * @Method설명 :
	 */
	@Override
	public void deleteExtrnlSprtActvtDetail(DataRequest dataRequest) {
		// TODO Auto-generated method stub

		Map<String, Object> mapParam = new HashMap<String, Object>();

		// 삭제 집단연계지원관리번호
		ParameterGroup dmDetailParam = dataRequest.getParameterGroup("dmDetail");

		mapParam.put("GR_LINK_SPRT_MNG_NO", dmDetailParam.getValue("GR_LINK_SPRT_MNG_NO")); // 집단연계지원관리번호

		extrnlSprtActvtMapper.deleteExtrnlSprtActvtDetail(mapParam);

	}



	/**
	 * @Method명   : getSearchCount
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 6. 30.
	 * @Method설명 :
	 */
	@Override
	public int getSearchCount(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return extrnlSprtActvtMapper.getSearchCount(mapParam);
	}



	/**
	 * @Method명   : selectSearchInstInfo
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 6. 30.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectSearchInstInfo(Map<String, Object> mapParam) {

		return extrnlSprtActvtMapper.selectSearchInstInfo(mapParam);
	}


}
