/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @파일명        : UserInstAuthVO.java
 * @프로그램 설명 : 사용자별 기관 권한 VO
 * - 
 * - 
 * @작성자        : Myeong.Jae.Cheol
 * @작성일        : 2023. 2. 19. 
 * @수정자        : Myeong.Jae.Cheol
 * @수정일        : 2023. 2. 19.
 * @수정내용      : 
 * -                
 * -                
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInstAuthVO implements Serializable {
	
	private static final long serialVersionUID = -9028617339214407332L;
	
	private Integer instNo;				// 기관번호
	private String instNm;				// 기관명
	private String groupAuthrtSeCd;		// 그룹권한구분코드
	private String authrtSeCd;			// 권한구분코드
	private String untTaskwkSeCd;		// 단위업무구분코드
	private String instTypeSeCd;		// 기관유형구분코드
	private String sysMngrYn;			// 시스템관리자여부
	private String maistYn;				// 주기관여부

	/**
	 * 기관번호를 설정한다.
	 * 
	 * @param instNo the instNo to set
	 */
	public void setInstNo(Integer instNo) {
		this.instNo = instNo;
	}
	/**
	 * 기관번호를 조회한다.
	 * 
	 * @return the instNo
	 */
	public Integer getInstNo() {
		return instNo;
	}
	
	/**
	 * 기관명을 설정한다.
	 * 
	 * @param instNm the instNm to set
	 */
	public void setInstNm(String instNm) {
		this.instNm = instNm;
	}
	/**
	 * 기관명을 조회한다.
	 * 
	 * @return the instNm
	 */
	public String getInstNm() {
		return instNm;
	}
	
	/**
	 * 그룹권한구분코드를 설정한다.
	 * 
	 * @param groupAuthrtSeCd the groupAuthrtSeCd to set
	 */
	public void setGroupAuthrtSeCd(String groupAuthrtSeCd) {
		this.groupAuthrtSeCd = groupAuthrtSeCd;
	}
	/**
	 * 그룹권한구분코드를 조회한다.
	 * 
	 * @return the groupAuthrtSeCd
	 */
	public String getGroupAuthrtSeCd() {
		return groupAuthrtSeCd;
	}
	
	/**
	 * 권한구분코드를 설정한다.
	 * 
	 * @param authrtSeCd the authrtSeCd to set
	 */
	public void setAuthrtSeCd(String authrtSeCd) {
		this.authrtSeCd = authrtSeCd;
	}
	/**
	 * 권한구분코드를 조회한다.
	 * 
	 * @return the authrtSeCd
	 */
	public String getAuthrtSeCd() {
		return authrtSeCd;
	}
	
	/**
	 * 단위업무구분코드를 설정한다.
	 * 
	 * @param untTaskwkSeCd the untTaskwkSeCd to set
	 */
	public void setUntTaskwkSeCd(String untTaskwkSeCd) {
		this.untTaskwkSeCd = untTaskwkSeCd;
	}
	/**
	 * 단위업무구분코드를 조회한다.
	 * 
	 * @return the untTaskwkSeCd
	 */
	public String getUntTaskwkSeCd() {
		return untTaskwkSeCd;
	}
	
	/**
	 * 기관유형구분코드를 설정한다.
	 * 
	 * @param instTypeSeCd the instTypeSeCd to set
	 */
	public void setInstTypeSeCd(String instTypeSeCd) {
		this.instTypeSeCd = instTypeSeCd;
	}
	/**
	 * 기관유형구분코드를 조회한다.
	 * 
	 * @return the instTypeSeCd
	 */
	public String getInstTypeSeCd() {
		return instTypeSeCd;
	}
	
	/**
	 * 시스템관리자여부(Y/N)를 설정한다.
	 * 
	 * @param sysMngrYn the sysMngrYn to set
	 */
	public void setSysMngrYn(String sysMngrYn) {
		this.sysMngrYn = sysMngrYn;
	}
	/**
	 * 시스템관리자여부(Y/N)를 조회한다.
	 * 
	 * @return the sysMngrYn
	 */
	public String getSysMngrYn() {
		return sysMngrYn;
	}
	
	/**
	 * 주기관여부(Y/N)를 설정한다.
	 * 
	 * @param maistYn the maistYn to set
	 */
	public void setMaistYn(String maistYn) {
		this.maistYn = maistYn;
	}
	/**
	 * 주기관여부(Y/N)를 조회한다.
	 * 
	 * @return the maistYn
	 */
	public String getMaistYn() {
		return maistYn;
	}
	
	/**
	 * 사용자별 기관 권한 VO 에 대한 정보를 문자열로 출력합니다.
	 */
	@Override
	public String toString() {
		return "UserInstAuthVO: { instNo=" + instNo + ", instNm=" + instNm + ", groupAuthrtSeCd=" + groupAuthrtSeCd
				+ ", authrtSeCd=" + authrtSeCd + ", untTaskwkSeCd=" + untTaskwkSeCd + ", instTypeSeCd=" + instTypeSeCd
				+ ", sysMngrYn=" + sysMngrYn + ", maistYn=" + maistYn + " }";
	}
}
