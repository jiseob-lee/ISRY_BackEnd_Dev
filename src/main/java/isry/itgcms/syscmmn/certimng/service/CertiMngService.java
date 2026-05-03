package isry.itgcms.syscmmn.certimng.service;

import java.util.List;
import java.util.Map;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명      	: CertiMngServiceImpl.java
 * @프로그램 설명	: 자격증에 대한 내역을 관리한다.
 * @작성자      	: Lee.Seung.Yeon
 * @작성일      	: 2022. 9. 16. 
 * @수정자      	: Lee.Seung.Yeon
 * @수정일      	: 2022. 9. 16.
 * @수정내용    	: 
 * -                
 * -                
 */
public interface CertiMngService {
	
	//자격증 목록 조회
	public List<Map<String, Object>> selectCertiList(DataRequest dataRequest) throws Exception;
	
}
