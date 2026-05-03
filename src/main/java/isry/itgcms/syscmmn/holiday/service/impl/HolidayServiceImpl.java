package isry.itgcms.syscmmn.holiday.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.itgcms.syscmmn.holiday.mapper.HolidayMapper;
import isry.itgcms.syscmmn.holiday.service.HolidayService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

@Service("holidayService")
public class HolidayServiceImpl implements HolidayService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "holidayMapper")
	private HolidayMapper holidayMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Override
	public List<Map<String, Object>> selectHoliday(HttpServletRequest request) throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		
		return holidayMapper.selectHoliday(paramMap);
	}
	
	@Override
	public void saveHoliday(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}
		
		log.debug("userId2 : " + userId2);
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dsList");
		List<Map<String, String>> list = parameterGroup.getAllRowList();
		
		for (int i=0; i < list.size(); i++) {
			Map<String, String> map = list.get(i);
			map.put("HOLIDAY_NAME", map.get("일정 명칭"));
			map.put("START_DATE", map.get("시작일"));
			map.put("END_DATE", map.get("종료일"));
			map.put("USER_ID", userId2);
			map.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
			holidayMapper.insertHoliday(map);
		}
	}
	
	@Override
	public Map<String, String> selectDuplicate(DataRequest dataRequest) throws Exception {
		
		Map<String, String> mapReturn = new HashMap<>();
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dsList");
		List<Map<String, String>> list = parameterGroup.getAllRowList();
		
		String str = "";
		
		for (int i=0; i < list.size(); i++) {
			
			Map<String, String> map = list.get(i);
			
			map.put("HOLIDAY_NAME", map.get("일정 명칭"));
			map.put("START_DATE", map.get("시작일"));
			map.put("END_DATE", map.get("종료일"));
			
			int count = holidayMapper.selectDuplicate(map);
			
			if (count > 0) {
				if (str.length() > 0) {
					str += ", ";
				}
				str += map.get("일정 명칭");
			}
		}
		
		mapReturn.put("msg", str);
		
		return mapReturn;
	}
	
	@Override
	public void saveHolidaySeparate(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmItem");
		Map<String, String> map = parameterGroup.getSingleValueMap();
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}
		
		map.put("START_DATE", map.get("START_DATE").replaceAll("[^\\d]", ""));
		map.put("END_DATE", map.get("END_DATE").replaceAll("[^\\d]", ""));
		
		map.put("USER_ID", userId2);
		map.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		
		holidayMapper.saveHolidaySeparate(map);
	}
	
	@Override
	public void updateHolidaySeparate(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmItem");
		Map<String, String> map = parameterGroup.getSingleValueMap();
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}
		
		map.put("START_DATE", map.get("START_DATE").replaceAll("[^\\d]", ""));
		map.put("END_DATE", map.get("END_DATE").replaceAll("[^\\d]", ""));
		map.put("PREV_START_DATE", map.get("PREV_START_DATE").replaceAll("[^\\d]", ""));
		map.put("PREV_END_DATE", map.get("PREV_END_DATE").replaceAll("[^\\d]", ""));
		
		map.put("USER_ID", userId2);
		
		holidayMapper.updateHolidaySeparate(map);
	}

	@Override
	public void deleteHolidaySeparate(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmItem");
		Map<String, String> map = parameterGroup.getSingleValueMap();

		map.put("START_DATE", map.get("START_DATE").replaceAll("[^\\d]", ""));
		map.put("END_DATE", map.get("END_DATE").replaceAll("[^\\d]", ""));
		map.put("PREV_START_DATE", map.get("PREV_START_DATE").replaceAll("[^\\d]", ""));
		map.put("PREV_END_DATE", map.get("PREV_END_DATE").replaceAll("[^\\d]", ""));
		
		holidayMapper.deleteHolidaySeparate(map);
	}
}
