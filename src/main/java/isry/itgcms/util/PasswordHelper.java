package isry.itgcms.util;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;

@Component
public class PasswordHelper {
	
	private UserLoginService userLoginService;
	
	@Autowired
	private ApplicationContext context;
	
	//public PasswordHelper() {
		//userLoginService = (UserLoginService) context.getBean("userLoginService");
	//}
	
    public String generatePassword (int lengthVal) {
    	
    	userLoginService = (UserLoginService) context.getBean("userLoginService");
    	
    	int length = lengthVal;
	    //minimum length of 6
	    if (length < 4) {
	        length = 6;
	    }
	
	    final char[] lowercase = "abcdefghjkmnpqrstuvwxyz".toCharArray();
	    final char[] uppercase = "ABCDEFGJKLMNPRSTUVWXYZ".toCharArray();
	    final char[] numbers = "23456789".toCharArray();
	    final char[] symbols = "^$?!@#%&".toCharArray();
	    final char[] allAllowed = "abcdefghjkmnpqrstuvwxyzABCDEFGJKLMNPRSTUVWXYZ23456789^$?!@#%&".toCharArray();
	
	    //Use cryptographically secure random number generator
	    Random random = new SecureRandom();
	
	    StringBuilder password = new StringBuilder(); 
	
	    for (int i = 0; i < length-4; i++) {
	        password.append(allAllowed[random.nextInt(allAllowed.length)]);
	    }
	
	    //Ensure password policy is met by inserting required random chars in random positions
	    password.insert(random.nextInt(password.length()), lowercase[random.nextInt(lowercase.length)]);
	    password.insert(random.nextInt(password.length()), uppercase[random.nextInt(uppercase.length)]);
	    password.insert(random.nextInt(password.length()), numbers[random.nextInt(numbers.length)]);
	    password.insert(random.nextInt(password.length()), symbols[random.nextInt(symbols.length)]);
	
	    return password.toString();

    }
    
    /**
     * 
     * @Method명   : passwordCheck
     * @param currentPassword 입력한 현재 패스워드
     * @param password 변경하고자 하는 신규 패스워드
     * @param userId 사용자 아이디
     * @param phoneNum 사용자 휴대폰 번호
     * @param currPassword 현재 비밀번호의 암호화되어 있는 값
     * @param prevPassword 이전 비밀번호의 암호화되어 있는 값
     * @param firstCheckedYN 앞서 비밀번호 검사를 수행했는지 여부
     * @return 이상이 없으면 공백 문자열을 리턴하고, 이상이 있으면 그 사유를 리턴한다.
     * @작성자     : Lee.Ji.Seob
     * @작성일     : 2022. 10. 19. 
     * @Method설명 : 비밀번호의 유효성을 체크한다.
     */
    public String passwordCheck(String currentPassword, String password, 
    		String userId, String phoneNumVal, String currPassword, String prevPassword, 
    		boolean firstCheckedYN) throws Exception {
    	
    	userLoginService = (UserLoginService) context.getBean("userLoginService");
    	
    	String phoneNum = phoneNumVal;
    	if (password == null || "".equals(password.trim())) {
    		return "패스워드를 입력해주시기 바랍니다.";
    	}

    	//ScpDb scpDb = new ScpDb();
    	
    	if (!firstCheckedYN) {  // 1차 검사를 안했을 때

        	//String currentPasswordEnc = scpDb.scpHashB64(currentPassword);
        	String currentPasswordEnc = currentPassword;
        	
        	if (currPassword != null && !"".equals(currPassword)
        			&& currentPassword != null && !"".equals(currentPassword)
        			//&& !currPassword.equals(currentPasswordEnc)) {
        			&& !userLoginService.selectPasswordEquals(currPassword, currentPasswordEnc)) {
        		return "현재 비밀번호가 일치하지 않습니다.";
        	}
        	
	    	if (password.length() < 9 || password.length() > 30) {
	    		return "패스워드는 9자 이상, 30자 이하의 길이를 사용합니다.";
	    	}
	    	
	    	int cnt = 0;
	
	    	if (StringUtil.containsLowerChar(password)) {
	    		cnt++;
	    	}
	    	if (StringUtil.containsUpperChar(password)) {
	    		cnt++;
	    	}
	    	if (StringUtil.containsSpecialChar(password)) {
	    		cnt++;
	    	}
	    	if (StringUtil.containsNumber(password)) {
	    		cnt++;
	    	}
	    	
	    	if (cnt < 3) {
	    		return "비밀번호는 영대문자, 영소문자, 숫자, 특수문자 중 3종류 이상으로 구성됩니다.";
	    	}
	    	
	    	String lowercase = "abcdefghijklmnopqrstuvwxyz";
	    	String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    	String number = "01234567890";
	
	    	for (int i=0; i <= lowercase.length() - 4; i++) {
				if (password.contains(lowercase.substring(i, i + 4))) {
					return "비밀번호는 연속된 알파벳으로 구성될 수 없습니다.";
				}
			}
	    	for (int i=0; i <= uppercase.length() - 4; i++) {
				if (password.contains(uppercase.substring(i, i + 4))) {
					return "비밀번호는 연속된 알파벳으로 구성될 수 없습니다.";
				}
			}
	    	for (int i=0; i <= number.length() - 4; i++) {
				if (password.contains(number.substring(i, i + 4))) {
					return "비밀번호는 연속된 숫자로 구성될 수 없습니다.";
				}
			}
	    }
    	
    	//String passwordEnc = scpDb.scpHashB64(password);
    	String passwordEnc = password;
    	
    	if (currPassword != null && !"".equals(currPassword)
    			&& password != null && !"".equals(password)
    			//&& currPassword.equals(passwordEnc)) {
    			&& userLoginService.selectPasswordEquals(currPassword, passwordEnc)) {
    		return "현재 비밀번호는 사용할 수 없습니다.";
    	}
    	
    	if (prevPassword != null && !"".equals(prevPassword) 
    			&& password != null && !"".equals(password)
	    		//&& prevPassword.equals(passwordEnc)) {
    			&& userLoginService.selectPasswordEquals(prevPassword, passwordEnc)) {
	    	return "바로 이전 비밀번호는 사용할 수 없습니다.";
    	}
    	
    	
    	if (userId != null) {
    		for (int i=0; i <= userId.length() - 4; i++) {
    			if (password.contains(userId.substring(i, i + 4))) {
    				return "비밀번호는 아이디의 일부를 포함할 수 없습니다.";
    			}
    		}
    	}
    	
    	if (phoneNum != null) {
    		phoneNum = phoneNum.replaceAll("[^\\d]", "");
    		for (int i=0; i <= phoneNum.length() - 4; i++) {
    			if (password.contains(phoneNum.substring(i, i + 4))) {
    				return "비밀번호는 전화번호의 일부를 포함할 수 없습니다.";
    			}
    		}
    	}
    	
    	return "";
    }
    
    /*
    public static void main(String[] args) {
    	String password = PasswordHelper.generatePassword(8);
    	System.out.println(password);
    	
    	String phoneNum = "123456";
		for (int i=0; i < phoneNum.length() - 4; i++) {
			System.out.println(phoneNum.substring(i, i + 4));
		}
    }
    */
}
