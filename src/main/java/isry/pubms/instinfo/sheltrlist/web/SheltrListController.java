/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.pubms.instinfo.sheltrlist.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;
import com.fasterxml.jackson.databind.ObjectMapper;

import isry.pubms.instinfo.sheltrlist.service.SheltrListService;

/**
 * @파일명        : SheltrListController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Jeong.Hyun.Jin
 * @작성일        : 2023. 8. 22. 
 * @수정자        : Jeong.Hyun.Jin
 * @수정일        : 2023. 8. 30.
 * @수정내용      : 
 * -                
 * -                
 */

@Controller
@RequestMapping("/sheltrlist")
public class SheltrListController {
	
	//private Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "sheltrListService")
	private SheltrListService sheltrListService;

	@RequestMapping("/sheltrListApi.do")
	public ResponseEntity<?> selectSheltrCntList(@Param("local")@Nullable Integer local) throws Exception {

		List<Map<String,String>> shelters = sheltrListService.selectSheltrList(local);
		Map<String, Object> response = new HashMap<>();
		response.put("total", shelters.size());
		response.put("shelters", shelters);
		return new ResponseEntity<>(response,HttpStatus.OK) ;
	}
}





