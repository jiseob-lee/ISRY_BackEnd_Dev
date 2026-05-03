/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.address.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : AddressService.java
 * @프로그램 설명 : 주소록 프로그램
 * - 
 * - 
 * @작성자        : Ji.Seob.Lee
 * @작성일        : 2022. 5. 18. 
 * @수정자        : Ji.Seob.Lee
 * @수정일        : 2022. 5. 18.
 * @수정내용      : 
 * -                
 * -                
 */
public interface AddressService {

	public List<Map<String, Object>> selectAddress(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Integer selectAddressCount(Map<String, Object> dmSearchMap) throws Exception;

	public List<Map<String, Object>> selectAddress(Map<String, Object> dmSearchMap) throws Exception;
	
	public List<Map<String, Object>> selectWorker(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public List<Map<String, Object>> selectClient(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public Integer selectClientCount(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> selectClientPaging(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> selectGroup(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public void saveGroup(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public void deleteGroup(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public List<Map<String, Object>> selectGroupPerson(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public void saveGroupPerson(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public void deleteGroupPerson(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public List<Map<String, Object>> selectGroupsPersons(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public List<Map<String, Object>> selectSggList() throws Exception;

}
