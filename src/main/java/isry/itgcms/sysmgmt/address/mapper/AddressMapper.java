/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.address.mapper;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : AddressMapper.java
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
@Mapper("addressMapper")
public interface AddressMapper {
	
	public List<Map<String, Object>> selectAddress(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> selectAddressPaging(Map<String, Object> map) throws Exception;
	
	public Integer selectAddressCount(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> selectWorker(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> selectClient(Map<String, Object> map) throws Exception;
	
	public Integer selectClientCount(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> selectClientPaging(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> selectGroup(Map<String, Object> map) throws Exception;
	
	public Integer deleteGroup(Map<String, Object> map) throws Exception;
	
	public Integer insertGroup(Map<String, Object> map) throws Exception;
	
	public Integer deleteGroupName(Map<String, String> map) throws Exception;
	
	public Integer deleteGroupPersons(Map<String, String> map) throws Exception;
	
	public List<Map<String, Object>> selectGroupPerson(Map<String, Object> map) throws Exception;
	
	public void insertGroupPerson(Map<String, Object> map) throws Exception;
	
	public void deleteGroupPerson(Map<String, String> map) throws Exception;
	
	public List<Map<String, Object>> selectGroupsPersons(List<Integer> list) throws Exception;
	
	public List<Map<String, Object>> selectSggList() throws Exception;
}
