/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.itgcms.itgBrd.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;

import isry.itgcms.itgBrd.mapper.ItgBrdCmnCmntMapper;
import isry.itgcms.itgBrd.service.ItgBrdCmnCmntService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.ScpDb;
import isry.itgcms.util.UserException;
import isry.itgcms.util.service.ArticleCheckService;
import isry.redis.service.RedisService;

/**
 * @파일명 : ItgBrdCmnCmntServiceImpl.java
 * @프로그램 설명 : - -
 * @작성자 : You Minsang
 * @작성일 : 2022. 6. 30.
 * @수정자 : You Minsang
 * @수정일 : 2022. 6. 30.
 * @수정내용 : - -
 */
@Service("itgBrdCmnCmntService")
public class ItgBrdCmnCmntServiceImpl implements ItgBrdCmnCmntService {

	@Resource(name = "itgBrdCmnCmntMapper")
	private ItgBrdCmnCmntMapper itgBrdCmnCmntMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	/**
	 * @Method명   : selectItgBrdCmntList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 7. 21.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectItgBrdCmntList(Map<String, Object> mapParam) throws Exception {
		return itgBrdCmnCmntMapper.selectItgBrdCmntList(mapParam);
	}

	/**
	 * @Method명   : saveItgBrdCmntList
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 7. 21.
	 * @Method설명 :
	 */
	@Override
	public void saveItgBrdCmntList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup dsBoardCmntList = dataRequest.getParameterGroup("dsBoardDtlCmntList");

		Iterator<ParameterRow> insertedRows = dsBoardCmntList.getInsertedRows();
		Iterator<ParameterRow> updatedRows = dsBoardCmntList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = dsBoardCmntList.getDeletedRows();

		if (insertedRows.hasNext()) {
//			ArticleCheckService articleCheckService = (ArticleCheckService) context.getBean("articleCheckService");
//			if (articleCheckService.checkDuplicateArticleRegist("saveItgBrdCmntList", request) > 0) {
//				throw new UserException("errors.preventDuplicateArticleRegist");
//			}
		}

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		while (insertedRows.hasNext()) {

			Map<String, String> mapIns = insertedRows.next().toMap();
			mapIns.put("FRST_RGTR_ID", userId);
			mapIns.put("LAST_MDFR_ID", userId);
			itgBrdCmnCmntMapper.insertItgBrdCmntList(mapIns);

		}

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			itgBrdCmnCmntMapper.updateItgBrdCmntList(mapUpd);
		}

		while (deletedRows.hasNext()) {

			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			itgBrdCmnCmntMapper.deleteItgBrdCmntList(mapDel);
		}

	}

	/**
	 * @Method명   : selectSysItgBrdCmntList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 2. 21.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectSysItgBrdCmntList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @Method명   : saveSysItgBrdCmntList
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 2. 21.
	 * @Method설명 :
	 */
	@Override
	public void saveSysItgBrdCmntList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		// TODO Auto-generated method stub

	}

}
