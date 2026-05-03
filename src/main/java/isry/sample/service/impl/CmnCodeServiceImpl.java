package isry.sample.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

//import javax.cache.Cache;

//import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import isry.sample.mapper.CmnCodeMapper;
import isry.sample.service.CmnCodeService;

/**
 * @Class Name : CmnCodeServiceImpl.java
 * @Description : 공통코드 샘플 Business Implement Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 *
 * @author tomatosystem
 * @since 
 * @version 
 * @see
 *
 */

@Service
public class CmnCodeServiceImpl extends EgovAbstractServiceImpl implements CmnCodeService {
	
	@Resource(name = "cmnCodeMapper")
	private CmnCodeMapper cmnCodeMapper;

	@Override
	public List<Map<String, Object>> selectCmnCodeList(String strCdCls) {
		// TODO Auto-generated method stub
		Map mapParam = new HashMap();
		mapParam.put("CD_CLS", strCdCls);
		mapParam.put("USE_YN", "Y");
		return cmnCodeMapper.selectCmnCodeList(mapParam);
	}

	

}
