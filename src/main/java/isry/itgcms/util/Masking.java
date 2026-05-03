package isry.itgcms.util;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Masking {
	
	// 이름 가운데 글자 마스킹 
	public static String nameMasking(String name) throws Exception {
		if (name == null || "".equals(name) || "null".equals(name)) {
			return "";
		}
//		// 한글만 (영어, 숫자 포함 이름은 제외) 
//		//String regex = "(^[가-힣]+)$";
//		String regex = "^(.+)$";
//		Matcher matcher = Pattern.compile(regex).matcher(name); 
//		if (matcher.find()) { 
//			int length = name.length(); 
//			String middleMask = ""; 
//			if(length > 2) { 
//				middleMask = name.substring(1, length - 1); 
//			} else { 
//				// 이름이 외자 
//				middleMask = name.substring(1, length); 
//			} 
//			String dot = ""; 
//			for(int i = 0; i<middleMask.length(); i++) { 
//				dot += "*"; 
//			} 
//			if(length > 2) { 
//				return name.substring(0, 1) + middleMask.replace(middleMask, dot) + name.substring(length-1, length); 
//			} else { 
//				// 이름이 외자 마스킹 리턴 
//				return name.substring(0, 1) + middleMask.replace(middleMask, dot); 
//			} 
//		} 
		return name; 
	}
	
	// 휴대폰번호 마스킹(가운데 숫자 4자리 마스킹) 
	public static String phoneMasking(String phoneNoVal) throws Exception {
		String phoneNo = phoneNoVal;
		if (phoneNo == null || "".equals(phoneNo) || "null".equals(phoneNo)) {
			return "";
		}
////		String regex = "(\\d{2,3})-?(\\d{3,4})-?(\\d{4})$"; 
////		Matcher matcher = Pattern.compile(regex).matcher(phoneNo); 
////		if (matcher.find()) { 
////			String target = matcher.group(2); 
////			int length = target.length(); 
////			char[] c = new char[length]; 
////			Arrays.fill(c, '*'); 
////			return phoneNo.replace(target, String.valueOf(c)); 
////		} 
////		return phoneNo; 
//		
//		/* target 번호로 지정된번호가 있는경우 해당번호 전부 마스킹처리됨 
//		   예) 010-1515-1515 경우 010-****-**** 처리되어 가운데자리만 * 되도록 수정 */
//		phoneNo = phoneNo.replaceAll("[^0-9]", "");
//		if ( ! (phoneNo.length() == 10 || phoneNo.length() == 11) ) {
//			return phoneNo;
//		}
//		if (phoneNo.length() == 10) {
//			return phoneNo.substring(0, 3) + "***" + phoneNo.substring(6,10);
//		}
//		if (phoneNo.length() == 11) {
//			return phoneNo.substring(0, 3) + "****" + phoneNo.substring(7, 11);
//		}
		return phoneNo;
	}

	// 이메일 마스킹(앞3자리 이후 '@'전까지 마스킹) 
	public static String emailMasking(String email) throws Exception {
		
		if (email == null || "".equals(email) || "null".equals(email)) {
			return "";
		}
		
//		String str1 = "";
//		String str2 = "";
//
//		if (email.indexOf("@") > -1) {
//			str1 = email.substring(0, email.indexOf("@"));
//			str2 = email.substring(email.indexOf("@") + 1);
//			
//			int length = str1.length();
//			
//			if (length > 1) {
//				char[] c = new char[length - 1];
//				Arrays.fill(c, '*');
//				return str1.substring(0, 1) + "" + String.valueOf(c) + "@" + str2;
//			}
//		}
//		
//		//String regex = "(\\S+)+@(\\S+.\\S+)";
//		//Matcher matcher = Pattern.compile(regex).matcher(email);
//		
//		//if (matcher.find()) { 
//			//String target = matcher.group(1);
//			//String target2 = matcher.group(2);
//			//int length = target.length();
//			//if (length > 1) {
//				//char[] c = new char[length - 1];
//				//Arrays.fill(c, '*');
//				//return target.substring(0, 1) + "" + String.valueOf(c) + "@" + target2;
//			//}
//		//}
		
		return email; 
	}

	// 계좌번호 마스킹(뒤 5자리) 
	public static String accountNoMasking(String accountNo) throws Exception {
		if (accountNo == null || "".equals(accountNo) || "null".equals(accountNo)) {
			return "";
		}
//		int len = accountNo.length();
//		String result = "";
//		for (int i=0; i < accountNo.length(); i++) {
//			if (i > len / 2) {
//				if (isNumeric(accountNo.charAt(i))) {
//					result += "*";
//				} else {
//					result += accountNo.charAt(i);
//				}
//			} else {
//				result += accountNo.charAt(i);
//			}
//		}
//		return result;
		return accountNo;
	}
	
	public static boolean isNumeric(char chrNum) {
		if (chrNum == '0' || chrNum == '1' || chrNum == '2' || chrNum == '3' || chrNum == '4' 
				|| chrNum == '5' || chrNum == '6' || chrNum == '7' || chrNum == '8' || chrNum == '9') {
			return true;
		} else {
			return false;
		}
	}

	// 생년월일 마스킹(8자리) 
	public static String birthMasking(String birthday) throws Exception {
		if (birthday == null || "".equals(birthday) || "null".equals(birthday)) {
			return "";
		}
//		String regex = "^((19|20)\\d\\d)?([-/.])?(0[1-9]|1[012])([-/.])?(0[1-9]|[12][0-9]|3[01])$"; 
//		Matcher matcher = Pattern.compile(regex).matcher(birthday); 
//		if (matcher.find()) { 
//			return birthday.replaceAll("[0-9]", "*"); 
//		}
		return birthday; 
	}

	// 생년월일 마스킹(8자리) 
	public static String birthMaskingDay(String birthday) throws Exception {
		if (birthday == null || "".equals(birthday) || "null".equals(birthday)) {
			return "";
		}
//		String regex = "^((19|20)\\d\\d)?([-/.])?(0[1-9]|1[012])([-/.])?(0[1-9]|[12][0-9]|3[01])$"; 
//		Matcher matcher = Pattern.compile(regex).matcher(birthday); 
//		if (matcher.find()) {
//			String target0 = matcher.group(0);
//			String target1 = matcher.group(1);
//			String target2 = matcher.group(2);
//			String target3 = matcher.group(3);
//			String target4 = matcher.group(4);
//			String target5 = matcher.group(5);
//			String target6 = matcher.group(6);
//			//System.out.println("------------------------------");
//			//System.out.println("target0 : " + target0);
//			//System.out.println("target1 : " + target1);
//			//System.out.println("target2 : " + target2);
//			//System.out.println("target3 : " + target3);
//			//System.out.println("target4 : " + target4);
//			//System.out.println("target5 : " + target5);
//			//System.out.println("target6 : " + target6);
//			//return birthday.replaceAll("[0-9]", "*");
//			return target1 + (target3 == null ? "-" : target3) + target4 + 
//					(target5 == null ? "-" : target5) + target6.replaceAll("[0-9]", "*");
//		}
		return birthday; 
	}

	// 카드번호 가운데 8자리 마스킹 
	public static String cardMasking(String cardNo) throws Exception { 
		if (cardNo == null || "".equals(cardNo) || "null".equals(cardNo)) {
			return "";
		}
//		// 카드번호 16자리 또는 15자리 '-'포함/미포함 상관없음 
//		String regex = "(\\d{4})-?(\\d{4})(-?)(\\d{4})-?(\\d{3,4})$"; 
//		Matcher matcher = Pattern.compile(regex).matcher(cardNo); 
//		if (matcher.find()) {
//			String target = matcher.group(2) + matcher.group(3) + matcher.group(4);
//			String target1 = matcher.group(2).replaceAll("[0-9]", "*");
//			String target2 = matcher.group(3);
//			String target3 = matcher.group(4).replaceAll("[0-9]", "*");
//			return cardNo.replace(target, String.valueOf(target1 + target2 + target3)); 
//		} 
		return cardNo; 
	}
	

	// 주소 마스킹 
	public static String addressMasking(String address) throws Exception {
		if (address == null || "".equals(address) || "null".equals(address)) {
			return "";
		}
//		int len = address.length();
//		String result = "";
//		for (int i=0; i < address.length(); i++) {
//			if (i > len / 2) {
//				if (address.charAt(i) == ' ') {
//					result += " ";
//				} else {
//					result += "*";
//				}
//			} else {
//				result += address.charAt(i);
//			}
//		}
//		return result;
		
		return address;
		
		// 신(구)주소, 도로명 주소
		/*
		String regex = "(([가-힣]+(\\d{1,5}|\\d{1,5}(,|.)\\d{1,5}|)+(읍|면|동|가|리))(^구|)((\\d{1,5}(~|-)\\d{1,5}|\\d{1,5})(가|리|)|))([ ](산(\\d{1,5}(~|-)\\d{1,5}|\\d{1,5}))|)|"; 
		String newRegx = "(([가-힣]|(\\d{1,5}(~|-)\\d{1,5})|\\d{1,5})+(로|길))"; 
		Matcher matcher = Pattern.compile(regex).matcher(address); 
		Matcher newMatcher = Pattern.compile(newRegx).matcher(address); 
		if (matcher.find()) { 
			return address.replaceAll("[0-9]", "*"); 
		} else if(newMatcher.find()) { 
			return address.replaceAll("[0-9]", "*"); 
		} 
		return address;
		*/
	}

	// 주민등록번호 마스킹
	public static String rrnoMasking(String ssn) {
		
		String last_6_char_pattern = "(.{6}$)";
		
		if (ssn == null || "".equals(ssn) || ssn.length() < 6) {
	       return ssn;
		}
		
//	    return ssn.replaceAll(last_6_char_pattern, "******");
		return ssn;
	}

	// 메신저ID 절반 마스킹 
	public static String msngrIdMasking(String msngrId) throws Exception {
		if (msngrId == null || "".equals(msngrId) || "null".equals(msngrId)) {
			return "";
		}
//		String result = "";
//		int length = msngrId.length(); 
//		if(length == 1) {
//			return "*";
//		} else {
//			for (int i=0; i < length; i++) {
//				if (i > length / 2) {
//					result += "*";
//				} else {
//					result += msngrId.charAt(i);
//				}
//			}
//			return result;
//		}
		return msngrId;
	}

	// 출처: https://develop-sense.tistory.com/62 [특별한 일상]
	
	/*
	public static void main(String[] args) {
		try {
			System.out.println("성명 : " + nameMasking("홍길동"));
			System.out.println("성명 : " + nameMasking("장일"));
			System.out.println("성명 : " + nameMasking("선우영재"));
			System.out.println("전화번호 : " + phoneMasking("010-1234-1234"));
			System.out.println("이메일 : " + emailMasking("123@test456.com"));
			System.out.println("계좌번호 : " + accountNoMasking("1234-12341-2341234"));
			System.out.println("생일 : " + birthMasking("1919-09-19"));
			System.out.println("카드번호 : " + cardMasking("1234-5678-0912-3412"));
			System.out.println("주소 : " + addressMasking("세종특별자치시 한누리대로 411(어진동)"));
			System.out.println("주민번호 : " + rrnoMasking("123456-1234567"));
			System.out.println("메신저ID : " + msngrIdMasking("abcde"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
}
