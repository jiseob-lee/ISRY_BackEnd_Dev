/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.sample.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import isry.sample.mapper.Sample2Mapper;
import isry.sample.service.Sample2Service;

/**
 * @파일명        : SampleService2Impl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2023. 6. 28. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2023. 6. 28.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("sample2Service")
public class Sample2ServiceImpl implements Sample2Service {
	
	@Resource(name = "sample2Mapper")
	private Sample2Mapper sample2Mapper;

	
	@Override
	public void update2Service() throws Exception {
		
		sample2Mapper.updateWrdPhone("jiseob45");
		
		int i = 1/0;
		
	}
}
