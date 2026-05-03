/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.syscmmn.email.vo;

import java.util.List;
import java.util.Map;

/**
 * @파일명        : EmailMessageVO.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2023. 4. 6. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2023. 4. 6.
 * @수정내용      : 
 * -                
 * -                
 */
public class EmailMessageVO {
	
	private String userId;
	private String contents;
	private String sender;
	private String senderName;
	private String title;
	private String reserveYN;
	private String reserveTime;
	private List<Map<String, String>> listReceiver;
	
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the contents
	 */
	public String getContents() {
		return contents;
	}
	/**
	 * @param contents the contents to set
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}
	/**
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}
	/**
	 * @param sender the sender to set
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}
	/**
	 * @return the senderName
	 */
	public String getSenderName() {
		return senderName;
	}
	/**
	 * @param senderName the senderName to set
	 */
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the reserveYN
	 */
	public String getReserveYN() {
		return reserveYN;
	}
	/**
	 * @param reserveYN the reserveYN to set
	 */
	public void setReserveYN(String reserveYN) {
		this.reserveYN = reserveYN;
	}
	/**
	 * @return the reserveTime
	 */
	public String getReserveTime() {
		return reserveTime;
	}
	/**
	 * @param reserveTime the reserveTime to set
	 */
	public void setReserveTime(String reserveTime) {
		this.reserveTime = reserveTime;
	}
	/**
	 * @return the listReceiver
	 */
	public List<Map<String, String>> getListReceiver() {
		return listReceiver;
	}
	/**
	 * @param listReceiver the listReceiver to set
	 */
	public void setListReceiver(List<Map<String, String>> listReceiver) {
		this.listReceiver = listReceiver;
	}
}
