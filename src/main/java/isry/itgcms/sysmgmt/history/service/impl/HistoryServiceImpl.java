/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.itgcms.sysmgmt.history.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import isry.base.IsryBaseServiceImpl;
import isry.itgcms.sysmgmt.history.mapper.HistoryMapper;
import isry.itgcms.sysmgmt.history.service.HistoryService;
import isry.itgcms.util.Formatter;
import isry.itgcms.util.Masking;

/**
 * @파일명 : HistoryServiceImpl.java
 * @프로그램 설명 : 이력 조회 및 상세조회 ServiceImpl
 * @작성자 : Park.Kyu.Young
 * @작성일 : 2022. 4. 7.
 * @수정자 : Park.Kyu.Young
 * @수정일 : 2022. 4. 7.
 * @수정내용 : - -
 */
@Service("historyService")
public class HistoryServiceImpl extends IsryBaseServiceImpl implements HistoryService {

	@Resource(name = "historyMapper")
	private HistoryMapper historyMapper;

	/**
	 * @Method명 : selectProgramHistoryTotalCount
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 5. 2.
	 * @Method설명 : 프로그램이력 totalCount 조회
	 */
	@Override
	public Integer selectProgramHistoryTotalCount(Map<String, Object> map) throws Exception {

		return historyMapper.selectProgramHistoryTotalCount(map);
	}

	/**
	 * @Method명 : selectProgramHistory
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 5. 2.
	 * @Method설명 : 프로그램이력 조회
	 */
	@Override
	public List<Map<String, Object>> selectProgramHistory(Map<String, Object> map) throws Exception {

		return historyMapper.selectProgramHistory(map);
	}

	/**
	 * @Method명 : selectMenuHistoryTotalCount
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 5. 2.
	 * @Method설명 :메뉴이력 totalCount 조회
	 */
	@Override
	public Integer selectMenuHistoryTotalCount(Map<String, Object> map) throws Exception {

		return historyMapper.selectMenuHistoryTotalCount(map);
	}

	/**
	 * @Method명 : selectMenuHistory
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 5. 2.
	 * @Method설명 : 메뉴이력 조회
	 */
	@Override
	public List<Map<String, Object>> selectMenuHistory(Map<String, Object> map) throws Exception {

		return historyMapper.selectMenuHistory(map);
	}

	/**
	 * @Method명 : selectDeptHistoryTotalCount
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 5. 2.
	 * @Method설명 : 부서이력 totalCount 조회
	 */
	@Override
	public Integer selectDeptHistoryTotalCount(Map<String, Object> map) throws Exception {

		return historyMapper.selectDeptHistoryTotalCount(map);
	}

	/**
	 * @Method명 : selectDeptHistory
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 5. 2.
	 * @Method설명 :부서이력 조회
	 */
	@Override
	public List<Map<String, Object>> selectDeptHistory(Map<String, Object> map) throws Exception {

		List<Map<String, Object>> list1 = historyMapper.selectDeptHistory(map);
		List<Map<String, Object>> list2 = new ArrayList<>();


		if (list1 != null) {
			for (int i=0; i < list1.size(); i++) {
				Map<String, Object> map1 = list1.get(i);
				map1.put("DEPT_TELNO", Formatter.phoneFormat(String.valueOf(map1.get("DEPT_TELNO")), 1));
				map1.put("DEPT_FXNO", Formatter.phoneFormat(String.valueOf(map1.get("DEPT_FXNO")), 1));
				list2.add(map1);
			}
		}

		return list2;
	}

	/**
	 * @Method명 : selectInstituteHistoryTotalCount
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 5. 2.
	 * @Method설명 : 통합기관이력 totalCount 조회
	 */
	@Override
	public Integer selectInstituteHistoryTotalCount(Map<String, Object> map) throws Exception {

		return historyMapper.selectInstituteHistoryTotalCount(map);
	}

