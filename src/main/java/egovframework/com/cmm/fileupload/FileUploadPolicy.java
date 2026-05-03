/**-----------------------------------------------------------------------
 * @Class Name : FileUploadPolicy.java
 * @Description : 파일 업로드에 대한 Policy 처리를 위한 클래스
 * @author HAN
 * @since 2022. 12. 25.
 * @version 
 * 
 *------------------------------------------------------------------------
 * Modification Information
 *------------------------------------------------------------------------   
 * 수정일           		수정자          		수정내용
 * ----------      ---------      ----------------------------------------
 * @ 2022. 12. 25.  Administrator  최초생성
 */
package egovframework.com.cmm.fileupload;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import egovframework.com.cmm.service.EgovProperties;

/**
 * <pre>
 * 기능 : 파일 업로드에 대한 Policy 처리를 위한 클래스
 * </pre>
 */
@Component("fileUploadPolicy")
public class FileUploadPolicy {
	
    /** log */
    Logger log = Logger.getLogger(this.getClass());

	/**
	 * Java Style의 정규식 패턴의 특수문자들을 escape 시키기 위하셔 정의한 정규식 패턴
	 */
	private static final Pattern escapePattern =
		Pattern.compile("(\\.|\\\\|\\[|\\]|\\^|\\$|\\+|\\{|\\}|\\(|\\)|\\|)");

	/**
	 * 도스 파일시스템의 wildcard중 *를 Java Style RegExp로 변경하기 위해 사용되는 정규식 패턴
	 */
	private static final Pattern asteriskPattern = Pattern.compile("\\*");

	/**
	 * 도스 파일시스템의 wildcard중 ?를 Java Style RegExp로 변경하기 위해 사용되는 정규식 패턴
	 */
	private static final Pattern questionmarkPattern = Pattern.compile("\\?");

	/**
	 * 여러개의 wildcard 적용시 seperator로 사용될 ;를 정의하기 위해  사용되는 정규식 패턴
	 */
	private static final Pattern multiplePattern = Pattern.compile("\\;");
	
	/**
	 * Policy 내부적으로 보관할 allow wildcard 변수
	 */
	private String deny = "*";

	/**
	 * Policy 내부적으로 보관할 deny wildcard 변수
	 */
	private String allow = ""; 

	/**
	 * Policy 확인을 위해 Spec 에 맞는 설정값을 초기화를 한다.
	 * 
	 * @param spec
	 */
    public void init()
    {
        this.allow = EgovProperties.getProperty("globals", "isry.uploadpolicy.default.allow");
        this.deny = EgovProperties.getProperty("globals", "isry.uploadpolicy.default.deny");
    }
    
    
    
    /**
     * fileName 을 확인하여, 허용가능한 확장자 파일인지 체크한다.
     * 
     * @param fileName
     * @return
     * @throws Exception
     */
    public boolean accept(String fileName) throws Exception
    {
    	
		if (("*".equals(allow) && "*".equals(deny))) {
			throw new Exception("allow 와 deny 패턴 설정 오류입니다.");
		}
	
		if ("*".equals(deny)) {
			Pattern p = compileWildcardPattern(allow, false);	//대소문자를 무시
			Matcher m = p.matcher(fileName);
			//log.info("m.matches()=="+m.matches());
			if (m.matches()) {
				//log.info("파일 등록 가능.("+ fileName+ ") ");
				return true;
			}
			
			//log.info("파일 등록 불가능.("+ fileName+ ") ");
			return false;
			

		} else if ("*".equals(allow)) {
			Pattern p = compileWildcardPattern(deny, false);	//대소문자를 무시
			Matcher m = p.matcher(fileName);
			if (!m.matches())
				//log.info("파일 허용정책에 일치 않음.("+ fileName+ ") ");
			return false;
		}
		
    	return true;
    }
        
	/**
	* 주어진 wildcard를 분석하여 Java 정규식 패턴으로 리턴한다.<BR>
	* 
	* @param wildcard 분석 대상이될 wildcard 문자열
	* @param ignoreCase wildcard 분석시 대소문자 구분할지를 결정 (true:대소문자 구분)
	* 
	* @return 분석된 자바 정규식 패턴 
	*/
	public Pattern compileWildcardPattern(String wildcard, boolean ignoreCase) {

		Matcher escapeMatcher = escapePattern.matcher(wildcard);
		wildcard = escapeMatcher.replaceAll("\\\\$1");

		Matcher asteriskMatcher = asteriskPattern.matcher(wildcard);
		wildcard = asteriskMatcher.replaceAll("(.*)");

		Matcher questionmarkMatcher = questionmarkPattern.matcher(wildcard);
		wildcard = questionmarkMatcher.replaceAll("(.)");

		Matcher multipleMacher = multiplePattern.matcher(wildcard);
		wildcard = multipleMacher.replaceAll(")|(");

		wildcard = "(" + wildcard + ")";

		if( ignoreCase ) 
			return Pattern.compile(wildcard);
		else
			return Pattern.compile(wildcard, Pattern.CASE_INSENSITIVE );
	}    
	
}
