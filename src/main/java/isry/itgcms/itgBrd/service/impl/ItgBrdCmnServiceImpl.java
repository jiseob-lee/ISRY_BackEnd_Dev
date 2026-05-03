/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.itgcms.itgBrd.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;

import isry.itgcms.itgBrd.mapper.ItgBrdCmnMapper;
import isry.itgcms.itgBrd.service.ItgBrdCmnService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;
import isry.itgcms.util.UserException;
import isry.itgcms.util.service.ArticleCheckService;

/**
 * @파일명 : itgBrdCmnServiceImpl.java
 * @프로그램 설명 : - -
 * @작성자 : You Minsang
 * @작성일 : 2022. 6. 30.
 * @수정자 : You Minsang
 * @수정일 : 2022. 6. 30.
 * @수정내용 : - -
 */
@Service("itgBrdCmnService")
public class ItgBrdCmnServiceImpl implements ItgBrdCmnService {

	@Resource(name = "itgBrdCmnMapper")
	private ItgBrdCmnMapper itgBrdCmnMapper;

	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;

	@Autowired
	private ApplicationContext context;

	/**
	 * @Method명 : selectInstCodeList
	 * @return
	 * @throws Exception
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 5.
	 * @Method설명 :
	 */
//	@Override
//	public List<Map<String, Object>> selectInstCodeList() throws Exception {
//
//		return itgBrdCmnMapper.selectInstCodeList();
//	}

	/**
	 * @Method명 : selectInstCode
	 * @return
	 * @throws Exception
	 * @작성자 : 이인성
	 * @작성일 : 2023. 7. 12.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectInstCode(Integer instNo) throws Exception {

		return itgBrdCmnMapper.selectInstCode(instNo);
	}


	/**
	 * @Method명 : selectItgCmnCtgtybInstList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 8.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectCtgtybInstList(Map<String, Object> mapParam) throws Exception {

		return itgBrdCmnMapper.selectCtgtybInstList(mapParam);
	}

	/**
	 * @Method명 : selectDeptCodeList
	 * @return
	 * @throws Exception
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectDeptCodeList() throws Exception {

		return itgBrdCmnMapper.selectDeptCodeList();
	}

	/**
	 * @Method명 : getTotalCount
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 4.
	 * @Method설명 :
	 */
	@Override
	public int getTotalCount(Map<String, Object> mapParam) throws Exception {

		return itgBrdCmnMapper.getTotalCount(mapParam);
	}

	/**
	 * @Method명 : selectItgNtcBrdList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 4.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectItgCmnBrdList(Map<String, Object> mapParam) throws Exception {

		List<Map<String, Object>> list = itgBrdCmnMapper.selectItgCmnBrdList(mapParam);

		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			String FRST_RGTR_NM = (String) map.get("FRST_RGTR_NM");

			if (FRST_RGTR_NM != null && !"".equals(FRST_RGTR_NM)) {
				FRST_RGTR_NM = Masking.nameMasking(FRST_RGTR_NM);
			}
			map.put("FRST_RGTR_NM", FRST_RGTR_NM);

			list.set(i, map);
		}

		return list;
	}

	/**
	 * @Method명 : selectItgCmnBrdImprtnList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 11.
	 * @Method설명 : 중요공지를 포함하는 리스트 조회
	 */
	@Override
	public List<Map<String, Object>> selectItgCmnBrdImprtnList(Map<String, Object> mapParam) throws Exception {

		List<Map<String, Object>> list = itgBrdCmnMapper.selectItgCmnBrdImprtnList(mapParam);

		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			String FRST_RGTR_NM = (String) map.get("FRST_RGTR_NM");

			if (FRST_RGTR_NM != null && !"".equals(FRST_RGTR_NM)) {
				FRST_RGTR_NM = Masking.nameMasking(FRST_RGTR_NM);
			}
			map.put("FRST_RGTR_NM", FRST_RGTR_NM);

			list.set(i, map);
		}

