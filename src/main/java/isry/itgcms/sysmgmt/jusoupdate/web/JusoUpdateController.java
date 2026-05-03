/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부 및 위기 청소년 통합 지원 시스템 관리팀의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/

package isry.itgcms.sysmgmt.jusoupdate.web;

import java.time.Duration;
import java.time.Instant;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseController;
import isry.itgcms.sysmgmt.jusoupdate.service.JusoUpdateService;
import isry.itgcms.sysmgmt.userjoin.service.SrchAddrService;

/**
 * 
 * @파일명        : JusoUpdateController.java
 * @프로그램 설명 : 도로명 주소 업데이트
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 29. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 29.
 * @수정내용      : 
 * -                
 * -
 */
@Controller
//@Api(value = "JusoUpdate web Controller")
@RequestMapping(value = "/isry/itgcm/sysmgmt/jusoupdate")
public class JusoUpdateController extends IsryBaseController {

	@Resource(name = "jusoUpdateService")
	private JusoUpdateService jusoUpdateService;

	@Resource(name = "srchAddrService")
	private SrchAddrService srchAddrService;
	
	// 호출 예 : http://localhost:8880/ISRY_BackEnd/isry/itgcm/sysmgmt/jusoupdate/jusoUpdate.do?dateFrom=20220206&dateTo=20220206
	
	//@ApiOperation(value = "/jusoUpdate.do", notes = "도로명 주소 업데이트 [공통] 이지섭")
	@RequestMapping(value = "/jusoUpdate.do")
	public View jusoUpdate(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		//Map<String, Object> map = mgmtMenuService.selectMenu();
		//dataRequest.setResponse("header", map.get("header"));
		//dataRequest.setResponse("menuId", map.get("menuId"));
		//dataRequest.setResponse("dsAllMenu", map.get("menuPivot"));
		//dataRequest.setResponse("dsMenuList", map.get("menuList"));

		String profile = EgovProperties.getProperty("globals", "isry.globals.profile");
		
		if (!"local".equals(profile)) {

			if (!"devserver".equals(System.getProperty("SERVER")) 
					&& !"grybwas11".equals(System.getProperty("SERVER"))) {
				// 개발 서버 JBOSS 와 행정망 운영 WAS 1 에서만 실행함.
				return new JSONDataView();
			}
			
		}

		Instant start = Instant.now();
		
		//boolean result0 = false;
		//boolean result0_1 = false;
		//boolean result0_2 = false;
		//boolean result0_3 = false;
		//boolean result0_4 = false;
		boolean result1 = false;
		boolean result2 = false;
		boolean result3 = false;
		boolean result4 = false;
		boolean result5 = false;
		boolean result6 = false;

		//result0_1 = jusoUpdateService.jusoUpdateInit1();
		//log.info("#### result0_1 : " + result0_1);
		//result0_2 = jusoUpdateService.jusoUpdateInit2();
		//log.info("#### result0_1 : " + result0_2);
		//result0_3 = jusoUpdateService.jusoUpdateInit3();
		//log.info("#### result0_1 : " + result0_3);
		//result0_4 = jusoUpdateService.jusoUpdateInit4();
		//log.info("#### result0_4 : " + result0_4);
		
		//if (result0_1 && result0_2 && result0_2 && result0_4) {
			result1 = jusoUpdateService.jusoUpdate(request.getParameter("dateFrom"), request.getParameter("dateTo"));
			//result0 = true;
		//}
		//log.info("#### result0 : " + result0);
		log.info("#### result1 : " + result1);
		
		if (result1) {
			//result2 = jusoUpdateService.processAddrData1();
			result2 = true;
		}
		log.info("#### result2 : " + result2);
		
		if (result1 && result2) {
			//jusoUpdateService.dropIndex();
			result3 = jusoUpdateService.jusoProcessAddrData3();
		}
		log.info("#### result3 : " + result3);
		
		if (result1 && result2 && result3) {
			result4 = true;
			//result4 = jusoUpdateService.createIndex();
		}
		log.info("#### result4 : " + result4);
		
		if (result1 && result2 && result3 && result4) {
			result5 = jusoUpdateService.jusoProcessAddrData4(request.getParameter("dateFrom"), request.getParameter("dateTo"));
		}
		log.info("#### result5 : " + result5);

		if (result1 && result2 && result3 && result4 && result5) {
			result6 = true; //jusoUpdateService.jusoProcessAddrData2();
		}
		log.info("#### result6 : " + result6);

		if (result1 && result2 && result3 && result4 && result5 && result6) {
			jusoUpdateService.jusoProcessSetEmd();
			//jusoUpdateService.jusoUpdateComment();
		}
		
		Instant finish = Instant.now();
		
		long timeElapsed = Duration.between(start, finish).toMillis();
		log.info("#### timeElapsed : " + timeElapsed);
		
		return new JSONDataView();
	}

	
	@RequestMapping(value = "/jusoSearchCache.do")
	public View jusoSearchCache(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		String[] search = {"가", "나", "다", "라", "마", "바", "사", "아", "자", "차", "카", "타", "파", "하"};
		for (int i=0; i < search.length; i++) {
			log.debug("#### search : " + search[i]);
			srchAddrService.selectAddr(search[i]);
		}

		return new JSONDataView();
	}
	
	@RequestMapping(value = "/jusoTableComment.do")
	public View jusoTableComment(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		jusoUpdateService.jusoUpdateComment();

		return new JSONDataView();
	}

}
