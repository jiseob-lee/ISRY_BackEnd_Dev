package isry.itgcms.sysmgmt.util;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CurrentTime {

	@RequestMapping(value = "/getCurrentTime.do")
	@ResponseBody
	public Long getCurrentTime(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //RestTemplate restTemplate = new RestTemplate();
        //String url = "https://worldtimeapi.org/api/timezone/Asia/Seoul";
        //String url = "https://timeapi.io/api/Time/current/zone?timeZone=Asia/Seoul";
        
        // GET 요청 예시
        //@SuppressWarnings("rawtypes")
		//ResponseEntity<Map> urlResponse = restTemplate.getForEntity(url, Map.class);
        //@SuppressWarnings("unchecked")
		//Map<String, Object> responseBody = urlResponse.getBody();
        //int statusCode = urlResponse.getStatusCodeValue();

        //System.out.println("Response Body: " + responseBody);
        //System.out.println("Status Code: " + statusCode);

        
        //LocalDateTime now = LocalDateTime.now();
        //System.out.println("현재 날짜 및 시간: " + now);

        //LocalDate date = LocalDate.now();
        //System.out.println("현재 날짜: " + date);

        //LocalTime time = LocalTime.now();
        //System.out.println("현재 시간: " + time);
        
        
        long timestamp = System.currentTimeMillis();
        System.out.println("current timestamp (milli seconds): " + timestamp);
        

		Instant timestamp1 = Instant.now();
		System.out.println("current timestamp: " + timestamp1);

		// Convert timestamp to milli seconds
		long milliseconds = timestamp1.toEpochMilli();
		System.out.println("current timestamp (milli seconds): " + milliseconds);
		
		return milliseconds;
        //return responseBody;
        //return restTemplate.getForObject(url, Map.class);
	}

}
