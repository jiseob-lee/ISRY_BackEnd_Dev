package isry.itgcms.syscmmn.certimng.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.itgcms.syscmmn.certimng.mapper.CertiMngMapper;
import isry.itgcms.syscmmn.certimng.service.CertiMngService;

/**
 * @파일명      	: CertiMngServiceImpl.java
 * @프로그램 설명	: 자격증에 대한 내역을 관리한다.
 * @작성자      	: Lee.Seung.Yeon
 * @작성일      	: 2022. 9. 16. 
 * @수정자      	: Lee.Seung.Yeon
 * @수정일      	: 2022. 9. 16.
 * @수정내용    	: 
 * -                
 * -                
 */
@Service("certiMngService")
public class CertiMngServiceImpl implements CertiMngService {

	@Resource(name = "certiMngMapper")
	private CertiMngMapper certiMngMapper;

	@Override
	public List<Map<String, Object>> selectCertiList(DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> rtnMap = new ArrayList<Map<String,Object>>();
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");

		Map<String, String> paramMap = null;
		if(parameterGroup != null) {
			paramMap = parameterGroup.getSingleValueMap();
		}

		rtnMap = certiMngMapper.selectCertiList(paramMap);		

		return rtnMap;
	}
}
