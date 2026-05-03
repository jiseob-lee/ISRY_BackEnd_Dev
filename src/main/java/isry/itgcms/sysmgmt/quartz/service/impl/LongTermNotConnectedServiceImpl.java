/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.quartz.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import isry.itgcms.sysmgmt.config.service.MgmtCmmnConfigService;
import isry.itgcms.sysmgmt.quartz.mapper.LongTermNotConnectedMapper;
import isry.itgcms.sysmgmt.quartz.service.LongTermNotConnectedService;

/**
 * @파일명        : LongTermNotConnectedServiceImpl.java
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
@Service("longTermNotConnectedService")
public class LongTermNotConnectedServiceImpl implements LongTermNotConnectedService {

	private final Logger log = LoggerFactory.getLogger(LongTermNotConnectedServiceImpl.class);
	
	@Resource(name="longTermNotConnectedMapper")
    private LongTermNotConnectedMapper longTermNotConnectedMapper;
	
	@Resource(name="mgmtCmmnConfigService")
	private MgmtCmmnConfigService mgmtCmmnConfigService;
	
	@Override
	public void updateCutOffLongTermNotConnected() throws Exception {
		
		List<String> list = longTermNotConnectedMapper.selectLongTermNotConnected();
		
		for (int i=0; i < list.size(); i++) {
			longTermNotConnectedMapper.updateCutOffLongTermNotConnected(list.get(i));
		}
	}
}