	/**
	 * @Method명 : selectInstituteHistory
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 5. 2.
	 * @Method설명 : 통합기관이력 조회
	 */
	@Override
	public List<Map<String, Object>> selectInstituteHistory(Map<String, Object> map) throws Exception {

		List<Map<String, Object>> list1 = historyMapper.selectInstituteHistory(map);
		List<Map<String, Object>> list2 = new ArrayList<>();

		if (list1 != null) {


			for (int i=0; i < list1.size(); i++) {
				Map<String, Object> map1 = list1.get(i);
				map1.put("RPRSV_NM", (String)map1.get("RPRSV_NM_ENCPT"));
				map1.put("RPRSV_NM_MASKING", Masking.nameMasking((String)map1.get("RPRSV_NM")));
				map1.put("PIC_NM", (String)map1.get("PIC_NM_ENCPT"));
				map1.put("PIC_MBL_TELNO", (String)map1.get("PIC_MBL_TELNO_ENCPT"));
				map1.put("PIC_EML_ADDR", (String)map1.get("PIC_EML_ADDR_ENCPT"));
				list2.add(map1);
			}
		}

		return list2;
	}

	/**
	 * @Method명 : selectOrg
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Kyu.Young
	 * @작성일 : 2022. 5. 2.
	 * @Method설명 : 상위기관 조회
	 */
	@Override
	public List<Map<String, Object>> selectOrg() throws Exception {

		return historyMapper.selectOrg();
	}

	/**
	 * @Method명   : selectRightHistoryCount
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Ji.Seob
	 * @작성일     : 2022. 8. 24.
	 * @Method설명 :
	 */
	@Override
	public Integer selectRightsHistoryCount(Map<String, Object> map) throws Exception {
		return historyMapper.selectRightsHistoryCount(map);
	}

	/**
	 * @Method명   : selectRightHistory
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Ji.Seob
	 * @작성일     : 2022. 8. 24.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectRightsHistory(Map<String, Object> map) throws Exception {
		return historyMapper.selectRightsHistory(map);
	}

	/**
	 * @Method명   : selectRightsHistoryGroupCount
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Ji.Seob
	 * @작성일     : 2022. 8. 29.
	 * @Method설명 :
	 */
	@Override
	public Integer selectRightsHistoryGroupCount(Map<String, Object> map) throws Exception {
		return historyMapper.selectRightsHistoryGroupCount(map);
	}

	/**
	 * @Method명   : selectRightsHistoryGroup
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Ji.Seob
	 * @작성일     : 2022. 8. 29.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectRightsHistoryGroup(Map<String, Object> map) throws Exception {
		return historyMapper.selectRightsHistoryGroup(map);
	}

	/**
	 * @Method명   : selectRightsHistoryUserMenuCount
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Ji.Seob
	 * @작성일     : 2022. 8. 29.
	 * @Method설명 :
	 */
	@Override
	public Integer selectRightsHistoryUserMenuCount(Map<String, Object> map) throws Exception {

		map.put("USER_NM_ENCPT", (String)map.get("USER_NM"));

		return historyMapper.selectRightsHistoryUserMenuCount(map);
	}

	/**
	 * @Method명   : selectRightsHistoryUserMenu
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Ji.Seob
	 * @작성일     : 2022. 8. 29.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectRightsHistoryUserMenu(Map<String, Object> map) throws Exception {

		map.put("USER_NM_ENCPT", (String)map.get("USER_NM"));
		// 2023.05.03 (Myeong.Jae.Cheol) : 작업자 칼럼 그리드에 표시되는게 최종수정자아이디로 되어 있는데, 암호화할 필요 없음!
//		map.put("WORKER", scpDb.scpEncB64((String)map.get("WORKER"))); // 작업자

		List<Map<String, Object>> list1 = historyMapper.selectRightsHistoryUserMenu(map);
		List<Map<String, Object>> list2 = new ArrayList<>();

		if (list1 != null) {
			for (int i=0; i < list1.size(); i++) {
				Map<String, Object> map1 = list1.get(i);

				map1.put("USER_NM", (String)map1.get("USER_NM"));
				map1.put("USER_NM_MASKING", Masking.nameMasking((String)map1.get("USER_NM")));
				list2.add(map1);
			}
		}

		return list2;
	}

}
