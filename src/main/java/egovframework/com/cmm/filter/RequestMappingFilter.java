/*
 * Copyright 2008-2009 MOPAS(MINISTRY OF SECURITY AND PUBLIC ADMINISTRATION).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package egovframework.com.cmm.filter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tomatosystem.exbuilder6.core.util.FileUtil;

import egovframework.com.cmm.ModifiableHttpServletRequest;

/**
 * 
 * @파일명 : RequestMappingFilter.java
 * @프로그램 설명 : - 아래 로직은 로그인 창이 완료 되기 전까지 필터를 통하여 값을 설정 한다. - 로그인창이 완료 되면 강제 정보 할당
 *       로직을 삭제 처리한다
 * @작성자 : Song.Young.Il
 * @작성일 : 2021. 11. 18.
 * @수정자 : Song.Young.Il
 * @수정일 : 2021. 11. 18.
 * @수정내용 : - -
 */
public class RequestMappingFilter implements Filter {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@SuppressWarnings("unused")
	private FilterConfig config;

	/**
	 * 
	 * @Method명 : doFilter
	 * @param servletRequest
	 * @param servletResponse
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 * @작성자 : Song.Young.Il
	 * @작성일 : 2021. 11. 25.
	 * @Method설명 : 로그인창이 완료 되기전까지 필터에 정보를 강제 할당 처리하며 로그인창이 완료 되면 로직만 삭제 처리한다.
	 */
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request;

		HttpServletResponse response;

		request  = (HttpServletRequest)  servletRequest;
		response = (HttpServletResponse) servletResponse;

		// ####################################################################
		// 아래 로직은 로그인인증이 완료 되기 전까지
		// 화면의 로그인 정보를 강제 할당 처리 한다.
		// 송영일 2021.11.18
		// 아래 로직은 추후 전체 삭제 하기로 한다. !!!!!!!!!!!!!
		// ####################################################################

		log.debug(".RequestMappingFilter.preHandle() START!");

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession();
		Enumeration<String> attributes = request.getSession().getAttributeNames();
		Enumeration params = request.getParameterNames();
		ModifiableHttpServletRequest updateRequest = new ModifiableHttpServletRequest(request);

		String strPgmId = FileUtil.getEncryptFileNm().substring(0, 8);
		String strUserId = "isryTester";

		updateRequest.setAttribute("@d1#InterCeptor Control", "isry Interceptor control");

		// ##################################################################
		// request setAttribute 정보를 변경 처리 한다.
		// ##################################################################
		updateRequest.setAttribute("@d1#USER_ID", strUserId);
		updateRequest.setAttribute("@d1#CRT_USER_ID", strUserId);
		updateRequest.setAttribute("@d1#UPD_USER_ID", strUserId);
		updateRequest.setAttribute("@d1#PGM_ID", strPgmId);
		updateRequest.setAttribute("@d1#CRT_PGM_ID", strPgmId);
		updateRequest.setAttribute("@d1#UPD_PGM_ID", strPgmId);
		updateRequest.setAttribute("@d1#CRT_IP_MAC", "NULL");
		updateRequest.setAttribute("@d1#UPD_IP_MAC", "NULL");

		// ##################################################################
		// request setParameter 정보를 변경 처리 한다.
		// ##################################################################
		updateRequest.setParameter("@d1#USER_ID", strUserId);
		updateRequest.setParameter("@d1#CRT_USER_ID", strUserId);
		updateRequest.setParameter("@d1#UPD_USER_ID", strUserId);
		updateRequest.setParameter("@d1#PGM_ID", strPgmId);
		updateRequest.setParameter("@d1#CRT_PGM_ID", strPgmId);
		updateRequest.setParameter("@d1#UPD_PGM_ID", strPgmId);
		updateRequest.setParameter("@d1#CRT_IP_MAC", "NULL");
		updateRequest.setParameter("@d1#UPD_IP_MAC", "NULL");

		log.debug("------------- getAttributeNames --------------------");

		// 속성구성내역
		Enumeration<String> attrNames = updateRequest.getAttributeNames();
		while (attrNames.hasMoreElements()) {
			String attrName = attrNames.nextElement();
			Object attrValue = updateRequest.getAttribute(attrName);
			log.debug("updateRequest:AttributeNames" + attrName + " : " + attrValue);
		}

		log.debug("------------- getAttributeNames --------------------");
		log.debug("");
		log.debug("");
		log.debug("------------- getParameterNames --------------------");

		String parameter = null;

		// 파라미터 구성 내역
		for (Object name : Collections.<String>list(updateRequest.getParameterNames())) {
			String value = updateRequest.getParameter(name.toString());
			// parameter += name + "=" + value + "&";
			log.debug("updateRequest:parameter->" + name + ":" + value);
		}

		// log.debug(parameter);

		log.debug("------------- getParameterNames --------------------");

		log.debug("");
		log.debug("");

		log.debug("------------- getParameterMap --------------------");

		parameter = "";

		Map<String, String[]> map = updateRequest.getParameterMap();

		for (Entry<String, String[]> entry : map.entrySet()) {

			String name = entry.getKey();

			String[] values = entry.getValue();

			parameter += name + "=" + Arrays.toString(values) + "&";

			log.debug("updateRequest:getParameterMap->" + name + ":" + values);

		}

		log.debug(parameter);
		log.debug("------------- getParameterMap --------------------");
		log.debug("");
		log.debug("");
		
		request = (HttpServletRequest) updateRequest;

		log.debug(".RequestMappingFilter.preHandle() END! ");
		chain.doFilter(request, response);

	}

	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}

	/**
	 * 
	 * @Method명 : destroy
	 * @작성자 : Song.Young.Il
	 * @작성일 : 2021. 11. 18.
	 * @Method설명 : destory
	 */
	public void destroy() {

		// destory
	}
}
