/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부 및 위기 청소년 통합 지원 시스템 관리팀의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/

package isry.sample.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.spring.JSONDataView;
//import com.penta.scpdb.ScpDbAgent;

import egovframework.com.cmm.service.EgovProperties;

import isry.base.IsryBaseController;

/**
 * 
 * @파일명 : SwController.java
 * @프로그램 설명 : - 솔루션 테스트를 위한 웹 컨트롤러 입니다
 * @작성자 : Song.Young.Il
 * @작성일 : 2022. 01. 05.
 * @수정자 : Song.Young.Il
 * @수정일 : 2022. 01. 05.
 * @수정내용 : - -
 */
@Controller
@RequestMapping("/sample")
public class SwController extends IsryBaseController {

	//@Resource
	//private SampleService sampleService;

	//@Resource
	//private CmnCodeService cmnCodeService;
	
	/**
	 * 
	 * @Method명 : testDamo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @작성자 : Song.Young.Il
	 * @작성일 : 2022. 1. 12.
	 * @Method설명 : DB 암호화 솔루션 디아모 기본 메서드를 테스트 합니다.
	 */
	@RequestMapping("/testDamo.do")
	public View testDamo(HttpServletRequest request, HttpServletResponse response) throws Exception {


		//byte[] byteInput = { (byte) 0xED, (byte) 0x99, (byte) 0x8D, (byte) 0xEA, (byte) 0xB8, (byte) 0xB8, (byte) 0xEB,
		//		(byte) 0x8F, (byte) 0x99, 0x2D, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37 };
		
		//String strInputPlain = new String(byteInput, "UTF-8"); /* Hong Gil Dong ( U+D640 U+AE38 U+B3D9 ) "-1234567" */
		
//		String strInputPlain = "ISRY SYSTEM 입니다.";
//		String strEnc = "";
//		String strDec = "";
//
//		int ret;
//
//		/* ################################################################### */
//		/* #################     DB 암호화 파일 디렉토리 위치      ########### */
//		/* ################################################################### */
//
//		
//		// ##################### SAMPLE FILE #################################
//		// 저장정보 = "C:\\temp\\damo\\scpdb_agent.ini"; /* scpdb_agent.ini
//		// ###################################################################
//		
//		String strDamoEncodingInfo   = EgovProperties.getProperty("globals", "isry.globals.damo.info");
//		String strDamoConfigFilePath = EgovProperties.getProperty("globals", "isry.globals.damo.path");
//
//		/* ################################################################### */
//		/* ##############              참고 정보               ############### */
//		/* ################################################################### */
//		
//		log.debug(".strDamoEncodingInfo:::" + strDamoEncodingInfo );
//		log.debug(".strDamoConfigFilePath:::" + strDamoConfigFilePath );
//		
//		log.debug("JAVA CLASS PATH : " + System.getProperty("java.class.path"));
//		log.debug("JAVA LIBRARY PATH : " + System.getProperty("java.library.path"));
//		
//		/* DAMO SCP : Create ScpDbAgent object */
//		
//		log.debug(" ");
//		log.debug(" strDamoEncodingInfo:::"+strDamoEncodingInfo);
//		
//		log.debug(" ");
//		ScpDbAgent agt = new ScpDbAgent();
//
//		log.debug(" ");
//		log.debug(" !!!!!!!!!!!!!!!!!!!!! DAMO SCP Agent실행 끝 !!!!!!!!!!!!!!");
//		log.debug(" ");
//		
//		/*****************************************************************************/
//		
//		strEnc = agt.ScpEncStr(strDamoConfigFilePath, strDamoEncodingInfo, strInputPlain);
//		log.debug("[java] ScpEncStr : " + strEnc);
//
//		strDec = agt.ScpDecStr(strDamoConfigFilePath, strDamoEncodingInfo, strEnc);
//		log.debug("[java] ScpDecStr : " + strDec);
//
//		strEnc = agt.ScpEncB64(strDamoConfigFilePath, strDamoEncodingInfo, strInputPlain);
//		log.debug("[java] ScpEncB64 : " + strEnc);
//
//		strDec = agt.ScpDecB64(strDamoConfigFilePath, strDamoEncodingInfo, strEnc);
//		log.debug("[java] ScpDecB64 : " + strDec);
//
//		/*
//		 * strEnc = agt.ScpEncRRNB64( strDamoConfigFilePath, strDamoEncodingInfo,
//		 * strInputPlain ); log.debug("[java] ScpEncRRNB64 : " + strEnc); strDec =
//		 * agt.ScpDecB64( strDamoConfigFilePath, strDamoEncodingInfo, strEnc);
//		 * log.debug("[java] ScpDecB64 : " + strDec);
//		 */
//
//		/*
//		 * DAMO SCP API HASH function HASH Algorithm ID : SHA1 = 70 SHA256 = 71 SHA384 =
//		 * 72 SHA512 = 73 HAS160 = 74 MD5 = 75
//		 */
//
//		strEnc = agt.ScpHashStr(strDamoConfigFilePath, 71, strInputPlain);
//		log.debug("[java] ScpHashStr String : " + strEnc);
//
//		strEnc = agt.ScpHashB64(strDamoConfigFilePath, 71, strInputPlain);
//		log.debug("[java] ScpHashB64 String : " + strEnc);
//		/*
//		 * strEnc = agt.ScpKeyHashStr( strDamoConfigFilePath, "KEY2", strInputPlain );
//		 * log.debug("[java] ScpKeyHashStr String : " + strEnc); strEnc =
//		 * agt.ScpKeyHashB64( strDamoConfigFilePath, "KEY2", strInputPlain );
//		 * log.debug("[java] ScpKeyHashB64 String : " + strEnc);
//		 */
//		/*****************************************************************************/
//
//		strEnc = agt.ScpEncStr(strDamoConfigFilePath, strDamoEncodingInfo, strInputPlain, "EUC-KR");
//		log.debug("[java] ScpEncStr EUC-KR : " + strEnc);
//		strDec = agt.ScpDecStr(strDamoConfigFilePath, strDamoEncodingInfo, strEnc, "EUC-KR");
//		log.debug("[java] ScpDecStr EUC-KR : " + strDec);
//		strEnc = agt.ScpEncB64(strDamoConfigFilePath, strDamoEncodingInfo, strInputPlain, "EUC-KR");
//		log.debug("[java] ScpEncB64 EUC-KR : " + strEnc);
//		strDec = agt.ScpDecB64(strDamoConfigFilePath, strDamoEncodingInfo, strEnc, "EUC-KR");
//		log.debug("[java] ScpDecB64 EUC-KR : " + strDec);
//		/*
//		 * strEnc = agt.ScpEncRRNB64( strDamoConfigFilePath, strDamoEncodingInfo,
//		 * strInputPlain, "EUC-KR"); log.debug("[java] ScpEncRRNB64 EUC-KR : " +
//		 * strEnc); strDec = agt.ScpDecB64( strDamoConfigFilePath, strDamoEncodingInfo,
//		 * strEnc, "EUC-KR"); log.debug("[java] ScpDecB64 EUC-KR : " + strDec);
//		 */
//
//		strEnc = agt.ScpHashStr(strDamoConfigFilePath, 71, strInputPlain, "EUC-KR");
//		log.debug("[java] ScpHashStr EUC-KR : " + strEnc);
//		strEnc = agt.ScpHashB64(strDamoConfigFilePath, 71, strInputPlain, "EUC-KR");
//		log.debug("[java] ScpHashB64 EUC-KR : " + strEnc);
//		/*
//		 * strEnc = agt.ScpKeyHashStr( strDamoConfigFilePath, "KEY2", strInputPlain,
//		 * "EUC-KR" ); log.debug("[java] ScpKeyHashStr EUC-KR : " + strEnc); strEnc =
//		 * agt.ScpKeyHashB64( strDamoConfigFilePath, "KEY2", strInputPlain, "EUC-KR" );
//		 * log.debug("[java] ScpKeyHashB64 EUC-KR : " + strEnc);
//		 */
//		/*****************************************************************************/
//
//		strEnc = agt.ScpEncStr(strDamoConfigFilePath, strDamoEncodingInfo, strInputPlain, "UTF-8");
//		log.debug("[java] ScpEncStr UTF-8 : " + strEnc);
//		strDec = agt.ScpDecStr(strDamoConfigFilePath, strDamoEncodingInfo, strEnc, "UTF-8");
//		log.debug("[java] ScpDecStr UTF-8 : " + strDec);
//		strEnc = agt.ScpEncB64(strDamoConfigFilePath, strDamoEncodingInfo, strInputPlain, "UTF-8");
//		log.debug("[java] ScpEncB64 UTF-8 : " + strEnc);
//		strDec = agt.ScpDecB64(strDamoConfigFilePath, strDamoEncodingInfo, strEnc, "UTF-8");
//		log.debug("[java] ScpDecB64 UTF-8 : " + strDec);
//		/*
//		 * strEnc = agt.ScpEncRRNB64( strDamoConfigFilePath, strDamoEncodingInfo,
//		 * strInputPlain, "UTF-8"); log.debug("[java] ScpEncRRNB64 UTF-8 : " + strEnc);
//		 * strDec = agt.ScpDecB64( strDamoConfigFilePath, strDamoEncodingInfo, strEnc,
//		 * "UTF-8"); log.debug("[java] ScpDecB64 UTF-8 : " + strDec);
//		 */
//
//		strEnc = agt.ScpHashStr(strDamoConfigFilePath, 71, strInputPlain, "UTF-8");
//		log.debug("[java] ScpHashStr UTF-8 : " + strEnc);
//		
//		strEnc = agt.ScpHashB64(strDamoConfigFilePath, 71, strInputPlain, "UTF-8");
//		log.debug("[java] ScpHashB64 UTF-8 : " + strEnc);
//		/*
//		 * strEnc = agt.ScpKeyHashStr( strDamoConfigFilePath, "KEY2", strInputPlain,
//		 * "UTF-8" ); log.debug("[java] ScpKeyHashStr UTF-8 : " + strEnc); strEnc =
//		 * agt.ScpKeyHashB64( strDamoConfigFilePath, "KEY2", strInputPlain, "UTF-8" );
//		 * log.debug("[java] ScpKeyHashB64 UTF-8 : " + strEnc);
//		 */
//		/*****************************************************************************/
//
//		byte[] enc = null;
//		byte[] dec = null;
//
//		enc = agt.ScpEncStr(strDamoConfigFilePath, strDamoEncodingInfo, strInputPlain.getBytes("UTF-8"));
//		strEnc = new String(enc);
//		log.debug("[Java] ScpEncStr Byte UTF-8 : " + strEnc);
//		
//		dec = agt.ScpDecStr(strDamoConfigFilePath, strDamoEncodingInfo, enc);
//		strDec = new String(dec, "UTF-8");
//		log.debug("[Java] ScpDecStr Byte UTF-8 : " + strDec);
//		
//		enc = agt.ScpEncB64(strDamoConfigFilePath, strDamoEncodingInfo, strInputPlain.getBytes("UTF-8"));
//		strEnc = new String(enc);
//		log.debug("[Java] ScpEncB64 Byte UTF-8 : " + strEnc);
//
//		dec = agt.ScpDecB64(strDamoConfigFilePath, strDamoEncodingInfo, enc);
//		strDec = new String(dec, "UTF-8");
//		log.debug("[Java] ScpDecB64 Byte UTF-8 : " + strDec);
//
//		enc = agt.ScpHashStr(strDamoConfigFilePath, 71, strInputPlain.getBytes("UTF-8"));
//		strEnc = new String(enc);
//		log.debug("[Java] ScpHashStr Byte UTF-8 : " + strEnc);
//		enc = agt.ScpHashB64(strDamoConfigFilePath, 71, strInputPlain.getBytes("UTF-8"));
//		strEnc = new String(enc);
//		log.debug("[Java] ScpHashB64 Byte UTF-8 : " + strEnc);
//
//		enc = agt.ScpEncStr(strDamoConfigFilePath, strDamoEncodingInfo, strInputPlain.getBytes("MS949"));
//		strEnc = new String(enc);
//		log.debug("[Java] ScpEncStr Byte MS949 : " + strEnc);
//
//		dec = agt.ScpDecStr(strDamoConfigFilePath, strDamoEncodingInfo, enc);
//		strDec = new String(dec, "MS949");
//		log.debug("[Java] ScpDecStr Byte MS949 : " + strDec);
//
//		enc = agt.ScpEncB64(strDamoConfigFilePath, strDamoEncodingInfo, strInputPlain.getBytes("MS949"));
//		strEnc = new String(enc);
//		log.debug("[Java] ScpEncB64 Byte MS949 : " + strEnc);
//
//		dec = agt.ScpDecB64(strDamoConfigFilePath, strDamoEncodingInfo, enc);
//		strDec = new String(dec, "MS949");
//		log.debug("[Java] ScpDecB64 Byte MS949 : " + strDec);
//
//		enc = agt.ScpHashStr(strDamoConfigFilePath, 71, strInputPlain.getBytes("MS949"));
//		strEnc = new String(enc);
//		log.debug("[Java] ScpHashStr Byte MS949 : " + strEnc);
//		enc = agt.ScpHashB64(strDamoConfigFilePath, 71, strInputPlain.getBytes("MS949"));
//		strEnc = new String(enc);
//		log.debug("[Java] ScpHashB64 Byte MS949 : " + strEnc);

		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("testDamo()", "Success!!!");
		return new JSONDataView(true, mapParam);

	}

