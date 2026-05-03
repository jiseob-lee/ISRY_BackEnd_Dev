/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csems.cmmn.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import isry.csems.cmmn.mapper.CsemsMapper;
import isry.csems.cmmn.service.CsemsService;

/**
 * @파일명        : CsemsServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Hye.Sun
 * @작성일        : 2022. 9. 29. 
 * @수정자        : Lee.Hye.Sun
 * @수정일        : 2022. 9. 29.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("csemsService")
public class CsemsServiceImpl implements CsemsService{

	@Resource(name = "csemsMapper")
	private CsemsMapper csemsMapper;
		
	@Override
	public List<Map<String, String>> selectLgsltn() {
		return csemsMapper.selectLgsltn();
	}

	@Override
	public List<Map<String, String>> selectDscsn() {
		return csemsMapper.selectDscsn();
	}

	@Override
	public List<Map<String, String>> selectDiss() {
		return csemsMapper.selectDiss();
	}

	@Override
	public List<Map<String, String>> selectPrtcr() {
		return csemsMapper.selectPrtcr();
	}

	@Override
	public List<Map<String, String>> selectProbmRelm() {
		return csemsMapper.selectProbmRelm();
	}

	@Override
	public List<Map<String, String>> selectSmkng() {
		return csemsMapper.selectSmkng();
	}

	@Override
	public List<Map<String, String>> selectDrnkg() {
		return csemsMapper.selectDrnkg();
	}

	@Override
	public List<Map<String, String>> selectTeachr() {
		return csemsMapper.selectTeachr();
	}

	@Override
	public List<Map<String, String>> selectFrid() {
		return csemsMapper.selectFrid();
	}

	@Override
	public List<Map<String, String>> selectSocty() {
		return csemsMapper.selectSocty();
	}

	@Override
	public List<Map<String, String>> selectFridCnt() {
		return csemsMapper.selectFridCnt();
	}

	@Override
	public List<Map<String, String>> selectDevlpa() {
		return csemsMapper.selectDevlpa();
	}

	@Override
	public List<Map<String, String>> selectViolnc() {
		return csemsMapper.selectViolnc();
	}

	@Override
	public List<Map<String, String>> selectSlfijr() {
		return csemsMapper.selectSlfijr();
	}

	@Override
	public List<Map<String, String>> selectSucde() {
		return csemsMapper.selectSucde();
	}

	@Override
	public List<Map<String, String>> selectNowTakng() {
		return csemsMapper.selectNowTakng();
	}

	@Override
	public List<Map<String, String>> selectTrl() {
		return csemsMapper.selectTrl();
	}

	@Override
	public List<Map<String, String>> selectRprsMaap() {
		return csemsMapper.selectRprsMaap();
	}

}
