/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.service;

import java.util.List;
import java.util.Map;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명 : OffcsSgnngMngService.java
 * @프로그램 설명 : 직인서명관리 서비스인터페이스 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 8. 11.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 8. 11.
 * @수정내용 : - -
 */
public interface OffcsSgnngMngService {

	public List<Map<String, Object>> selectOffcsSgnngList(DataRequest dataRequest) throws Exception;

	public Map<String, Object> saveOffcsSgnng(DataRequest dataRequest) throws Exception;

}