	/**
	 * 
	 * @Method명 : testDamo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @작성자 : Song.Young.Il
	 * @작성일 : 2022. 1. 12.
	 * @Method설명 : DB 암호화 솔루션 디아모 파일 암호화 테스트입니다.
	 */
	@RequestMapping("/testEncFile.do")
	public View testDamoEncFile(HttpServletRequest request, HttpServletResponse response) throws Exception {

		
		/* ################################################################### */
		/* ################# DB 암호화 파일 디렉토리 위치 ############### */
		/* ################################################################### */
//		String strDamoEncodingInfo = EgovProperties.getProperty("globals", "isry.globals.damo.info");
//		String strDamoConfigFilePath = EgovProperties.getProperty("globals", "isry.globals.damo.path");
//
//		
//		/* ################################################################### */
//		/* ################# 참고 정보 ############### */
//		/* ################################################################### */
//
//		log.debug("JAVA CLASS PATH : " + System.getProperty("java.class.path"));
//		log.debug("JAVA LIBRARY PATH : " + System.getProperty("java.library.path"));
//
//		int ret;
//
//		/* DAMO SCP : Create ScpDbAgent object */
//		ScpDbAgent agt = new ScpDbAgent();
//
//		String strSourceFile = EgovProperties.getProperty("globals", "isry.globals.damo.sourcefile.path");
//		String strEncFile = EgovProperties.getProperty("globals", "isry.globals.damo.encfile.path");
//
//		ret = agt.ScpEncFile(strDamoConfigFilePath, strDamoEncodingInfo, strSourceFile, strEncFile);
//
//		log.debug("[java] ScpEncFile : " + ret);

		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("testDamoEncFile()", "Success!!!");
		return new JSONDataView(true, mapParam);

	}

