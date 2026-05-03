/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.math.NumberUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import isry.itgcms.util.StringUtil;

/**
 * @파일명        : UnitSysAuthVO.java
 * @프로그램 설명 : 단위업무별 권한 VO
 * - 
 * - 
 * @작성자        : Myeong.Jae.Cheol
 * @작성일        : 2023. 1. 14. 
 * @수정자        : Myeong.Jae.Cheol
 * @수정일        : 2023. 1. 14.
 * @수정내용      : 
 * -                
 * -                
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UnitSysAuthVO implements AutoCloseable, Serializable {
	
	private static final long serialVersionUID = -3939149642400908007L;

	/** 사용자아이디 */
	private String userId;
	
	/** 단위업무별 권한항목 [단위업무구분코드 : (권한유형구분코드 : 권한유형 항목 목록)] */
	private Map<String, Map<String, List<AuthDetailItem>>> items;
	
	/**
	 * 사용자아이디 조회
	 * 
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 단위업무별 권한항목 조회
	 * 
	 * @return the items
	 */
	public Map<String, Map<String, List<AuthDetailItem>>> getItems() {
		return items;
	}
	
	/**
	 * 단위업무별 권한항목이 비워있는지 확인
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		if (this.items == null) return true;
		return this.items.isEmpty();
	}

	/**
	 * Key 목록 조회
	 * 
	 * @return the keyList
	 */
	public Set<String> keySet() {
		if (this.items != null) {
			return items.keySet();
		}
		return new HashSet<>();
	}
	
	/**
	 * Value 목록 조회
	 * 
	 * @return the valueList
	 */
	public Collection<Map<String, List<AuthDetailItem>>> values() {
		if (this.items != null) {
			return (Collection<Map<String, List<AuthDetailItem>>>) items.values();
		}
		return new ArrayList<>();
	}
	
	/**
	 * 단위업무별 권한 항목 선택
	 * 
	 * @param key	단위업무구분코드 (UNT_TASKWK_SE_CD)
	 * @return
	 */
	public Map<String, List<AuthDetailItem>> selectItems(String key) {
		Map<String, List<AuthDetailItem>> results = new LinkedHashMap<>();
		
		if (this.items != null) {
			return items.get(key);
		}
		
		return results;
	}
	
	/**
	 * 단위업무별 권한 항목 선택
	 * 
	 * @param key			단위업무구분코드 (UNT_TASKWK_SE_CD)
	 * @param authTypeKey	권한유형구분코드 (AUTHRT_TYPE_SE_CD)
	 * @return
	 */
	public List<AuthDetailItem> selectItems(String key, String authTypeKey) {
		List<AuthDetailItem> results = new ArrayList<>();
		
		if (this.items != null) {
			Map<String, List<AuthDetailItem>> authMap = items.get(key);
			if (authMap != null && authTypeKey != null) {
				if (authMap.containsKey(authTypeKey)) {
					return authMap.get(authTypeKey);
				}
			}
		}
		
		return results;
	}
	
	/**
	 * 단위업무별 권한 VO 기본 생성자
	 */
	public UnitSysAuthVO() {
		this.userId = new String();
		this.items = new HashMap<>();
	}
	
	/**
	 * 단위업무별 권한 VO 기본 생성자
	 * 
	 * @param userId	사용자아이디
	 */
	public UnitSysAuthVO(String userId) {
		this.userId = userId;
		this.items = new HashMap<>();
	}
	
	/**
	 * 단위업무별 권한 VO 생성자
	 * 
	 * @param mapList		SAB260 (단위업무별권한) 데이터 목록
	 */
	public UnitSysAuthVO(String userId, List<Map<String, Object>> mapList) {
		this(userId);
		
		if (mapList != null) {
			// 키 목록 설정	(단위업무구분코드)
			Set<String> keySet = mapList.stream()
					.distinct()
					.map(m -> StringUtil.nullConvert(m.get("UNT_TASKWK_SE_CD")))
					.collect(Collectors.toSet());
			
			// 단위업무별 권한항목 설정			
			if (keySet != null) {
				keySet.forEach(key -> {
					// 단위업무별 권한 필터링 및 맵핑 리스트 생성
					
					// 권한유형구분코드 키 목록 설정
					Set<String> authTypeKeySet = mapList.stream()
							.filter(map -> {
								String untTaskwkSeCd = StringUtil.nullConvert(map.get("UNT_TASKWK_SE_CD"));
								return key.equals(untTaskwkSeCd);
							})
							.distinct()
							.map(m -> StringUtil.nullConvert(m.get("AUTHRT_TYPE_SE_CD")))
							.collect(Collectors.toSet());
					
					// 권한유형구분코드 키 목록 설정
					Map<String, List<AuthDetailItem>> authTypeMap = new HashMap<>();
					for (String authTypeKey : authTypeKeySet) {
						List<AuthDetailItem> unitSysAuthList = mapList.stream()
								.filter(map -> {
									String untTaskwkSeCd = StringUtil.nullConvert(map.get("UNT_TASKWK_SE_CD"));
									String authrtTypeSeCd = StringUtil.nullConvert(map.get("AUTHRT_TYPE_SE_CD"));
									return key.equals(untTaskwkSeCd) && authTypeKey.equals(authrtTypeSeCd);
								})
								.map(m -> {
									AuthDetailItem item = new AuthDetailItem();
									
									// 시도코드 설정
									String ctpvCd = StringUtil.nullConvert(m.get("CTPV_CD"));
									item.setCtpvCd(ctpvCd);
									
									// 시군구코드 설정
									String sggCd = StringUtil.nullConvert(m.get("SGG_CD"));
									item.setSggCd(sggCd);
									
									// 기관번호 설정
									String instNoVal = StringUtil.nullConvert(m.get("INST_NO"));
									Integer instNo = NumberUtils.toInt(instNoVal);
									item.setInstNo(instNo);
									
									return item;
								})
								.collect(Collectors.toList());
						authTypeMap.put(authTypeKey, unitSysAuthList);
					}
					
					// 단위업무별 권한항목 설정
					items.put(key, authTypeMap);
				});
			}
		}
		
	}
	
	/**
	 * 단위업무별 권한 VO 에 대한 정보를 문자열로 출력합니다.
	 */
	@Override
	public String toString() {
		String className = this.getClass().getSimpleName();
		return className + " [" + "\n"
				+ "userId='" + this.userId + "'\n"
				+ ", items=" + this.toStringItems()
				+ "]";
	}
	
	private String toStringItems() {
		StringBuilder sb = new StringBuilder();
		if (this.keySet().isEmpty()) {
			sb.append("{}\n");
		} else {
			sb.append("{\n");
			this.keySet().forEach(key -> {
				sb.append("\t[\n");
				sb.append("\t\tUNT_TASKWK_SE_CD : '" + key + "',\n");
				Map<String, List<AuthDetailItem>> authItems = this.selectItems(key);
				authItems.entrySet().forEach(entry -> {
					sb.append("\t\t{\n");
					sb.append("\t\t\tAUTHRT_TYPE_SE_CD : '" + entry.getKey() + "',\n");
					List<AuthDetailItem> values = entry.getValue();
					if (values == null || values.size() == 0) {
						sb.append("\t\t\t[] // values is empty.\n");
					} else {
						sb.append("\t\t\t[\n");
						values.forEach(value -> {
							sb.append("\t\t\t\t" + value + ",\n");
						});
						sb.append("\t\t\t],\n");
					}
					sb.append("\t\t},\n");
				});
				sb.append("\t],\n");
			});
			sb.append("}\n");
		}
		
		return sb.toString();
	}
	
	/**
	 * 객체 리소스 반환
	 * 
	 * @throws Exception
	 */
	@Override
	public void close() throws Exception {
		this.userId = "";
		
		// 단위업무별 권한항목 삭제
		if (!this.isEmpty()) {
			this.keySet().forEach(key -> {
				Map<String, List<AuthDetailItem>> authItems = this.selectItems(key);
				if (!authItems.isEmpty()) {
					authItems.values().forEach(value -> {
						value.clear();
					});
					authItems.clear();
				}
			});
			this.items.clear();
		}
	}
	
	/**
	 * 권한상세 항목 객체
	 */
	public static class AuthDetailItem implements Serializable {
		
		private static final long serialVersionUID = 7656427095288013729L;

		private String ctpvCd;				// 시도코드
		
		private String sggCd;				// 시군구코드
		
		private Integer instNo;				// 기관번호
		
		public String getCtpvCd() {
			return ctpvCd;
		}
		
		public void setCtpvCd(String ctpvCd) {
			this.ctpvCd = ctpvCd;
		}
		
		public String getSggCd() {
			return sggCd;
		}
		
		public void setSggCd(String sggCd) {
			this.sggCd = sggCd;
		}
		
		public Integer getInstNo() {
			return instNo;
		}
		
		public void setInstNo(Integer instNo) {
			this.instNo = instNo;
		}

		@Override
		public String toString() {
			return "AuthDetailItem: { ctpvCd=" + ctpvCd + ", sggCd=" + sggCd
					+ ", instNo=" + instNo + " }";
		}
	}
}