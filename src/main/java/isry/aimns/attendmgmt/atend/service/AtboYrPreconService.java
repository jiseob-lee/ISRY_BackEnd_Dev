/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.aimns.attendmgmt.atend.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명 : AtboYrPreconService.java
 * @프로그램 설명 : 출석부 현황 서비스 인터페이스
 * @작성자 : Park.Seong.Won
 * @작성일 : 2022. 7. 28.
 * @수정자 : Park.Seong.Won
 * @수정일 : 2022. 7. 28.
 * @수정내용 : - -
 */
public interface AtboYrPreconService {

	public List<Map<String, Object>> selectAtboPcList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
}
