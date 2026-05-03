package isry.itgcms.syscmmn.holiday.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

public interface HolidayService {
	
	public List<Map<String, Object>> selectHoliday(HttpServletRequest request) throws Exception;
	
	public void saveHoliday(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public Map<String, String> selectDuplicate(DataRequest dataRequest) throws Exception;
	
	public void saveHolidaySeparate(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public void updateHolidaySeparate(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public void deleteHolidaySeparate(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
}
