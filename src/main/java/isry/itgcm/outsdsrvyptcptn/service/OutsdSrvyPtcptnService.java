/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.itgcm.outsdsrvyptcptn.service;

import java.util.Map;

/**
 * @파일명        : OutsdSrvyPtcptnService.java
 * @프로그램 설명 :
 * -
 * -
 * @작성자        : Kim.Seong.Ok
 * @작성일        : 2022. 11. 21.
 * @수정자        : Kim.Seong.Ok
 * @수정일        : 2022. 11. 21.
 * @수정내용      :
 * -
 * -
 */
public interface OutsdSrvyPtcptnService {

	/**
	 * @Method명   : getSendMsg
	 * @param outsdSrvyPtcptnParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 21.
	 * @Method설명 :
	 */
	String getSendMsg(Map<String, String> outsdSrvyPtcptnParam);

	/**
	 * @Method명   : getSrvySendMsg
	 * @param outsdSrvyPtcptnParam
	 * @return
	 * @작성자     : Tae.Soo.Song
	 * @작성일     : 2023. 8. 16.
	 * @Method설명 :
	 */
	String getSrvySendMsg(Map<String, String> outsdSrvyPtcptnParam);

}
