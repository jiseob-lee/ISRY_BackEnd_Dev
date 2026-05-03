/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.util;

import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명        : CommUtils.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Myeong.Sang
 * @작성일        : 2022. 6. 20. 
 * @수정자        : Lee.Myeong.Sang
 * @수정일        : 2022. 6. 20.
 * @수정내용      : 
 * -                
 * -                
 */
public class CommUtils {

	private CommUtils() {}

	/**
	 * @Method명   : getUserId 사용자
	 * @param request
	 * @return
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	public static String getUserId(UserDetailsVO userDetailsVO) {
		//HttpSession session = request.getSession();
		UserDetailsVO loginVO = userDetailsVO; //userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		return userId;
	}
	
	/**
	 * @Method명   : getUntTaskwk 단위업무구분
	 * @param request
	 * @return
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	public static String getUntTaskwk(UserDetailsVO userDetailsVO) {
		//HttpSession session = request.getSession();
		UserDetailsVO loginVO = userDetailsVO; //userLoginService.getLoginSessionVO(request);
		String untTaskwk = "";
		if (loginVO != null && loginVO.getUntTaskwk() != null && !"".equals(loginVO.getUntTaskwk())) {
			untTaskwk = loginVO.getUntTaskwk();
		}
		return untTaskwk;
	}

	/**
	 * @Method명   : getInstNo 기관코드
	 * @param request
	 * @return
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	public static String getInstNo(UserDetailsVO userDetailsVO) {
		//HttpSession session = request.getSession();
		UserDetailsVO loginVO = userDetailsVO; //userLoginService.getLoginSessionVO(request);
		String instNo = "";
		if (loginVO != null && loginVO.getInstNo() != null) {
			instNo = String.valueOf(loginVO.getInstNo());
		}
		return instNo;
	}
	
	/**
	 * @Method명   : getEnfsnNo 종사자번호
	 * @param request
	 * @return
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	public static String getEnfsnNo(UserDetailsVO userDetailsVO) {
		//HttpSession session = request.getSession();
		UserDetailsVO loginVO = userDetailsVO; //userLoginService.getLoginSessionVO(request);
		String enfsnNo = "";
		if (loginVO != null && loginVO.getEnfsnNo() != null) {
			enfsnNo = String.valueOf(loginVO.getEnfsnNo());
		}
		return enfsnNo;
	}
	
	/**
	 * @Method명   : decToMask - 마스킹(이름)
	 * @param scpDb
	 * @param result
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 6. 20. 
	 * @Method설명 :
	 */
	public static String decToMask(String encString) throws Exception {
		
		//ScpDb scpDb = new ScpDb();

		//String decString = scpDb.scpDecB64(encString);

		return Masking.nameMasking(encString);
	}

	/**
	 * @Method명   : decToPhoneFormat - 마스킹(이름)
	 * @param scpDb
	 * @param result
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 6. 20. 
	 * @Method설명 :
	 */
	public static String decToPhoneFormat(String encString) {
		//ScpDb scpDb = new ScpDb();

		//String decString = scpDb.scpDecB64(encString);
		
		return Formatter.phoneFormat(encString, 1);
		
	}

	/**
	 * @Method명   : decToMaskRrno - 주민번호 마스킹
	 * @param scpDb
	 * @param result
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 6. 20. 
	 * @Method설명 :
	 */
	public static String decToMaskRrno(String encString) {
		//ScpDb scpDb = new ScpDb();
		
		//String decString = scpDb.scpDecB64(encString);
		
		return Masking.rrnoMasking(encString);
		
	}

	/**
	 * @Method명   : getAuthrt - 그룹권한
	 * @param scpDb
	 * @param result
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2022. 6. 20. 
	 * @Method설명 :
	 */
	public static String getAuthrt(String groupAuthrt) {

		if (groupAuthrt.equals("100") || groupAuthrt.equals("110") || groupAuthrt.equals("120") || groupAuthrt.equals("200") ) {  //200
			return "1";
		}else if (groupAuthrt.equals("210") || groupAuthrt.equals("310")) {
			return "2";
		}else if (groupAuthrt.equals("220") || groupAuthrt.equals("230") || groupAuthrt.equals("320") || groupAuthrt.equals("330")) {
			return "3";
		}else if (groupAuthrt.equals("140") || groupAuthrt.equals("240") || groupAuthrt.equals("340")) {
			return "4";
		}
		return "4";
	}

}
