/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.taskwkschmng.schprecon.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;

import isry.base.IsryBaseServiceImpl;
import isry.couns.taskwkschmng.schprecon.mapper.MnthngSchdlMapper;
import isry.couns.taskwkschmng.schprecon.service.MnthngSchdlService;

/**
 * @파일명 : SurvshtMmnServiceImpl.java
 * @프로그램 설명 : 설문지 작성을 관리하는 ServiceImpl
 * @작성자 : kim.seong.gyu
 * @작성일 : 2022. 5. 04
 * @수정자 : 
 * @수정일 : 
 * @수정내용 : - -
 */
@Service("mnthngSchdlService")
public class MnthngSchdlServiceImpl extends IsryBaseServiceImpl implements MnthngSchdlService {

	@Resource(name = "mnthngSchdlMapper")
	private MnthngSchdlMapper mapper;
	
	/**
	 * @Method명   : consultantSrch
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 14. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> consultantSrch(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return mapper.consultantSrch(mapParam);	
	}
	
	/**
	 * @Method명   : searchComboDept
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 24. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> searchComboDept(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return mapper.searchComboDept(mapParam);
	}
	
	/**
	 * @Method명   : selectCombo1ListMnthng
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 24. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectCombo1ListMnthng(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectCombo1ListMnthng(mapParam);
	}

	/**
	 * @Method명   : selectWorkListFrom
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 5. 31. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectMnthngSchdlList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectMnthngSchdlList(mapParam);
	}
	
	/**
	 * @Method명   : selectMnthngSchdlList01
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 5. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectMnthngSchdlList01(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectMnthngSchdlList01(mapParam);
	}
	
	/**
	 * @Method명   : selectMnthngSchdlList02
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 5. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectMnthngSchdlList02(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectMnthngSchdlList02(mapParam);
	}
	
	/**
	 * @Method명   : searchComboOptionTimes
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 28. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> searchComboOptionTimes(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return mapper.searchComboOptionTimes(mapParam);
	}
	
	/**
	 * @Method명   : insertMnthngSchdl
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 14. 
	 * @Method설명 :
	 */
	@Override
	public int insertMnthngSchdl(Map<String, String> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return mapper.insertMnthngSchdl(mapParam);
	}
	
	/**
	 * @Method명   : insertMnthngSchdlOut
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 14. 
	 * @Method설명 :
	 */
	@Override
	public int insertMnthngSchdlOut(Map<String, String> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return mapper.insertMnthngSchdlOut(mapParam);
	}

	/**
	 * @Method명   : updateMnthngSchdlDetail
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 28. 
	 * @Method설명 :
	 */
	@Override
	public int updateMnthngSchdlDetail(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return mapper.updateMnthngSchdlDetail(mapParam);
	}
	
	/**
	 * @Method명   : deleteMnthngSchdlDetail
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 28. 
	 * @Method설명 :
	 */
	@Override
	public int deleteMnthngSchdlDetail(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return mapper.deleteMnthngSchdlDetail(mapParam);
	}
	
	/**
	 * @Method명   : deleteDaySchdl
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 13. 
	 * @Method설명 :
	 */
	@Override
	public int deleteDaySchdl(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return mapper.deleteDaySchdl(mapParam);
	}
	
	/**
	 * @Method명   : insertDaySchdl
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 13. 
	 * @Method설명 :
	 */
	@Override
	public int insertDaySchdl(Map<String, String> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return mapper.insertDaySchdl(mapParam);
	}
	
	/**
	 * @Method명   : insertDaySchdlOut
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 14. 
	 * @Method설명 :
	 */
	@Override
	public int insertDaySchdlOut(Map<String, String> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return mapper.insertDaySchdlOut(mapParam);
	}
	
	/**
	 * @Method명   : getRowCnt
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 28. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> getRowCnt(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return mapper.getRowCnt(mapParam);
	}
	
	/**
	 * @Method명   : selectYmdSchdlExmpl
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 28. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectYmdSchdlExmpl(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectYmdSchdlExmpl(mapParam);
	}
	
	/**
	 * @Method명   : searchComboHrWork
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 29. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> searchComboHrWork(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return mapper.searchComboHrWork(mapParam);
	}
	
	/**
	 * @Method명   : getTimesForExclDown
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 11. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> getTimesForExclDown(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return mapper.getTimesForExclDown(mapParam);
	}
	
	/**
	 * @Method명   : selectMnthForExclDown
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 11. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectMnthForExclDown(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectMnthForExclDown(mapParam);
	}
	
	/**
	 * @Method명   : selectAllMemberDeptcd
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 11. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectAllMemberDeptcd(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectAllMemberDeptcd(mapParam);
	}

	

}
