/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.taskwksprt.dclrandsgstd.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;

import isry.couns.taskwksprt.dclrandsgstd.mapper.BbserrListMapper;
import isry.couns.taskwksprt.dclrandsgstd.service.BbserrListService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;


/**
 * @파일명        : BbserrListServiceimpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Park Chan Ho
 * @작성일        : 2022. 5. 23. 
 * @수정자        : Park Chan Ho
 * @수정일        : 2022. 5. 23.
 * @수정내용      : 
 * -                
 * -                
 */
@Service
public class BbserrListServiceimpl implements BbserrListService{
	@Resource(name = "bbserrListMapper")
	private BbserrListMapper bbserrListMapper;

	/**
	 * @Method명   : selectCommonCode
	 * @param codeId
	 * @return
	 * @throws Exception
	 * @작성자     : Park Chan Ho
	 * @작성일     : 2022. 5. 23. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectCommonCode(String codeId) throws Exception {
		// TODO Auto-generated method stub
		if (codeId == null) {
			return null;
		}
		return bbserrListMapper.selectCommonCode(codeId);
	}

	/**
	 * @Method명   : getTotalCount
	 * @return
	 * @throws Exception
	 * @작성자     : Park Chan Ho
	 * @작성일     : 2022. 5. 24. 
	 * @Method설명 :
	 */
	@Override
	public int getTotalCount(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return bbserrListMapper.getTotalCount(mapParam);
	}

	/**
	 * @Method명   : selectBbsmosList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Chan Ho
	 * @작성일     : 2022. 5. 24. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectBbserrList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return bbserrListMapper.selectBbserrList(mapParam);
	}

	/**
	 * @Method명   : insertBbserrList
	 * @param mapParam
	 * @throws Exception
	 * @작성자     : Park Chan Ho
	 * @작성일     : 2022. 5. 27. 
	 * @Method설명 :
	 */
	@Override
	public int insertBbserrList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return bbserrListMapper.insertBbserrList(mapParam);
	}

	/**
	 * @Method명   : selectBbserrDetail
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Chan Ho
	 * @작성일     : 2022. 5. 28. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectBbserrDetail(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return bbserrListMapper.selectBbserrDetail(mapParam);
	}

	/**
	 * @Method명   : plusRdcntNocs
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Chan Ho
	 * @작성일     : 2022. 5. 28. 
	 * @Method설명 : 조회수 증가시키기
	 */
	@Override
	public int plusRdcntNocs(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return bbserrListMapper.plusRdcntNocs(mapParam);
	}

	/**
	 * @Method명   : saveNoticeBoardList
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Park Chan Ho
	 * @작성일     : 2022. 5. 30. 
	 * @Method설명 :
	 */
	@Override
	public void saveBbserrDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> mapReturn = new HashMap<String, Object>();
		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsList");
		
		Iterator<ParameterRow> updatedRows = dsBoardList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = dsBoardList.getDeletedRows();
				
		/*
		 * HttpSession session = request.getSession(); UserDetailsVO loginVO =
		 * userLoginService.getLoginSessionVO(request); String userId = "";
		 * 
		 * if (loginVO != null && loginVO.getId() != null &&
		 * !"".equals(loginVO.getId())) { userId = loginVO.getId(); }
		 */
		
		while (updatedRows.hasNext()) {
			Map<String, String> mapDel = updatedRows.next().toMap();
//			System.out.println("들어왔다.");
//			System.out.println(mapDel); 
			// {WRTR_ID=이지섭(jslee), FRST_REG_DT=2022-05-28, BRTH_YMD=1995, BBSCTT_TTL_NM=save(게시글제목), SXDC_SE_CD=M, RDCNT_NOCS=67, NTABRD_PSWD=1234, BBSCTT_CN(게시글내용)=<p>ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ</p>
			// , BBSCTT_ESNTAL_NO=, CNTN_IP_ADDR=127.0.0.1}
//			
			bbserrListMapper.updateBbserrDetail(mapDel);
		}

		while (deletedRows.hasNext()) { 
		  Map<String, String> mapDel = deletedRows.next().toMap();
		  //System.out.println("들어왔다.");
		  //System.out.println(mapDel);
		  bbserrListMapper.deleteBbserrDetail(mapDel);
		}
		 
		return;
	}
}
