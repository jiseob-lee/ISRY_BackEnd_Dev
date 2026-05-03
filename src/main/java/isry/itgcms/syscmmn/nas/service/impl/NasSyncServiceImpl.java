package isry.itgcms.syscmmn.nas.service.impl;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import egovframework.com.cmm.service.EgovProperties;
import isry.itgcms.syscmmn.nas.service.NasSyncService;
import isry.itgcms.syscmmn.nas.vo.NasSyncResultVO;

@Service("nasSyncService")
public class NasSyncServiceImpl implements NasSyncService {

	private final Logger log = LoggerFactory.getLogger(NasSyncServiceImpl.class);
	
	public String getNasSyncServer() {
		String nasSyncServer = null;
		
		String strNasSyncServer =  EgovProperties.getProperty("globals", "isry.nas.sync.server");
		if(strNasSyncServer != null) {
			String[] nasSyncServerArr = strNasSyncServer.split(";");
			List<String> nasSyncServerArrToList = Arrays.asList(nasSyncServerArr);
			Collections.shuffle(nasSyncServerArrToList);
			nasSyncServerArrToList.toArray(nasSyncServerArr);
			
			for(int i=0;i < nasSyncServerArr.length;i++) {
				HttpURLConnection httpConn = null;
				try {
					if(nasSyncServerArr[i] == null) {
						continue;
					}
					
					log.debug("http://"+nasSyncServerArr[i]);
					URL url = new URL("http://"+nasSyncServerArr[i]);
					httpConn = (HttpURLConnection) url.openConnection();
					httpConn.setConnectTimeout(300); // 타임아웃 설정 0.3초
					int responseCode = httpConn.getResponseCode();
					log.debug(""+responseCode);
					if(responseCode == 200) {
						nasSyncServer = nasSyncServerArr[i];
						break;
					}
				} catch (Exception e) {
				}
			}
		}
		
		return nasSyncServer;
	}
	
	
	@Override
	public NasSyncResultVO uploadFile(String fileName, String filePath) throws Exception {
		
		NasSyncResultVO nasSyncResult = new NasSyncResultVO();
		
		String nasSyncServer = getNasSyncServer();

		if(nasSyncServer != null) { 
			String apiUrl = "http://"+nasSyncServer + "/nas/upload";
			log.debug(apiUrl);
	
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			
	        RestTemplate restTemplate = new RestTemplate();
	        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
	        body.add("fileName", fileName);
	        body.add("filePath", filePath);
	        FileSystemResource fileSystemResource = new FileSystemResource(new File(filePath+fileName));
	        body.add("upFile", fileSystemResource);
	        
	        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
	        
	        // API 서버로 요청 전송
	        ResponseEntity<NasSyncResultVO> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, NasSyncResultVO.class);
	        
	        // API 서버로부터 응답 데이터 받기
	        nasSyncResult = responseEntity.getBody();
	        log.debug("RESULT : {}", nasSyncResult);
		}
        return nasSyncResult;
	}

	@Override
	public NasSyncResultVO deleteFile(String fileName, String filePath) throws Exception {
	NasSyncResultVO nasSyncResult = new NasSyncResultVO();
		
		String nasSyncServer = getNasSyncServer();

		if(nasSyncServer != null) { 
			String apiUrl = "http://" + nasSyncServer + "/nas/delete";
			log.debug(apiUrl);
	
	        RestTemplate restTemplate = new RestTemplate();
	        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
	        paramMap.add("fileName", fileName);
	        paramMap.add("filePath", filePath);
	        
	        // API 서버로 요청 전송
	        ResponseEntity<NasSyncResultVO> responseEntity = restTemplate.postForEntity(apiUrl, paramMap, NasSyncResultVO.class);
	        
	        // API 서버로부터 응답 데이터 받기
	        nasSyncResult = responseEntity.getBody();
	        log.debug("RESULT : {}", nasSyncResult);
		}
        return nasSyncResult;
	}

}
