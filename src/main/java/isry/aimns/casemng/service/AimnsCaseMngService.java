/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.aimns.casemng.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명 : AimnsCaseMngService.java
 * @프로그램 설명 : 사례관리>실행&종결 화면의 고유항목 서비스 인터페이스 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 10. 12.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 10. 12.
 * @수정내용 : - -
 */
public interface AimnsCaseMngService {

	public void selectEduComplSchdl(DataRequest dataRequest) throws Exception;

	public void saveEduComplSchdl(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명 : selectPvsnResrceNm
	 * @param request
	 * @param dataRequest
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2023. 6. 7.
	 * @Method설명 :
	 */
	public Map<String, Object> selectPvsnResrceNm(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명 : saveEduCmplSchdlMng
	 * @param request
	 * @param dataRequest
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2023. 6. 7.
	 * @Method설명 :
	 */
	public void saveEduCmplSchdlMng(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명 : selectCaseinqPagingList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 8. 7.
	 * @Method설명 : 사례목록
	 */
	public Map<String, Object> selectCaseinqPagingList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;

}
