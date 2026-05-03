/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.taskwkaltmntmng.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.tomatosystem.exbuilder6.core.util.StringUtil;

import isry.base.IsryBaseServiceImpl;
import isry.couns.mngr.taskwkaltmntmng.mapper.CnnctChatReqstdMapper;
import isry.couns.mngr.taskwkaltmntmng.service.CnnctChatReqstdService;


@Service
public class CnnctChatReqstdServiceImpl extends IsryBaseServiceImpl implements CnnctChatReqstdService {

	@Resource(name = "cnnctChatReqstdMapper")
	private CnnctChatReqstdMapper cnnctChatReqstdMapper;
	
	/**
	 * @Method명   : selectCnnctChatReqstdList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 17. 
	 * @Method설명 : 잇는채팅 상담신청서 조회
	 */
	@Override
	public List<Map<String, Object>> selectCnnctChatReqstdList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return cnnctChatReqstdMapper.selectCnnctChatReqstdList(mapParam);
	}
	
	/**
	 * @Method명   : selectCnnctChatReqstdDetail
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 18. 
	 * @Method설명 : 잇는채팅 상담신청서 상세 조회
	 */
	@Override
	public List<Map<String, Object>> selectCnnctChatReqstdDetail(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return cnnctChatReqstdMapper.selectCnnctChatReqstdDetail(mapParam);
	}
	
	/**
	 * @Method명   : selectCnnctChatReqstdDetailInfo
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 27. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectCnnctChatReqstdDetailInfo(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return cnnctChatReqstdMapper.selectCnnctChatReqstdDetailInfo(mapParam);
	}
	
	/**
	 * @Method명   : selectCnnctChatReqstdExpInfo
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 27. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectCnnctChatReqstdExpInfo(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return cnnctChatReqstdMapper.selectCnnctChatReqstdExpInfo(mapParam);
	}

	/**
	 * @Method명   : selectCnnctChatRcritTrgtInfo
	 * @param 	   : mapParam
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 5. 30. 
	 * @Method설명 : 잇는채팅모집대상구분(AYB143) 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectCnnctChatRcritTrgtInfo(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @Method명   : processCnnctChatReqstd
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 20. 
	 * @Method설명 : 잇는채팅 상담신청서 상담자 할당
	 */
	@Override
	public int processCnnctChatReqstd(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return cnnctChatReqstdMapper.processCnnctChatReqstd(mapParam);
	}
	
	/**
	 * @Method명   : updateCnnctChatReqstd
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 23. 
	 * @Method설명 : 잇는채팅 상담신청서 상담자 할당 삭제
	 */
	@Override
	public int updateCnnctChatReqstd(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return cnnctChatReqstdMapper.updateCnnctChatReqstd(mapParam);
	}
	
	/**
	 * @Method명   : searchComboOption
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 18. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> searchComboOption() throws Exception {
		// TODO Auto-generated method stub
		return cnnctChatReqstdMapper.searchComboOption();
	}
	
	/**
	 * @Method명   : updateFileAffi
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 22. 
	 * @Method설명 :
	 */
	@Override
	public int updateFileAffi(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return cnnctChatReqstdMapper.updateFileAffi(mapParam);
	}

}
