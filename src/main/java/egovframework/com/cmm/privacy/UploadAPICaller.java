package egovframework.com.cmm.privacy;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import egovframework.com.cmm.service.EgovProperties;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명        : UploadAPICaller.java
 * @프로그램 설명 : 개인정보 필터링 시스템 API. 이지서티 제공 기본 API
 * @작성자        : HAN CHANH HUN
 * @작성일        : 2022. 06. 27. 
 * @수정자        : HAN CHANH HUN
 * @수정일        : 2022. 06. 27.
 * @수정내용      :                
 */

public class UploadAPICaller {
	
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	
	private static final String DEFAULT_ENCODING = "UTF-8";
 	final static private String crlf = "\r\n"; //
 	final static String twoHyphens = "--";
 
    private String url;
    private String encoding;
    private HashMap<String, Object> param;
     
    //Constructor =========================================================
    
    public UploadAPICaller(){
    }
     
    public UploadAPICaller(String url){
        this(url, null);
    }
     
    public UploadAPICaller(String url, String encoding){
        this.encoding = encoding==null ? DEFAULT_ENCODING : encoding;
        this.url = url;
        this.param = new HashMap<String, Object>();
    }

    public UploadAPICaller addParam(String name, String value){
        this.param.put(name, value);
        return this;
    }

    public UploadAPICaller addParam(String name, File file){
    	this.param.put(name, file);
        return this;
    }
    //=====================================================================
    
    //API 서버로에 전송
	public String submit(int apiTimeout){ //apiTimeout 단위 : ms
		
        String result = null;
	 	String boundary = "------------------------------------" + UUID.randomUUID().toString().replace("-",""); // boundary 랜덤 생성
	 	FileInputStream inputStream = null;
	 	DataOutputStream request = null;
	 	BufferedReader br = null;
	 	
        try {
             URL url = new URL(this.url);
             HttpURLConnection con = (HttpURLConnection) url.openConnection(); 
             con.setConnectTimeout(apiTimeout); 								//서버에 연결되는 Timeout 시간 설정
             con.setUseCaches(false);
             con.setDoOutput(true);
             con.setDoInput(true);
             con.setRequestMethod("POST");
             con.setRequestProperty("Cache-Control", "no-cache");            // cache 없이
             con.setRequestProperty("accept-charset", encoding); 
             con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
             
             // FORM REQUEST BODY 제작 -------------------------------
             OutputStream httpConnOutputStream = con.getOutputStream();
             request = new DataOutputStream(httpConnOutputStream);
             
             int chkContents = 0;
             for (Map.Entry<String, Object> entry : this.param.entrySet()) { 
            	if(entry.getKey().contains("content"))
            		chkContents++;
             }
             
             for (Map.Entry<String, Object> entry : this.param.entrySet()) {
            	 String _key = entry.getKey(); 
            	 Object _value = entry.getValue(); 
            	 
            	 
            	 // String Type 
            	 if(_value.getClass() == java.lang.String.class){
            		 String value = (String)_value;
    	             request.writeBytes(twoHyphens + boundary + crlf);
    	             request.writeBytes("Content-Disposition: form-data; name=\"" + _key + "\""+ crlf);
    	             request.writeBytes("Content-Type: text/plain; charset=" + encoding + crlf);
    	             request.writeBytes(crlf);
    	             request.write(value.toString().getBytes()); //content 한글 정보 깨짐 처리 방지를 위해 byte로 
    	             request.writeBytes(crlf);
    	             request.flush();
            	 } 
            	 // File Type
            	 else if(_value.getClass() == java.io.File.class){
            		 File file_info = (File)_value;
    	             String uploadFileName = new String(file_info.getName().getBytes(encoding), "ISO-8859-1"); // 한글깨짐 방지
    	             request.writeBytes(twoHyphens + boundary + crlf);
    	             request.writeBytes("Content-Disposition: form-data; name=\"" + _key +"\"; filename=\""+ uploadFileName + "\"" + crlf);
    	             request.writeBytes("Content-Type: " + URLConnection.guessContentTypeFromName(uploadFileName) + crlf);
    	             request.writeBytes("Content-Transfer-Encoding: binary" + crlf + crlf);
    	             request.flush();
    	             
    	             // file 읽어 전달
    	             inputStream = new FileInputStream(file_info);
    	             byte[] buffer = new byte[4096]; //4096 바이트씩 잘라
    	             int bytesRead = -1;
    	             while ((bytesRead = inputStream.read(buffer)) != -1) {
    	             	httpConnOutputStream.write(buffer, 0, bytesRead);
    	             }
    	             httpConnOutputStream.flush();
    	             inputStream.close();
    	             
    	             request.writeBytes(crlf);
    	             request.flush();
            	 }
            	 
             }

             // FORM REQUEST BODY 끝 -------------------------------
             request.writeBytes(twoHyphens + boundary + twoHyphens + crlf); // requestbody end
             request.flush();
             request.close();
             
             StringBuilder sb = new StringBuilder();
             int nRet = con.getResponseCode();
             if (nRet == HttpURLConnection.HTTP_OK) { // HTTP_OK
                 br = new BufferedReader(new InputStreamReader(con.getInputStream(), encoding));
                 String line;
                 while ((line = br.readLine()) != null) {
                     sb.append(line).append("\n");
                 }
                 br.close();
                 result = sb.toString();
             }else { // HTTP_ERROR
            	 result = "HTTP_ERROR:" + nRet + "/" + con.getResponseMessage();            	
             }            
         } catch (Exception e) {
        	 if("connect timed out".equals(e.getMessage()))	
        		 result = null;
        	 else
        		 result = "EXCEPTION:"+e.getMessage();
         } finally {
        	 if (inputStream != null) {
    			try {
					inputStream.close();
				} catch (IOException e) {
					//System.out.println(e.getMessage());
				}
        	 }
        	 if (request != null) {
        		 try {
					request.close();
				} catch (IOException e) {
					//System.out.println(e.getMessage());
				}
        	 }
        	 if (br != null) {
        		 try {
					br.close();
				} catch (IOException e) {
					//System.out.println(e.getMessage());
				}
        	 }
         }
 	    
        return result;
    }

	
	// 컨텐츠 등록 및 수정시 content 정보 전달 => 개인정보 필터링 API 호출
    public void prevacyContent(String content) throws Exception {

    	ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    	HttpServletRequest request = servletRequestAttribute.getRequest();
    	
    	String clientIp = request.getRemoteAddr();
    	String clientUri = request.getRequestURI();
    	String userId = "";
    	String userName = "";
    	String orgName = "";

		HttpSession session = request.getSession();
		//UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
		
		userId = session == null ? null : (String)session.getAttribute("userId"); //userDetailsVO.getId();
		userName = session == null ? null : (String)session.getAttribute("userName"); //userDetailsVO.getUserName();
		
		if (userId != null && !"".equals(userId)) {	
			
	    	UploadAPICaller caller = new UploadAPICaller(EgovProperties.getProperty("globals", "isry.privacy.url")); // 개발계 API 서버 주소
			String result = caller
			.addParam("hostName", EgovProperties.getProperty("globals", "isry.server.ip")) // 필수 ** : 관리자 UI 상에 등록 된 hostname
			.addParam("hostPort", EgovProperties.getProperty("globals", "isry.server.port")) // 필수 ** : 관리자 UI 상에 등록 된 hostPort
			
			// 필수 항목. 해당 값은 파라미터 정보 데이터 
			.addParam("userIP", clientIp) // 필수 ** : 사용자 IP - log 적재를 위한 값으로 was에서 사용하는 파라미터값으로 삽입
			.addParam("reqUrl", clientUri) // 필수 ** : API를 호출하는 페이지 IP/domain - log 적재를 위한 파라미터값
			
			// 선택 항목. 해당 값은 파라미터 정보 데이터 
			.addParam("userId", userId) // was에서 사용하는 파라미터값으로 삽입
			.addParam("userName", userName) // was에서 사용하는 파라미터값으로 삽입
			.addParam("deptId", orgName) // was에서 사용하는 파라미터값으로 삽입
			.addParam("content", content) // was에서 사용하는 파라미터값으로 삽입
			// 파일 정	보 : 사용자 등록한 파일정보 
			//.addParam("file", new File(filePath)) // was에서 사용하는 파라미터값으로 삽입
			.submit(2000);	
			log.info("개인정보 필터링 API 결과 : " + result); 
						
		}	
    }
    
