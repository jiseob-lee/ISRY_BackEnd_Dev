package isry.itgcms.sysmgmt.ssoToken;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class SsoTokenController {

	@RequestMapping("/getSsoToken.do")
	public View getSsoToken(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://13.124.111.141:8089/api/v1/sso/getSsoToken";
        //String url = "https://timeapi.io/api/Time/current/zone?timeZone=Asia/Seoul";
        
        // GET 요청 예시
        @SuppressWarnings("rawtypes")
		ResponseEntity<Map> urlResponse = restTemplate.getForEntity(url, Map.class);
        @SuppressWarnings("unchecked")
		Map<String, Object> responseBody = urlResponse.getBody();
        int statusCode = urlResponse.getStatusCodeValue();

        System.out.println("Response Body: " + responseBody);
        System.out.println("Status Code: " + statusCode);

		dataRequest.setResponse("ssoTokenMap", responseBody);
		
		return new JSONDataView();
	}
}