	/**
	 * 
	 * @Method명 : testDamo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @작성자 : Song.Young.Il
	 * @작성일 : 2022. 1. 12.
	 * @Method설명 : DB 암호화 솔루션 디아모- 파일 복호화 테스트 입니다.
	 */
	@RequestMapping("/testDecFile.do")
	public View testDamoDecFile(HttpServletRequest request, HttpServletResponse response) throws Exception {

		
		/* ################################################################### */
		/* ################# DB 암호화 파일 디렉토리 위치 ############### */
		/* ################################################################### */
//		String strDamoEncodingInfo = EgovProperties.getProperty("globals", "isry.globals.damo.info");
//		String strDamoConfigFilePath = EgovProperties.getProperty("globals", "isry.globals.damo.path");
//
//		
//		/* ################################################################### */
//		/* #################              참고 정보            ############### */
//		/* ################################################################### */
//
//		log.debug("JAVA CLASS PATH : " + System.getProperty("java.class.path"));
//		log.debug("JAVA LIBRARY PATH : " + System.getProperty("java.library.path"));
//
//		int ret;
//
//		/* DAMO SCP : Create ScpDbAgent object */
//		ScpDbAgent agt = new ScpDbAgent();
//
//		String strEncFile = EgovProperties.getProperty("globals", "isry.globals.damo.encfile.path");
//		String strDecFile = EgovProperties.getProperty("globals", "isry.globals.damo.decfile.path");
//
//		ret = agt.ScpDecFile(strDamoConfigFilePath, strDamoEncodingInfo, strEncFile, strDecFile);
//
//		log.debug("[java] ScpDecFile : " + ret);

		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("testDamoDecFile()", "Success!!!");
		return new JSONDataView(true, mapParam);

	}
	

	public static String getDateTime(String fm) {

		Date today = new Date();
		//System.out.println(today);

		SimpleDateFormat date = new SimpleDateFormat(fm, Locale.KOREAN);

		return date.format(today);
	}	

	

}