    // 파일 등록 및 수정시 content 정보 전달 => 개인정보 필터링 API 호출
    public void prevacyFile(String filePath) throws Exception {

    	ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    	HttpServletRequest request = servletRequestAttribute.getRequest();
    	
    	String clientIp = request.getRemoteAddr();
    	String clientUri = request.getRequestURI();
    	String userId = "";
    	String userName = "";
    	String orgName = "";

		HttpSession session = request.getSession(false);
		//UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);

		userId = session == null ? null : (String)session.getAttribute("userId"); //userDetailsVO.getId();
		userName = session == null ? null : (String)session.getAttribute("userName"); //userDetailsVO.getUserName();

		if (userId != null && !"".equals(userId)) {

	    	UploadAPICaller caller = new UploadAPICaller(EgovProperties.getProperty("globals", "isry.privacy.url")); // 개발계 API 서버 주소
			String result = caller
			.addParam("hostName", EgovProperties.getProperty("globals", "isry.server.ip")) // 필수 ** : 관리자 UI 상에 등록 된 hostname
			.addParam("hostPort", EgovProperties.getProperty("globals", "isry.server.port")) // 필수 ** : 관리자 UI 상에 등록 된 hostPort
			
			// 필수 항목. 해당 값은 파라미터 정보 데이터 
			.addParam("userIP", clientIp) // 필수 ** : 사용자 IP - log 적재를 위한 값으로 was에서 사용하는 파라미터값으로 삽입
			.addParam("reqUrl", clientUri) // 필수 ** : API를 호출하는 페이지 IP/domain - log 적재를 위한 파라미터값
			
			// 선택 항목. 해당 값은 파라미터 정보 데이터 
			.addParam("userId", userId) // was에서 사용하는 파라미터값으로 삽입
			.addParam("userName", userName) // was에서 사용하는 파라미터값으로 삽입
			.addParam("deptId", orgName) // was에서 사용하는 파라미터값으로 삽입
			//.addParam("content", content) // was에서 사용하는 파라미터값으로 삽입
			// 파일 정	보 : 사용자 등록한 파일정보 
			.addParam("file", new File(filePath)) // was에서 사용하는 파라미터값으로 삽입
			.submit(2000);	
			log.info("개인정보 필터링 API 결과 : " + result);  		
					
		}	
    }
}