		return list;
	}

	/**
	 * @Method명 : selectItgBrdDtlList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 5.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectItgBrdDtlList(Map<String, Object> mapParam) throws Exception {

		String strCreateYn = (String) mapParam.get("CREATE_YN");

		if (!"Y".equals(strCreateYn)) {
			// 조회수 추가
			itgBrdCmnMapper.updateRdcntItgBrdDtlList(mapParam);
		}

		return itgBrdCmnMapper.selectItgBrdDtlList(mapParam);
	}

	/**
	 * @Method명 : selectItgBrdDtlTaskSysCdList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectItgBrdDtlTaskSysCdList(Map<String, Object> mapParam) throws Exception {

		return itgBrdCmnMapper.selectItgBrdDtlTaskSysCdList(mapParam);
	}

	/**
	 * @Method명 : selectCtgrySeCdList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 4.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectCtgrySeCdList(Map<String, Object> mapParam) throws Exception {

		return itgBrdCmnMapper.selectCtgrySeCdList(mapParam);
	}

	/**
	 * @Method명 : saveItgBrdCmnList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 4.
	 * @Method설명 :
	 */
	@Override
	public Map<String, Object> saveItgBrdCmnList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		Map<String, Object> mapReturn = new HashMap<String, Object>();

		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsBoardDtlList");

		Iterator<ParameterRow> insertedRows = dsBoardList.getInsertedRows();
		Iterator<ParameterRow> updatedRows = dsBoardList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = dsBoardList.getDeletedRows();

		if (insertedRows.hasNext()) {
			ArticleCheckService articleCheckService = (ArticleCheckService) context.getBean("articleCheckService");
			if (articleCheckService.checkDuplicateArticleRegist("saveItgBrdCmnList", request) > 0) {
				throw new UserException("errors.preventDuplicateArticleRegist");
			}
		}

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		while (insertedRows.hasNext()) {

			Map<String, String> mapIns = insertedRows.next().toMap();
			mapIns.put("FRST_RGTR_ID", userId);
			mapIns.put("LAST_MDFR_ID", userId);

			itgBrdCmnMapper.insertItgBrdCmnList(mapIns);

			// 게시글 번호 키값 셋팅
			mapReturn.put("UNITY_BBSCTT_ESNTAL_NO", mapIns.get("UNITY_BBSCTT_ESNTAL_NO"));

		}

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);

			itgBrdCmnMapper.updateItgBrdCmnList(mapUpd);

			mapReturn.put("UNITY_BBSCTT_ESNTAL_NO", mapUpd.get("UNITY_BBSCTT_ESNTAL_NO"));

		}

		while (deletedRows.hasNext()) {

			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			itgBrdCmnMapper.deleteItgBrdCmnList(mapDel);
		}

		boolean isImportDsUseTaskCd = false;

		if (dataRequest.getParameterGroupNames().contains("dsUseTaskSysSeCd")) {
			List<Map<String, String>> dsUseTaskSysSeCds = dataRequest.getParameterGroup("dsUseTaskSysSeCd")
					.getAllRowList();
			if (dsUseTaskSysSeCds.size() > 0)
				isImportDsUseTaskCd = true;
		}

		if (isImportDsUseTaskCd) {

			ParameterGroup dsUseTaskSysSeCd = dataRequest.getParameterGroup("dsUseTaskSysSeCd");
			Iterator<ParameterRow> insertedTaskSysSeCdRows = dsUseTaskSysSeCd.getInsertedRows();

			// 기존 게시글 조회권한 삭제 후 insert
			itgBrdCmnMapper.deleteItgBrdCmnTaskSysSeCdList(mapReturn);

			while (insertedTaskSysSeCdRows.hasNext()) {

				Map<String, String> mapIns = insertedTaskSysSeCdRows.next().toMap();
				mapIns.put("FRST_RGTR_ID", userId);
				mapIns.put("LAST_MDFR_ID", userId);

				// 신규 게시글 생성 컬럼 데이터 추가시
				if (mapIns.get("UNITY_BBSCTT_ESNTAL_NO").equals("")) {
					String brdSeq = mapReturn.get("UNITY_BBSCTT_ESNTAL_NO").toString();
					mapIns.put("UNITY_BBSCTT_ESNTAL_NO", brdSeq);
				}

				itgBrdCmnMapper.insertItgBrdCmnTaskSysSeCdList(mapIns);
			}
		}

		return mapReturn;
	}

	/**
	 * @Method명 : deleteItgNtcBrd
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 6.
	 * @Method설명 :
	 */
	@Override
	public void deleteItgNtcBrd(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsBoardList");

		Iterator<ParameterRow> deletedRows = dsBoardList.getDeletedRows();

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		while (deletedRows.hasNext()) {

			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			itgBrdCmnMapper.deleteItgNtcBrd(mapDel);
		}
	}

	/**
	 * @Method명 : selectAllCtgrySeCdList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectAllCtgrySeCdList(Map<String, Object> mapParam) throws Exception {

		return itgBrdCmnMapper.selectAllCtgrySeCdList(mapParam);
	}

	/**
	 * @Method명 : selectCtpvCodeList
	 * @return
	 * @throws Exception
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 11.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectCtpvCodeList() throws Exception {

		return itgBrdCmnMapper.selectCtpvCodeList();
	}

	/**
	 * @Method명 : selectSggCodeList
	 * @return
	 * @throws Exception
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 11.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectSggCodeList() throws Exception {

		return itgBrdCmnMapper.selectSggCodeList();
	}

	/**
	 * @Method명 : selectSysItgBrdDtlList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2023. 2. 21.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectSysItgBrdDtlList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @Method명 : selectSysItgBrdDtlTaskSysCdList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2023. 2. 21.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectSysItgBrdDtlTaskSysCdList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @Method명 : saveSysItgBrdCmnList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2023. 2. 21.
	 * @Method설명 :
	 */
	@Override
	public Map<String, Object> saveSysItgBrdCmnList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @Method명 : selectSysCtgrySeCdList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 4. 3.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectSysCtgrySeCdList(Map<String, Object> mapParam) throws Exception {

		return itgBrdCmnMapper.selectSysCtgtybInstList(mapParam);
	}
}
