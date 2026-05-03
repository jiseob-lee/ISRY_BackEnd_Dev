package isry.itgcms.syscmmn.rest.service;

/**
 * @파일명      	: RestService.java
 * @프로그램 설명 	: REST 방식의 연계 호출을 위한 서비스
 * @작성자      	: 
 * @작성일      	: 2022. 9. 14.
 * @수정자      	: 
 * @수정일      	: 2022. 9. 14.
 * @수정내용    	: 
 * - 
 */
public interface RestService {
	
	public String sendREST(String sendUrl, String jsonValue) throws Exception;
	
}
