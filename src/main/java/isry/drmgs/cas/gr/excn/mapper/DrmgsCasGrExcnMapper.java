/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.cas.gr.excn.mapper;

import java.util.List;
import java.util.Map;

import com.cleopatra.protocol.data.DataRequest;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : DrmgsCasGrExcnMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Hee.Sung.Yoon
 * @작성일        : 2022. 7. 13. 
 * @수정자        : Hee.Sung.Yoon
 * @수정일        : 2022. 7. 13.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("drmgsCasGrExcnMapper")
public interface DrmgsCasGrExcnMapper {

	public List<Map<String, Object>> selectChkList(Map<String, String> map) throws Exception;
	
	public void delChkList(Map<String, String> map) throws Exception;
	
	public void insChkList(Map<String, String> map) throws Exception;
	
	public void delDayList(Map<String, String> map) throws Exception;
	
	public void insDayList(Map<String, String> map) throws Exception;
	
	public List<Map<String, Object>> selectDayList(Map<String, String> map) throws Exception;
	
	public void updatePvsnWhda(Map<String, String> map) throws Exception;
}
