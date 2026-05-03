/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.penta.scpdb.ScpDbAgent;

//import egovframework.com.cmm.service.EgovProperties;

/**
 * @파일명        : ScpDb.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Ji.Seob.Lee
 * @작성일        : 2022. 2. 7. 
 * @수정자        : Ji.Seob.Lee
 * @수정일        : 2022. 2. 7.
 * @수정내용      : 
 * -                
 * -                
 */
public class ScpDb {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	//private final String strDamoEncodingInfo = EgovProperties.getProperty("globals", "isry.globals.damo.info");
	//private final String strDamoConfigFilePath = EgovProperties.getProperty("globals", "isry.globals.damo.path");

	//private final ScpDbAgent agt = new ScpDbAgent();
	
	public ScpDb() {

		/* ################################################################### */
		/* #################     DB 암호화 파일 디렉토리 위치      ########### */
		/* ################################################################### */

		
		// ##################### SAMPLE FILE #################################
		// 저장정보 = "C:\\temp\\damo\\scpdb_agent.ini"; /* scpdb_agent.ini
		// ###################################################################
		

		/* ################################################################### */
		/* ##############              참고 정보               ############### */
		/* ################################################################### */
		
		//log.debug(".strDamoEncodingInfo:::" + strDamoEncodingInfo );
		//log.debug(".strDamoConfigFilePath:::" + strDamoConfigFilePath );
		
		//log.debug("JAVA CLASS PATH : " + System.getProperty("java.class.path"));
		//log.debug("JAVA LIBRARY PATH : " + System.getProperty("java.library.path"));
		
		/* DAMO SCP : Create ScpDbAgent object */
		
		//log.debug(" ");
		//log.debug(" strDamoEncodingInfo:::"+strDamoEncodingInfo);
		
		//log.debug(" ");
		//log.debug(" !!!!!!!!!!!!!!!!!!!!! DAMO SCP Agent실행 끝 !!!!!!!!!!!!!!");
		//log.debug(" ");
		
		/*****************************************************************************/
	}
	
	// 양방향 암호화 (아리아 256)
	public String scpEncB64(String strInputPlain) {

		//if (strInputPlain == null || "".equals(strInputPlain) || "null".equals(strInputPlain)) {
			//return "";
		//}
		
		//String strEnc = agt.ScpEncB64(strDamoConfigFilePath, strDamoEncodingInfo, strInputPlain);
		//log.debug("[java] ScpEncB64 : " + strEnc);

		return strInputPlain;
	}
	
	// 양방향 복호화 (아리아 256)
	public String scpDecB64(String strEnc) {
		//log.debug("#### strEnc : " + strEnc);
		if (strEnc == null || "".equals(strEnc) || "null".equals(strEnc)) {
			//log.debug("#### strEnc is empty.");
			return "";
		}
		
		if (strEnc.startsWith("____")) {
			return strEnc.substring(4);
		}
		
		String strDec = "";
		
		//try {
			//strDec = agt.ScpDecB64(strDamoConfigFilePath, strDamoEncodingInfo, strEnc);
		//} catch (Exception e) {
			//e.printStackTrace();
			//return strEnc;
		//}
		
		//String strDec = agt.ScpDecB64(strDamoConfigFilePath, strDamoEncodingInfo, strEnc);
		//log.debug("[java] ScpDecB64 : " + strDec);
		
		return strEnc;
	}

	// 단방향 암호화 (Sha 512)
	public String scpHashB64(String strInputPlain) {
		
		if (strInputPlain == null || "".equals(strInputPlain)) {
			return "";
		}
		
		//String strEnc = agt.ScpHashB64(strDamoConfigFilePath, 73, strInputPlain);
		//log.debug("[java] ScpHashB64 String : " + strEnc);
		
		return strInputPlain;
	}

	// 이관된 데이터 암호화 처리 메서드
	public void encriptPersonalInfo() {
		
	}

	public static void main(String[] args) {
		//ScpDb scpDb = new ScpDb();
		//String strInputPlain = "김고은";
		//System.out.println("tls7808!@#");
		//scpDb.scpHashB64("mogef123!");
		//-- pvLNDYspPy6ZSyFnTp8IMw==
		//-- SJ+Q7AOWrQkSMdaDhPRX8g==

		//String strEnc = scpDb.scpEncB64(strInputPlain);
		//String strEnc = "SJ+Q7AOWrQkSMdaDhPRX8g==";
		//String s = scpDb.scpDecB64(strEnc);
		//System.out.println(s);
		
	}
	
}
