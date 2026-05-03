package isry.itgcms.sysmgmt.userlogin.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
 
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
 
public class CustomUserDetails implements UserDetails {
 
    private static final long serialVersionUID = -4450269958885980297L;
    
    private String username;
    private String password;
     
    public CustomUserDetails(String userName, String password)
    {
        this.username = userName;
        this.password = password;
    }
     
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();   
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
         
        return authorities;
    }
  
    @Override
    public String getPassword() {
        return password;
    }
  
    @Override
    public String getUsername() {
        return username;
    }
  
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
  
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
  
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
  
    @Override
    public boolean isEnabled() {
        return true;
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (obj instanceof CustomUserDetails) {
    		return this.username.equals(((CustomUserDetails)obj).username);
    	}
    	return false;
    }
    
    @Override
    public int hashCode() {
    	return this.username.hashCode();
    }
    
 }


// 출처: http://goodcodes.tistory.com/entry/Spring-01-스프링-시큐리티로그인-사용자-인증 [Good Codes Make a Good Program]