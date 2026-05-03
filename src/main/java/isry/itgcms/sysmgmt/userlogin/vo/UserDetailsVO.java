/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userlogin.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import org.springframework.data.redis.core.RedisHash;

import isry.itgcms.sysmgmt.userauth.vo.UserInstAuthVO;

/**
 * @파일명        : UserDetailsVO.java
 * @프로그램 설명 : 통합 로그인
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 6. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 6.
 * @수정내용      : 
 * -                
 * -                
 */
//@Entity
//@RedisHash("UserDetailsVO")
public class UserDetailsVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 623281623577796750L;
	
	private String id;
	private String pass;
	private String userName;
	
	private Integer orgCode;
	private String orgName;
	private String engCtpvNm;
	private String ctpvNm;
	private String sggCd;
	private String sggNm;
	
	private String rgnSeCd;
	
	private String ip;  // 접속 아이피
	
	private String birthdate;  // 생년월일
	private String gender;  // 성별
	private String email;  // 개인 이메일 주소
	private String mobile;  // 개인 휴대폰 번호
	private String age;  // 나이
	private String memberType;  // 회원 종류 (종사자, 기관, 청소년, 학부모)
	private String agencyContacts;  // 기관 연락처
	private String lastLoginTime;  // 최근 로그인 일시
	private String untTaskwkSeCd;  // 단위 업무 코드
	private String topMenuNo; // 최상위 메뉴 번호
	private String certificate;  // 인증서 로그인 여부
	
	private String untTaskwk;  // 현재 선택된 단위 시스템 코드
	
	private String lgnScsYn;  // 로그인 성공 여부
	
	private String enfsnNo;  // 종사자 번호
	private String enfsnRoleSeCd;  // 종사자의 역할구분코드
	
	private Integer userInstNo;  // 사용자 기관번호
	private String yngbgsPrtcrNo;  // 청소년보호자번호
	private String indvIdntfcNo;  // 개인식별번호
	
	private String instTypeSeCd;  // 기관유형구분코드
	
	private Integer instNo;  //  기관 번호
	private String instNm;  //  기관명
	
	private String wrdTelno;  // 유선전화번호
	private String sidoNm;  // 시도명
	private String sigunguNm;  // 시군구명
	
	private String deptCd;  // 부서코드
	private String deptNm;  // 부서명
	
	private String sessionId;
	
	private String managerYn;
	
	private String groupAuthrtSeCd = "";	// 그룹권한구분코드
	
	private String authrtSeCd = "";			// 권한구분코드
	
	private List<UserInstAuthVO> instAuthList;		// 기관 권한 목록
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the pass
	 */
	public String getPass() {
		return pass;
	}
	/**
	 * @param pass the pass to set
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public String toString() {
		return this.id;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the orgCode
	 */
	public Integer getOrgCode() {
		return orgCode;
	}
	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(Integer orgCode) {
		this.orgCode = orgCode;
	}
	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}
	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	/**
	 * @return the engCtpvNm
	 */
	public String getEngCtpvNm() {
		return engCtpvNm;
	}
	/**
	 * @param engCtpvNm the engCtpvNm to set
	 */
	public void setEngCtpvNm(String engCtpvNm) {
		this.engCtpvNm = engCtpvNm;
	}
	/**
	 * @return the ctpvNm
	 */
	public String getCtpvNm() {
		return ctpvNm;
	}
	/**
	 * @param ctpvNm the ctpvNm to set
	 */
	public void setCtpvNm(String ctpvNm) {
		this.ctpvNm = ctpvNm;
	}
	/**
	 * @return the ctpvSggCd
	 */
	public String getSggCd() {
		return sggCd;
	}
	/**
	 * @param ctpvSggCd the ctpvSggCd to set
	 */
	public void setSggCd(String sggCd) {
		this.sggCd = sggCd;
	}
	/**
	 * @return the sggNm
	 */
	public String getSggNm() {
		return sggNm;
	}
	/**
	 * @param sggNm the sggNm to set
	 */
	public void setSggNm(String sggNm) {
		this.sggNm = sggNm;
	}
	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * @return the birthdate
	 */
	public String getBirthdate() {
		return birthdate;
	}
	/**
	 * @param birthdate the birthdate to set
	 */
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the age
	 */
	public String getAge() {
		return age;
	}
	/**
	 * @param age the age to set
	 */
	public void setAge(String age) {
		this.age = age;
	}
	/**
	 * @return the agencyContact
	 */
	public String getAgencyContacts() {
		return agencyContacts;
	}
	/**
	 * @param agencyContact the agencyContact to set
	 */
	public void setAgencyContacts(String agencyContacts) {
		this.agencyContacts = agencyContacts;
	}
	/**
	 * @return the memberType
	 */
	public String getMemberType() {
		return memberType;
	}
	/**
	 * @param memberType the memberType to set
	 */
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
	/**
	 * @return the rgnSeCd
	 */
	public String getRgnSeCd() {
		return rgnSeCd;
	}
	/**
	 * @param rgnSeCd the rgnSeCd to set
	 */
	public void setRgnSeCd(String rgnSeCd) {
		this.rgnSeCd = rgnSeCd;
	}
	/**
	 * @return the lastLoginTime
	 */
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	/**
	 * @param lastLoginTime the lastLoginTime to set
	 */
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	/**
	 * @return the untTaskwkSeCd
	 */
	public String getUntTaskwkSeCd() {
		return untTaskwkSeCd;
	}
	/**
	 * @param untTaskwkSeCd the untTaskwkSeCd to set
	 */
	public void setUntTaskwkSeCd(String untTaskwkSeCd) {
		this.untTaskwkSeCd = untTaskwkSeCd;
	}
	/**
	 * @return the topMenuNo
	 */
	public String getTopMenuNo() {
		return topMenuNo;
	}
	/**
	 * @param topMenuNo the topMenuNo to set
	 */
	public void setTopMenuNo(String topMenuNo) {
		this.topMenuNo = topMenuNo;
	}
	/**
	 * @return the certificate
	 */
	public String getCertificate() {
		return certificate;
	}
	/**
	 * @param certificate the certificate to set
	 */
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}
	/**
	 * @return the untTaskwk
	 */
	public String getUntTaskwk() {
		return untTaskwk;
	}
	/**
	 * @param untTaskwk the untTaskwk to set
	 */
	public void setUntTaskwk(String untTaskwk) {
		this.untTaskwk = untTaskwk;
	}
	/**
	 * @return the lgnScsYn
	 */
	public String getLgnScsYn() {
		return lgnScsYn;
	}
	/**
	 * @param lgnScsYn the lgnScsYn to set
	 */
	public void setLgnScsYn(String lgnScsYn) {
		this.lgnScsYn = lgnScsYn;
	}
	/**
	 * @return the enfsnNo
	 */
	public String getEnfsnNo() {
		return enfsnNo;
	}
	/**
	 * @param enfsnNo the enfsnNo to set
	 */
	public void setEnfsnNo(String enfsnNo) {
		this.enfsnNo = enfsnNo;
	}
	/**
	 * @return the enfsnRoleSeCd
	 */
	public String getEnfsnRoleSeCd() {
		return enfsnRoleSeCd;
	}
	/**
	 * @param enfsnRoleSeCd the enfsnRoleSeCd to set
	 */
	public void setEnfsnRoleSeCd(String enfsnRoleSeCd) {
		this.enfsnRoleSeCd = enfsnRoleSeCd;
	}
	/**
	 * @return the instNo
	 */
	public Integer getInstNo() {
		return instNo;
	}
	/**
	 * @param instNo the instNo to set
	 */
	public void setInstNo(Integer instNo) {
		this.instNo = instNo;
	}
	/**
	 * @return the wrdTelno
	 */
	public String getWrdTelno() {
		return wrdTelno;
	}
	/**
	 * @param wrdTelno the wrdTelno to set
	 */
	public void setWrdTelno(String wrdTelno) {
		this.wrdTelno = wrdTelno;
	}
	/**
	 * @return the sidoNm
	 */
	public String getSidoNm() {
		return sidoNm;
	}
	/**
	 * @param sidoNm the sidoNm to set
	 */
	public void setSidoNm(String sidoNm) {
		this.sidoNm = sidoNm;
	}
	/**
	 * @return the sigunguNm
	 */
	public String getSigunguNm() {
		return sigunguNm;
	}
	/**
	 * @param sigunguNm the sigunguNm to set
	 */
	public void setSigunguNm(String sigunguNm) {
		this.sigunguNm = sigunguNm;
	}
	/**
	 * @return the deptCd
	 */
	public String getDeptCd() {
		return deptCd;
	}
	/**
	 * @param deptCd the deptCd to set
	 */
	public void setDeptCd(String deptCd) {
		this.deptCd = deptCd;
	}
	/**
	 * @return the deptNm
	 */
	public String getDeptNm() {
		return deptNm;
	}
	/**
	 * @param deptNm the deptNm to set
	 */
	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}
	/**
	 * @return the instTypeSeCd
	 */
	public String getInstTypeSeCd() {
		return instTypeSeCd;
	}
	/**
	 * @param instTypeSeCd the instTypeSeCd to set
	 */
	public void setInstTypeSeCd(String instTypeSeCd) {
		this.instTypeSeCd = instTypeSeCd;
	}
	/**
	 * @return the userInstNo
	 */
	public Integer getUserInstNo() {
		return userInstNo;
	}
	/**
	 * @param userInstNo the userInstNo to set
	 */
	public void setUserInstNo(Integer userInstNo) {
		this.userInstNo = userInstNo;
	}
	/**
	 * @return the yngbgsPrtcrNo
	 */
	public String getYngbgsPrtcrNo() {
		return yngbgsPrtcrNo;
	}
	/**
	 * @param yngbgsPrtcrNo the yngbgsPrtcrNo to set
	 */
	public void setYngbgsPrtcrNo(String yngbgsPrtcrNo) {
		this.yngbgsPrtcrNo = yngbgsPrtcrNo;
	}
	/**
	 * @return the indvIdntfcNo
	 */
	public String getIndvIdntfcNo() {
		return indvIdntfcNo;
	}
	/**
	 * @param indvIdntfcNo the indvIdntfcNo to set
	 */
	public void setIndvIdntfcNo(String indvIdntfcNo) {
		this.indvIdntfcNo = indvIdntfcNo;
	}
	/**
	 * @return the instNm
	 */
	public String getInstNm() {
		return instNm;
	}
	/**
	 * @param instNm the instNm to set
	 */
	public void setInstNm(String instNm) {
		this.instNm = instNm;
	}
	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}
	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Map<String, Object> getMap() {
		Map<String, Object> map = new HashMap<>();

		map.put("id", id);
		map.put("pass", pass);
		map.put("userName", userName);
		
		map.put("orgCode", orgCode);
		map.put("orgName", orgName);
		map.put("engCtpvNm", engCtpvNm);
		map.put("ctpvNm", ctpvNm);
		map.put("sggCd", sggCd);
		map.put("sggNm", sggNm);
		
		map.put("rgnSeCd", rgnSeCd);
		
		map.put("ip", ip);  // 접속 아이피
		
		map.put("birthdate", birthdate);  // 생년월일
		map.put("gender", gender);  // 성별
		map.put("email", email);  // 개인 이메일 주소
		map.put("mobile", mobile);  // 개인 휴대폰 번호
		map.put("age", age);  // 나이
		map.put("memberType", memberType);  // 회원 종류 (종사자, 기관, 청소년, 학부모)
		map.put("agencyContacts", agencyContacts);  // 기관 연락처
		map.put("lastLoginTime", lastLoginTime);  // 최근 로그인 일시
		map.put("untTaskwkSeCd", untTaskwkSeCd);  // 단위 업무 코드
		map.put("topMenuNo", topMenuNo); // 최상위 메뉴 번호
		map.put("certificate", certificate);  // 인증서 로그인 여부
		
		map.put("untTaskwk", untTaskwk);  // 현재 선택된 단위 시스템 코드
		
		map.put("lgnScsYn", lgnScsYn);  // 로그인 성공 여부
		
		map.put("enfsnNo", enfsnNo);  // 종사자 번호
		map.put("enfsnRoleSeCd", enfsnRoleSeCd);  // 종사자의 역할구분코드
		
		map.put("userInstNo", userInstNo);  // 사용자 기관번호
		map.put("yngbgsPrtcrNo", yngbgsPrtcrNo);  // 청소년보호자번호
		map.put("indvIdntfcNo", indvIdntfcNo);  // 개인식별번호
		
		map.put("instTypeSeCd", instTypeSeCd);  // 기관유형구분코드
		
		map.put("instNo", instNo);  //  기관 번호
		map.put("instNm", instNm);  //  기관명
		
		map.put("wrdTelno", wrdTelno);  // 유선전화번호
		map.put("sidoNm", sidoNm);  // 시도명
		map.put("sigunguNm", sigunguNm);  // 시군구명
		
		map.put("deptCd", deptCd);  // 부서코드
		map.put("deptNm", deptNm);  // 부서명
		
		map.put("sessionId", sessionId);

		map.put("managerYn", managerYn);
		
		map.put("groupAuthrtSeCd", groupAuthrtSeCd);	// 그룹권한구분코드
		map.put("authrtSeCd", authrtSeCd);				// 권한구분코드
		
		map.put("instAuthList", instAuthList);			// 기관 권한 목록
		
		return map;
	}
	/**
	 * @return the managerYn
	 */
	public String getManagerYn() {
		return managerYn;
	}
	/**
	 * @param managerYn the managerYn to set
	 */
	public void setManagerYn(String managerYn) {
		this.managerYn = managerYn;
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
	 * 그룹권한구분코드를 설정한다.
	 * 
	 * @param groupAuthrtSeCd the groupAuthrtSeCd to set
	 */
	public void setGroupAuthrtSeCd(String groupAuthrtSeCd) {
		this.groupAuthrtSeCd = groupAuthrtSeCd;
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
	 * 권한구분코드를 설정한다.
	 * 
	 * @param authrtSeCd the authrtSeCd to set
	 */
	public void setAuthrtSeCd(String authrtSeCd) {
		this.authrtSeCd = authrtSeCd;
	}
	
	/**
	 * 기관 권한 목록을 조회한다.
	 * 
	 * @return the instAuthList
	 */
	public List<UserInstAuthVO> getInstAuthList() {
		return instAuthList;
	}
	/**
	 * 기관 권한 목록을 설정한다.
	 * 
	 * @param instAuthList the instAuthList to set
	 */
	public void setInstAuthList(List<UserInstAuthVO> instAuthList) {
		this.instAuthList = instAuthList;
	}
}
