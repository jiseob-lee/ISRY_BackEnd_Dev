/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @파일명        : Test.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Ji.Seob.Lee
 * @작성일        : 2022. 4. 12. 
 * @수정자        : Ji.Seob.Lee
 * @수정일        : 2022. 4. 12.
 * @수정내용      : 
 * -                
 * -                
 */
public class Test {

	/**
	 * @Method명   : main
	 * @param args
	 * @작성자     : Ji.Seob.Lee
	 * @작성일     : 2022. 4. 12. 
	 * @Method설명 :
	 */
	
	public static void main(String[] args) {
		/*
		String str = "1|2";
		String[] arr = str.split("\\|");
		for (int i=0; i < arr.length; i++) {
			System.out.println(arr[i]);
		}
		LocalDate now = LocalDate.now();
		System.out.println(now);
		
		String c = "\"김명희\"<mhkim@wisewires.com>";
		String mailAddr = "";
		String mailTo = "";
		if (c.indexOf("<") > -1) {
			mailAddr = c.substring(c.indexOf("<") + 1, c.indexOf(">"));
			mailTo = c.substring(1, c.indexOf("<") - 1);
		} else {
			mailAddr = c;
		}
		
		//String str = "1.2.*.*";
		//System.out.println(str.replace("*", "0/255"));
		
		String flnm = "123-123a";
		flnm = flnm.replace("-", "");
        flnm = flnm.replaceAll("[\\d]", "");
        System.out.println("flnm : " + flnm);
        
        List<Map<String, String>> list = new ArrayList<>();
        
        for (int i=0; i < 3; i++) {
        	Map<String, String> map = new HashMap<>();
        	map.put("k", String.valueOf(i));
        	list.add(map);
        }
        
        System.out.println(list.toString());
        

        for (int i=0; i < 3; i++) {
        	Map<String, String> map = list.get(i);
        	map.put("k1", String.valueOf(i));
        	
        }
        
        System.out.println(list.toString());
        */
        List<String> list = new ArrayList<>();
        list.add("123");
        //list = null;
        for (String str : list) {
        	System.out.println(str);
        }
        
        SimpleDateFormat sDate2 = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS");
        System.out.println(sDate2.format(new Date()));
        
        Calendar calendar = Calendar.getInstance(); // gets a calendar using the default time zone and locale.
        calendar.add(Calendar.SECOND, 5);
        System.out.println(calendar.getTime());
        
        SimpleDateFormat sDate3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sDate3.format(calendar.getTime()));
	}
	

}
