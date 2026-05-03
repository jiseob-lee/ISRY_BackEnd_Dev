/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부 및 위기 청소년 통합 지원 시스템 관리팀의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/

package isry.sample.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import isry.base.IsryBaseController;
import isry.redis.service.RedisService;

/**
 * 
 * @파일명        : RedisController.java
 * @프로그램 설명 :
 * - Redis Cache 데이터를 조회,저장 하는 컨트롤러  
 * - 
 * @작성자        : Song.Young.Il
 * @작성일        : 2022. 2. 15. 
 * @수정자        : Song.Young.Il
 * @수정일        : 2022. 2. 15.
 * @수정내용      : 
 * -                
 * -
 */
@Controller
@RequestMapping("/sample")
public class RedisController extends IsryBaseController {

	/* Redis Cache 서비스 선언 */
	//@Resource
	//private RedisService3 redisService;

	/* =========================================================================================== */
	/* 추후Cache 키는 아래의 내역처럼 대상 서비스명(패키지명+서비스+Cache명+map,list )의 명명규칙을 따른다.          */
	/* 업무팀에서 Cache 데이터를 생성 할때는 AA와 상담 후 키를 생성하도록 한다.                    */
	/* =========================================================================================== */
	String KeyMap  = "isry.sample.redis.service.cache.map" ;
	String KeyList = "isry.sample.redis.service.cache.list" ;

	// Cache 대상 키를 조회 한다. 
	@RequestMapping("/selectrediskey.do")
	public String selectRedisKey(HttpServletRequest request, HttpServletResponse response,String Key) throws Exception {
		/*
		Set<String> listData= redisService.selectKey(Key) ;
		log.debug("SwControler.selectRedis().listData.toString()?"+listData.toString());
		return "selectrediskey.do Running OK !!!";
		*/
		return "";
	}
	
	
	// Cache 데이터를 Map 구조로 입력 한다.
	// 테스트 URL: http://localhost:8880/ISRY_BackEnd/sample/insertredis.do
	@RequestMapping("/insertredis.do")
	public void  insertRedisMap(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
		Map<String, Object> mapData = new HashMap<>();

		mapData.put("데이터11", "값11") ;
		mapData.put("데이터12", "값12") ;
		mapData.put("데이터13", "값13") ;
		int intReturn = redisService.insertRedisMap(KeyMap,mapData) ;
		*/
	}

	//Cache 데이터를 Map구조의 Redis데이터를 조회 한다. 
	@RequestMapping("/selectredis.do")
	public void selectRedisMap(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// Cache 조회 규칙
		// 1차: Redis Cache에서 조회 한다.
		// 2차: Cache에 데이터 미존재 시 DB에서 데이터를 조회 하여 Cache에 저장한다. 
		
		// Redis Cache Key에 해당하는 데이터가 존재 한다면...
		/*
		if ( redisService.getRedisSize(KeyMap) > 0) {
			
			//키값을 전달한다.
			Map<String, Object> MapData= redisService.selectRedisMap(KeyMap);
			log.debug("mapData:::::::"+MapData.toString());

		} else {

			
			// =========================================================== 
			// ==============DB 저장 서비스 호출(현재는 스킵  )  ========= 
			// =========================================================== 
			
			//  ..........................................................
			//  ..........................................................
			//  ................... DB 저장 처리 .........................
			//  ..........................................................
			//  ..........................................................
			
			// =========================================================== 
			// ==============      Cache 등록 처리               ========= 
			// =========================================================== 
			
			// Cache에 Data가 존재 하지 않는 경우 DB 데이터를 조회 하여 Cache에 등록
			
			Map<String, Object> mapData = new HashMap<>();

			mapData.put("데이터11", "값11") ;
			mapData.put("데이터12", "값12") ;
			mapData.put("데이터13", "값13") ;
			
			int intReturn = redisService.insertRedisMap(KeyMap,mapData) ;
		}
		*/
	}
	
	// DB데이터를  List 기반으로 데이터를 저장한다.
	// 테스트 URL: http://localhost:8880/ISRY_BackEnd/sample/insertredislist.do
	@RequestMapping("/insertredislist.do")
	public void  insertRedisList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
		List<Map<String, Object>> listMapData = new ArrayList<>();
		Map<String, Object> mapData1 = new HashMap<>();
		Map<String, Object> mapData2 = new HashMap<>();
		
		// 복수의 맵 객체를 리스트에 저장함. 
		mapData1.put("data11", "value11") ;
		mapData1.put("data12", "value12") ;
		mapData1.put("data13", "value13") ;
		
		mapData2.put("data21", "value21") ;
		mapData2.put("data22", "value22") ;
		mapData2.put("data23", "value23") ;
		
		listMapData.add(mapData1) ;
		listMapData.add(mapData2) ;
		
		int intReturn = redisService.insertRedisList(KeyList,listMapData) ;
		*/
	}
	
	//Cache 데이터를 List기반으로 조회 한다. 
	@RequestMapping("/selectredislist.do")
	public void selectRedisList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
		Map<String, Object> mapData1 = new HashMap<>();
		Map<String, Object> mapData2 = new HashMap<>();
		
		// Redis Cache 서버에 Key값에 해당하는 데이터가 있는지 조회 !
		if ( redisService.getRedisSize(KeyList) > 0) {
			
			// 키값을전달한다.
			List<Map<String, Object>> listData = redisService.selectRedisList(KeyList) ;

			
			for (int i = 0, len = listData.size(); i < len; i++) {

				Map<String, Object> mapData = new HashMap<>();
				mapData= listData.get(i) ;
				log.debug("SwController.selectRedisList().mapData:::"+mapData);;
			}
			
		} else {
			
			// =========================================================== 
			// ==============DB 저장 서비스 호출(현재는 스킵함)  ========= 
			// =========================================================== 
			
			//  ..........................................................
			//  ..........................................................
			//  ................... DB 저장 처리 .........................
			//  ..........................................................
			//  ..........................................................
			
			// =========================================================== 
			// ==============        Cache 등록 처리             ========= 
			// =========================================================== 
			
			// Cache에 Data가 존재 하지 않는 경우 DB 데이터를 조회 하여 Cache에 등록
			List<Map<String, Object>> listMapData = new ArrayList<>();
			
			// 복수의 맵 객체를 리스트에 저장함.
			mapData1.put("data11", "value11") ;
			mapData1.put("data12", "value12") ;
			mapData1.put("data13", "value13") ;
			
			mapData2.put("data21", "value21") ;
			mapData2.put("data22", "value22") ;
			mapData2.put("data23", "value23") ;
			
			listMapData.add(mapData1) ;
			listMapData.add(mapData2) ;
			
			int intReturn = redisService.insertRedisList(KeyList,listMapData) ;
			
		}
		*/
	}
}
