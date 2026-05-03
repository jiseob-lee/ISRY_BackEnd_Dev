/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.quartz.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import isry.itgcms.sysmgmt.quartz.mapper.SystemLogMapper;
import isry.itgcms.sysmgmt.quartz.service.SystemLogService;

/**
 * @파일명        : SystemLogServiceImpl.java
 * @프로그램 설명 : 시스템 로그 관리
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2022. 11. 11. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2022. 11. 11.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("systemLogService")
public class SystemLogServiceImpl implements SystemLogService {
	
	private final Logger log = LoggerFactory.getLogger(SystemLogServiceImpl.class);
	
	@Resource(name="systemLogMapper")
    private SystemLogMapper systemLogMapper;
	
	/**
	 * 
	 * @Method명   : deleteSystemLogOlderThan3Months
	 * @throws Exception
	 * @작성자     : Lee.Ji.Seob
	 * @작성일     : 2022. 11. 11. 
	 * @Method설명 : 3개월 이상된 시스템 로그 삭제
	 */
	@Override
	public void clearSystemLogOlderThan1Months() throws Exception {
		log.info("#### clearSystemLogOlderThan1Months");
		systemLogMapper.clearSystemLogOlderThan1Months();
	}
}
