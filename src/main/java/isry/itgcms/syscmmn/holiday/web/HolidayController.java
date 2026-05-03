package isry.itgcms.syscmmn.holiday.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.itgcms.syscmmn.holiday.service.HolidayService;

@Controller
@RequestMapping("/isry/itgcm/syscmmn/holiday")
public class HolidayController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "holidayService")
	private HolidayService holidayService;

	@RequestMapping(value = "/selectHoliday.do")
	public View selectHoliday(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		dataRequest.setResponse("dsList", holidayService.selectHoliday(request)); // 공휴일 목록

		return new JSONDataView();
	}

	@RequestMapping(value = "/saveHoliday.do")
	public View saveHoliday(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		holidayService.saveHoliday(request, dataRequest); // 공휴일 저장

		return new JSONDataView();
	}
	

	@RequestMapping(value = "/selectDuplicate.do")
	public View selectDuplicate(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		dataRequest.setResponse("dmMessage", holidayService.selectDuplicate(dataRequest)); // 기존 공휴일 존재 여부
		
		return new JSONDataView();
	}
	

	@RequestMapping(value = "/saveHolidaySeparate.do")
	public View saveHolidaySeparate(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		holidayService.saveHolidaySeparate(request, dataRequest); // 공휴일 저장

		return new JSONDataView();
	}

	@RequestMapping(value = "/updateHolidaySeparate.do")
	public View updateHolidaySeparate(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		holidayService.updateHolidaySeparate(request, dataRequest); // 공휴일 저장

		return new JSONDataView();
	}

	@RequestMapping(value = "/deleteHolidaySeparate.do")
	public View deleteHolidaySeparate(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		holidayService.deleteHolidaySeparate(request, dataRequest); // 공휴일 저장

		log.debug("test");
		
		return new JSONDataView();
	}
	
}
