package isry.itgcms.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IP {

	private static Logger log = LoggerFactory.getLogger(IP.class);

	public static String getClientIP(HttpServletRequest request) {
		
		String ip = request.getHeader("X-Forwarded-For");
		//log.info("> X-FORWARDED-FOR : " + ip);

		if (ip == null) {
			ip = request.getHeader("Proxy-Client-IP");
			//log.info("> Proxy-Client-IP : " + ip);
		}
		if (ip == null) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			//log.info(">  WL-Proxy-Client-IP : " + ip);
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_CLIENT_IP");
			//log.info("> HTTP_CLIENT_IP : " + ip);
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			//log.info("> HTTP_X_FORWARDED_FOR : " + ip);
		}
		if (ip == null) {
			ip = request.getRemoteAddr();
			//log.info("> getRemoteAddr : " + ip);
		}
		log.info("> Result : IP Address : " + ip);

		return ip;
	}
	
	public static String getServerIP() {
		
		InetAddress local = null;
		String ip = null;
		
		try {
			local = InetAddress.getLocalHost();
			ip = local.getHostAddress();
			log.info("local ip : " + ip);
		} catch (UnknownHostException e) {
			//System.out.println(e.getMessage());
		}

		return ip;
		//출처: https://javacpro.tistory.com/47 [버물리의 IT공부]
	}

	public static String getHostName() {
		
		String hostName = "";
		
		try {
			hostName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			//System.out.println(e.getMessage());
		}

		return hostName;
	}
}
