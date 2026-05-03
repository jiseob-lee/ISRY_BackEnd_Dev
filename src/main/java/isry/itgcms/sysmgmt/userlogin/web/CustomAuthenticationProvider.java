package isry.itgcms.sysmgmt.userlogin.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import isry.itgcms.sysmgmt.userlogin.vo.CustomUserDetails;

import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;

public class CustomAuthenticationProvider implements AuthenticationProvider { 

	private final Logger logger = LogManager.getLogger(CustomAuthenticationProvider.class);
	
	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;
	
	@Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    	String userId = (String)authentication.getPrincipal();    
        String userPw = (String)authentication.getCredentials();

        Map<String, Object> result1 = null;
		try {
			result1 = new HashMap<>(); //userLoginService.userLogin3(userId, userPw);
		} catch (Exception e) {
			e.printStackTrace();
		} 

        //logger.info("사용자가 입력한 로그인정보입니다. {}", userId + "/" + userPw);

        //if (userId.equals("test") && userPw.equals("test")) {
        if (result1 != null && result1.get("id") != null && !"".equals(result1.get("id"))) {

        	logger.info("정상 로그인입니다.");
        	
            List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
            
            roles.add(new SimpleGrantedAuthority("ROLE_USER"));
            

            UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(userId, userPw, roles);

            result.setDetails(new CustomUserDetails(userId, ""));

            return result;

        } else if (result1 != null && result1.get("msg") != null && !"".equals(result1.get("msg"))) {

        	logger.info("로그인 오류가 발생했습니다.");

        	throw new BadCredentialsException((String)result1.get("msg"));
        	
        } else {

        	logger.info("사용자 크리덴셜 정보가 틀립니다. 에러가 발생합니다.");

        	throw new BadCredentialsException("로그인 정보가 올바르지 않습니다.");
        }
    }
}
