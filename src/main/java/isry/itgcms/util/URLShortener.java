/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;

/**
 * @파일명        : URLShortener.java
 * @프로그램 설명 : 단축 URL 생성
 * - 
 * - 
 * @작성자        : Lee.In.Sung
 * @작성일        : 2022. 11. 18. 
 * @수정자        : Lee.In.Sung
 * @수정일        : 2022. 11. 18.
 * @수정내용      : 
 * -                
 * -                
 */
public class URLShortener {

	/**
	 * 생성된 키 저장소
	 */
	private HashMap<String, String> keyMap;
	/**
	 * 이미 생성된 url 인지 빠르게 확인하는 용도의 해쉬맵
	 */
	private HashMap<String, String> valueMap;

	private String domain;
	/**
	 * 캐릭터를 숫자로 맵핑하는 용도의 배열
	 */
	private char myChars[];
	/**
	 * 무작위 정수 생성용 난수 객체
	 */
	private Random myRand;
	/**
	 * 축약 URL의 키 길이. 기본값은 8
	 */
	private int keyLength;

	/**
	 * 디폴트 생성자
	 */
	public URLShortener() {
		keyMap = new HashMap<String, String>();
		valueMap = new HashMap<String, String>();
		myRand = new Random();
		keyLength = 8;
		myChars = new char[62];
		for (int i = 0; i < 62; i++) {
			int j = 0;
			if (i < 10) {
				j = i + 48;
			} else if (i > 9 && i <= 35) {
				j = i + 55;
			} else {
				j = i + 61;
			}
			myChars[i] = (char) j;
		}
		domain = "";
		//domain = "http://localhost:8080/in";
	}

	/**
	 * 베이스 URL과 길이 지정을 통해 줄임URL 생성자
	 * @param length
	 * @param newDomain
	 */
	public URLShortener(int length, String newDomain) {
		this();
		this.keyLength = length;
		if (!newDomain.isEmpty()) {
			newDomain = sanitizeURL(newDomain);
			domain = newDomain;
		}
	}

	/**
	 * 축약 URL 생성을 위해 호출하는 공개 메쏘드
	 * @param longURL
	 * @return
	 */
	public String shortenURL(String longURL) {
		String shortURL = "";
		if (validateURL(longURL)) {
			longURL = sanitizeURL(longURL);
			shortURL = getKey(longURL);
		}
		// 프로토콜 부분 추가
		return shortURL;
	}

	/**
	 * 축약URL로부터 원래 URL 가져오는 공개 메쏘드
	 * @param shortURL
	 * @return
	 */
	public String expandURL(String shortURL) {
		String longURL = "";
		String key = shortURL;
		longURL = keyMap.get(key);
		return longURL;
	}


	/**
	 * 해당 URL이 올바른지 확인하는 메쏘드
	 * @param url
	 * @return
	 */
	boolean validateURL(String url) {
		return true;
	}

	/**
	 * 올바른 url 이라도 발생될 수 있는 문제를 제거하는 절차 수행
	 * 즉, www.naver.com/, https://www.naver.com,
	 * https://www.naver.com/
	 * 위에서 나열된 URL들 모두 동일한 단축 URL을 가르키므로
	 * 이러한 경우에 대한 처리가 필요함.
	 * @param url
	 * @return
	 */
	String sanitizeURL(String url) {
		if (url.substring(0, 7).equals("http://"))
			url = url.substring(7);

		if (url.substring(0, 8).equals("https://"))
			url = url.substring(8);

		if (url.charAt(url.length() - 1) == '/')
			url = url.substring(0, url.length() - 1);
		return url;
	}


	/**
	 * 원래 URL에서 키를 찾는다.
	 * @param longURL
	 * @return
	 */
	private String getKey(String longURL) {
		String key;
		key = generateKey();
		keyMap.put(key, longURL);
		valueMap.put(longURL, key);
		return key;
	}

	/**
	 * 키를 생성한다.
	 * @return
	 */
	private String generateKey() {
		String key = "";
		boolean flag = true;
		while (flag) {
			key = "";
			for (int i = 0; i <= keyLength; i++) {
				key += myChars[myRand.nextInt(62)];
			}

			Date date = new Date();
			int year = date.getYear();
			key = year + key;
			
			if (!keyMap.containsKey(key)) {
				flag = false;
			}
		}
		return key;
	}
}
