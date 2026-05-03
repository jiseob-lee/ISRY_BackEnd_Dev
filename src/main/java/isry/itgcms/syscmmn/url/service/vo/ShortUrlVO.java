/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.syscmmn.url.service.vo;

import lombok.Data;

/**
 * @파일명        : ShortUrlVo.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.In.Sung
 * @작성일        : 2022. 11. 22. 
 * @수정자        : Lee.In.Sung
 * @수정일        : 2022. 11. 22.
 * @수정내용      : 
 * -                
 * -                
 */
public class ShortUrlVO {
	private String urlMngNo;		/* URL관리번호 */
	private String domnNm;         /* 도메인명 */
	private String orgnlUrlAddr;   /* 원본URL주소 */
	private String methdaTypeNm;   /* 메서드유형명 */
	private String paraDtlCn;      /* 파라미터상세내용 */
	private String frstRgtrId;     /* 최초등록자아이디 */
	private String frstRegDt;      /* 최초등록일시 */
	private String lastMdfrId;     /* 최종수정자아이디 */
	private String lastMdfcnDt;    /* 최종수정일시 */

	public String getUrlMngNo() {
		return urlMngNo;
	}
	public void setUrlMngNo(String urlMngNo) {
		this.urlMngNo = urlMngNo;
	}
	public String getDomnNm() {
		return domnNm;
	}
	public void setDomnNm(String domnNm) {
		this.domnNm = domnNm;
	}
	public String getOrgnlUrlAddr() {
		return orgnlUrlAddr;
	}
	public void setOrgnlUrlAddr(String orgnlUrlAddr) {
		this.orgnlUrlAddr = orgnlUrlAddr;
	}
	public String getMethdaTypeNm() {
		return methdaTypeNm;
	}
	public void setMethdaTypeNm(String methdaTypeNm) {
		this.methdaTypeNm = methdaTypeNm;
	}
	public String getParaDtlCn() {
		return paraDtlCn;
	}
	public void setParaDtlCn(String paraDtlCn) {
		this.paraDtlCn = paraDtlCn;
	}
	public String getFrstRgtrId() {
		return frstRgtrId;
	}
	public void setFrstRgtrId(String frstRgtrId) {
		this.frstRgtrId = frstRgtrId;
	}
	public String getFrstRegDt() {
		return frstRegDt;
	}
	public void setFrstRegDt(String frstRegDt) {
		this.frstRegDt = frstRegDt;
	}
	public String getLastMdfrId() {
		return lastMdfrId;
	}
	public void setLastMdfrId(String lastMdfrId) {
		this.lastMdfrId = lastMdfrId;
	}
	public String getLastMdfcnDt() {
		return lastMdfcnDt;
	}
	public void setLastMdfcnDt(String lastMdfcnDt) {
		this.lastMdfcnDt = lastMdfcnDt;
	}
	
}
