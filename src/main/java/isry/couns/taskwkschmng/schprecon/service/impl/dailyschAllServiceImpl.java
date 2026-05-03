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
import isry.couns.taskwkschmng.schprecon.mapper.dailyschAllMapper;
import isry.couns.taskwkschmng.schprecon.service.dailyschAllService;

/**
 * @파일명 : SurvshtMmnServiceImpl.java
 * @프로그램 설명 : 설문지 작성을 관리하는 ServiceImpl
 * @작성자 : kim.seong.gyu
 * @작성일 : 2022. 5. 04
 * @수정자 : 
 * @수정일 : 
 * @수정내용 : - -
 */
@Service("dailyschAllService")
public class dailyschAllServiceImpl extends IsryBaseServiceImpl implements dailyschAllService {

	@Resource(name = "dailyschAllMapper")
	private dailyschAllMapper dailyschAllMapper;

	/**
	 * @Method명   : subOnLoadDsTime
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 5. 24. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> subOnLoadDsTime(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return dailyschAllMapper.subOnLoadDsTime(mapParam);
	}

	/**
	 * @Method명   : subOnLoadDsCyberTime
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 5. 24. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> subOnLoadDsCyberTime(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return dailyschAllMapper.subOnLoadDsCyberTime(mapParam);
	}

	/**
	 * @Method명   : subOnLoadDsMobileTime
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 5. 24. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> subOnLoadDsMobileTime(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return dailyschAllMapper.subOnLoadDsMobileTime(mapParam);
	}

	/**
	 * @Method명   : subOnLoadDsOutreachTime
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 5. 24. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> subOnLoadDsOutreachTime(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		
		List<Map<String, Object>> returnList = dailyschAllMapper.subOnLoadDsOutreachTime(mapParam);

		for (Map<String, Object> map : returnList) {

			for (int i = 1; i < 3; i++) {
				String type = (String) map.get("T" + i + "_WORK_TYPE");
				System.out.println("type ::: " + type);

				if (type != null && type != "") {
					if ("F".equals(type)) {
						map.replace("T" + i + "_WORK_TYPE", "A");
					} else if ("C".equals(type)) {
						map.replace("T" + i + "_WORK_TYPE", "C");
					} else if ("T".equals(type) || "I".equals(type)){
						map.replace("T" + i + "_WORK_TYPE", "B");
					}
				}
				System.out.println("map ::: " + map.toString());
			}
		}
		return returnList;
		  
		
//		return dailyschAllMapper.subOnLoadDsOutreachTime(mapParam);
	}


	

}
