package isry.itgcms.syscmmn.rest.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import isry.itgcms.syscmmn.rest.service.RestService;

/**
 * @파일명      	: RestServiceImpl.java
 * @프로그램 설명 	: REST 방식의 연계 호출을 위한 서비스
 * @작성자      	: 
 * @작성일      	: 2022. 9. 14.
 * @수정자      	: 
 * @수정일      	: 2022. 9. 14.
 * @수정내용    	: 
 * - 
 */
@Service("restService")
public class RestServiceImpl implements RestService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * @Method명	 : sendREST
	 * @param	 : sendUrl	 - 호출URL
	 *             jsonValue - json형태의 요청 데이터
	 * @return	 : 응답 데이터
	 * @throws Exception
	 * @작성자 	 : 
	 * @작성일	 : 2022. 9. 14.
	 * @Method설명 :
	 */
	@Override
	public String sendREST(String sendUrl, String jsonValue) throws Exception {

		String inputLine = null;

		if(sendUrl == null || "".equals(sendUrl)) {
			throw new AppWorksException("수신 URL을 확인해 주세요.", Alert.ERROR);
		}

		if(jsonValue == null || "".equals(jsonValue)) {
			throw new AppWorksException("요청 데이터를 확인해주세요.", Alert.ERROR);
		}

		log.debug("sendUrl : " + sendUrl);
		log.debug("jsonValue : " + jsonValue);

		StringBuffer outResult = new StringBuffer();
		
		OutputStream   os = null;
		BufferedReader in = null;

		try {

			URL url = new URL(sendUrl);

			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type"  , "application/json");
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setConnectTimeout(100000);
			conn.setReadTimeout(100000);
			
			os = conn.getOutputStream();
			os.write(jsonValue.getBytes("UTF-8"));
			os.flush();

			//리턴된 결과
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while((inputLine = in.readLine()) != null) {
				outResult.append(inputLine);
			}

			conn.disconnect();			

		} finally {
			if(os != null) {
				try {
					os.close();
				} catch(IOException ie) {
					log.error("IO 예외 발생");
				} catch(Exception e) {
					log.error("기타 예외 발생");
				}
			}
			
			if(in != null) {
				try {
					in.close();
				} catch(IOException ie) {
					log.error("IO 예외 발생");
				} catch(Exception e) {
					log.error("기타 예외 발생");
				}
			}			
		}

		return outResult.toString();
	}
}
