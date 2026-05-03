/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.quartz.mapper;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : LongTermNotConnectedMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2023. 4. 19. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2023. 4. 19.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("longTermNotConnectedMapper")
public interface LongTermNotConnectedMapper {
	
	public void updateCutOffLongTermNotConnected(String userId) throws Exception;
	
	public List<String> selectLongTermNotConnected() throws Exception;
	
}
