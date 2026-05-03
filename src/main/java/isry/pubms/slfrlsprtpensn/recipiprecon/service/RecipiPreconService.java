/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.pubms.slfrlsprtpensn.recipiprecon.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : RecipiPreconService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Baek.Gyu.Ha
 * @작성일        : 2023.07.24
 * @수정자        : Baek.Gyu.Ha
 * @수정일        : 2023.07.27
 * @수정내용      : 
 * - Paging 처리 방식 변경 (강화영 수석 :기존 페이징 방식에 문제 있어서 사용 권유하지않는다고 함, 후속 작업자 참고 바람)                
 * - [2023-08-30, Gyu.Ha.Baek] PRE 반영 
 */
public interface RecipiPreconService {
	
	public Map<String, Object> selectRecipiPreconList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
}
