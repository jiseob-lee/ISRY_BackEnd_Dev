/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.stdnt.service.impl;

import java.util.ArrayList;
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
import isry.drmgs.stdnt.mapper.YouthLifeRecodeDetailMapper;
import isry.drmgs.stdnt.mapper.YouthLifeRecodeMapper;
import isry.drmgs.stdnt.service.YouthLifeRecodeService;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명        : YouthLifeRecodeServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kang.Hwa.Young
 * @작성일        : 2022. 7. 14. 
 * @수정자        : Kang.Hwa.Young
 * @수정일        : 2022. 7. 14.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("youthLifeRecodeService")
public class YouthLifeRecodeServiceImpl extends IsryBaseServiceImpl implements YouthLifeRecodeService {
	
	//private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	//@Resource(name="trprInqService")
	//private TrprInqService trprInqService;
	
	@Resource(name="youthLifeRecodeMapper")
	private YouthLifeRecodeMapper youthLifeRecodeMapper;
	
	@Resource(name="youthLifeRecodeDetailMapper")
	private YouthLifeRecodeDetailMapper youthLifeRecodeDetailMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;
	
	public List<Map<String, Object>> selectYouthLifeRecodeMainList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtnMap = new ArrayList<Map<String,Object>>();
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");

		String trprNm    = null;

		if (parameterGroup != null) {
			trprNm = parameterGroup.getValue("TRPR_NM");	//내담자성명
		}

		Map<String, String> paramMap = parameterGroup.getSingleValueMap();

		HttpSession session   = request.getSession();
        UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
        
        /* 20230126_강화영_권한 적용_시작 */
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) -> {
			paramMap2.put(StrKey, StrValue);
		}); /* 형변환 */

		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/* 20230126_강화영_권한 적용_종료 */
		
		rtnMap = youthLifeRecodeMapper.selectYouthLifeRecodeMainList(paramMap2);

		Map<String, Object> map = new HashMap<>();
		for(int i=0; i<rtnMap.toArray().length; i++) {
			map = rtnMap.get(i);
			if (map.get("FRST_REG_DT") != null && !"".equals(map.get("FRST_REG_DT"))) {
				String frstRegDt = map.get("FRST_REG_DT").toString();
				frstRegDt = frstRegDt.substring(0, 4) + "-" + frstRegDt.substring(4, 6) + "-" + frstRegDt.substring(6, 8);
				map.put("FRST_REG_DT", frstRegDt);
			}
			rtnMap.set(i, map);
		}

		return rtnMap;
	}	
	
	public int deleteYouthLifeRecode(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String sUserId = ""; // 세션정보의 유저ID

		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}

		ParameterGroup infoParam = dataRequest.getParameterGroup("dmYngbsInfo");
		Map<String, String> dmYngbsInfoMap = infoParam.getSingleValueMap();
		dmYngbsInfoMap.put("LAST_MDFR_ID", sUserId);

		// 1. AKA300(생활기록부) DEL_YN = 'Y'처리
		youthLifeRecodeMapper.deleteYouthLifeRecode(dmYngbsInfoMap);

		// 2. AKA310 ~ AKA380 USE_YN = 'N' 처리
		youthLifeRecodeDetailMapper.deleteRead		  (dmYngbsInfoMap); // AKA310(독서활동 현황)
		youthLifeRecodeDetailMapper.deleteSchulwInfo  (dmYngbsInfoMap); // AKA320(학업노력 현황)
		youthLifeRecodeDetailMapper.deleteSvcb		  (dmYngbsInfoMap); // AKA330(봉사활동 현황)
		youthLifeRecodeDetailMapper.DeleteArprCareer  (dmYngbsInfoMap); // AKA340(수상경력)
		youthLifeRecodeDetailMapper.deleteCertiInfo	  (dmYngbsInfoMap); // AKA350(자격증현황)
		youthLifeRecodeDetailMapper.deleteCreativeInfo(dmYngbsInfoMap); // AKA360(창의적 체험활동상황)		
		youthLifeRecodeDetailMapper.DeleteAtncSittn   (dmYngbsInfoMap); // AKA370(출석현황)
		youthLifeRecodeDetailMapper.deleteOpnn		  (dmYngbsInfoMap); // AKA380(종합의견)

		return 0;
	}

	public List<Map<String, Object>> selectOtptList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> rtnMap = new ArrayList<Map<String,Object>>();
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");

		Map<String, String> paramMap = parameterGroup.getSingleValueMap();

		// 2022.09.23최두일 조회권한
		HttpSession session   = request.getSession();
        UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
        
        return youthLifeRecodeMapper.selectOtptList(paramMap);
	}
}
