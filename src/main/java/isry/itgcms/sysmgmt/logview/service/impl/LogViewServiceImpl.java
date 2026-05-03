package isry.itgcms.sysmgmt.logview.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

//import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.base.IsryBaseServiceImpl;
import isry.itgcms.sysmgmt.logview.mapper.LogViewMapper;
import isry.itgcms.sysmgmt.logview.service.LogViewService;

@Service("logViewService")
public class LogViewServiceImpl extends IsryBaseServiceImpl implements LogViewService {

	@Resource(name="logViewMapper")
    private LogViewMapper logViewMapper;

	@Override
	//@Cacheable(cacheNames="cache-10") // 캐쉬 테스트 추가 (20221104)
	public List<Map<String, Object>> selectSystemLog(Map<String, Object> map) throws Exception {

		List<Map<String, Object>> list1 = logViewMapper.selectSystemLog(map);
		//List<Map<String, Object>> list2 = new ArrayList<>();
		//if (list1 != null) {
			//ScpDb scpDb = new ScpDb();
			//for (int i=0; i < list1.size(); i++) {
				//Map<String, Object> map1 = list1.get(i);
				//map1.put("FRST_RGTR_NM_DEC", scpDb.scpDecB64((String)map1.get("FRST_RGTR_NM")));
				//map1.put("FRST_RGTR_NM", Masking.nameMasking((String)map1.get("FRST_RGTR_NM_DEC")));
				//list2.add(map1);
			//}
		//}
		
		return list1;
	}

	@Override
	public Integer selectSystemLogTotalCount(Map<String, Object> map) throws Exception {
		return logViewMapper.selectSystemLogTotalCount(map);
	}

	@Override
	public List<Map<String, Object>> selectLogInOutLog(Map<String, Object> map) throws Exception {

		List<Map<String, Object>> list1 = logViewMapper.selectLogInOutLog(map);
		//List<Map<String, Object>> list2 = new ArrayList<>();
		//ScpDb scpDb = new ScpDb();
		//for (int i=0; i < list1.size(); i++) {
			//Map<String, Object> map1 = list1.get(i);
			//map1.put("FRST_RGTR_NM", Masking.nameMasking((scpDb.scpDecB64((String)map1.get("FRST_RGTR_NM")))));
			//list2.add(map1);
		//}
		return list1;
	}

	@Override
	public Integer selectLogInOutLogTotalCount(Map<String, Object> map) throws Exception {
		return logViewMapper.selectLogInOutLogTotalCount(map);
	}

	@Override
	public List<Map<String, Object>> selectLogInOutLog2(Map<String, Object> map) throws Exception {

		map.put("ADMIN", "Y");
		
		List<Map<String, Object>> list1 = logViewMapper.selectLogInOutLog(map);
		//List<Map<String, Object>> list2 = new ArrayList<>();
		//ScpDb scpDb = new ScpDb();
		//for (int i=0; i < list1.size(); i++) {
			//Map<String, Object> map1 = list1.get(i);
			//map1.put("FRST_RGTR_NM", Masking.nameMasking((scpDb.scpDecB64((String)map1.get("FRST_RGTR_NM")))));
			//list2.add(map1);
		//}
		return list1;
	}

	@Override
	public Integer selectLogInOutLogTotalCount2(Map<String, Object> map) throws Exception {
		
		map.put("ADMIN", "Y");
		
		return logViewMapper.selectLogInOutLogTotalCount(map);
	}

	@Override
	public List<Map<String, Object>> selectErrorLog(Map<String, Object> map) throws Exception {
		List<Map<String, Object>> list = logViewMapper.selectErrorLog(map);
		//List<Map<String, Object>> list2 = new ArrayList<>();
		//if (list != null) {
			//ScpDb scpDb = new ScpDb();
			//for (int i=0; i < list.size(); i++) {
				//Map<String, Object> map1 = list.get(i);
				//map1.put("FRST_RGTR_NM", scpDb.scpDecB64((String)map1.get("FRST_RGTR_NM")));
				//list2.add(map1);
			//}
		//}
		return list;
	}

	@Override
	public Integer selectErrorLogTotalCount(Map<String, Object> map) throws Exception {
		return logViewMapper.selectErrorLogTotalCount(map);
	}

	@Override
	public Map<String, Object> selectErrorDetail(DataRequest dataRequest) throws Exception {
		
		Map<String, Object> map = null;
		
		ParameterGroup dmParam = dataRequest.getParameterGroup("dmParam");
		Map<String, String> mapParam = dmParam.getSingleValueMap();
		Integer logMngNo = Integer.parseInt(mapParam.get("LOG_MNG_NO"));
		
		map = logViewMapper.selectErrorDetail(logMngNo);
		
		return map;
	}
}
