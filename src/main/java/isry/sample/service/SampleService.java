/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부 및 위기 청소년 통합 지원 시스템 관리팀의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/

package isry.sample.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.cleopatra.protocol.data.DataRequest;

/**
 * 
 * @파일명        : SampleService.java
 * @프로그램 설명 :
 * - Sample 서비스를 위한  Service Interface 입니다. 
 * @작성자        : Song.Young.Il
 * @작성일        : 2021. 11. 11. 
 * @수정자        : Song.Young.Il
 * @수정일        : 2021. 11. 11.
 * @수정내용      : 
 * -                
 * -
 */
public interface SampleService {

	List<Map<String, Object>> selectSample(Map<String, String> mapParam) throws Exception;

	void saveSample(DataRequest dataRequest);

	void saveSampleTab(DataRequest dataRequest);

	void saveSampleWithFile(DataRequest dataRequest);

	public void updateService() throws Exception; 
}
