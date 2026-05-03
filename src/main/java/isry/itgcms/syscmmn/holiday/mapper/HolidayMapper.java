package isry.itgcms.syscmmn.holiday.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("holidayMapper")
public interface HolidayMapper {
	
	public List<Map<String, Object>> selectHoliday(Map<String, Object> paramMap) throws Exception;
	
	public void insertHoliday(Map<String, String> map) throws Exception;
	
	public int selectDuplicate(Map<String, String> map) throws Exception;
	
	public void saveHolidaySeparate(Map<String, String> map) throws Exception;
	
	public void updateHolidaySeparate(Map<String, String> map) throws Exception;
	
	public void deleteHolidaySeparate(Map<String, String> map) throws Exception;
	
}
