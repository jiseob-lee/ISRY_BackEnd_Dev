/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.ytosp.portalmng.menuandprogrm.service;

import java.util.List;
import java.util.Map;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : YtospMgmtMenuService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2023. 9. 7. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2023. 9. 7.
 * @수정내용      : 
 * -                
 * -                
 */
public interface YtospMgmtMenuService {
	
	public List<Map<String, Object>> selectMenu(DataRequest dataRequest) throws Exception;
	
}
