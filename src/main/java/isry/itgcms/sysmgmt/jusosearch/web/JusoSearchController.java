package isry.itgcms.sysmgmt.jusosearch.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;
import com.google.gson.Gson;

import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseController;
import isry.itgcms.sysmgmt.jusosearch.vo.JusoResults;
import isry.itgcms.sysmgmt.jusosearch.vo.JusoResults.Common;

@Controller
@RequestMapping(value = "/isry/itgcm/sysmgmt/jusosearch")
public class JusoSearchController extends IsryBaseController {

	@RequestMapping(value = "/getAddrApi.do")
	public View getAddrApi(HttpServletRequest req, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		String keyword = "";
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmKeyword");
		if (parameterGroup != null) {
			keyword = parameterGroup.getValue("keyword");
		}

		Map<String, String> dmPageMap = dataRequest.getParameterGroup("dmPage").getSingleValueMap();
		
		//log.debug("#### pageNo : " + dmPageMap.get("pageNo"));
		//log.debug("#### pageRowCount : " + dmPageMap.get("pageRowCount"));
		
		// 요청변수 설정
		String currentPage = dmPageMap != null ? dmPageMap.get("pageNo") : "1"; // 요청 변수 설정 (현재 페이지. currentPage : n > 0)
		String countPerPage = dmPageMap != null ? dmPageMap.get("pageRowCount") : "100"; // 요청 변수 설정 (페이지당 출력 개수. countPerPage 범위 : 0 < n <= 100)
		String resultType = "json"; // 요청 변수 설정 (검색결과형식 설정, json)
		String confmKey = EgovProperties.getProperty("globals", "juso.search.appKey"); // 요청 변수 설정 (승인키)
		//String keyword = req.getParameter("keyword"); // 요청 변수 설정 (키워드)
		
		
		
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
			public void checkClientTrusted(X509Certificate[] certs, String authType) {
				// test
			}
			public void checkServerTrusted(X509Certificate[] certs, String authType) {
				// test
			}
		} };

		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		

		// OPEN API 호출 URL 정보 설정
		String apiUrl = "https://www.juso.go.kr/addrlink/addrLinkApi.do?currentPage=" + currentPage + "&countPerPage="
				+ countPerPage + "&keyword=" + URLEncoder.encode(keyword, "UTF-8") + "&confmKey=" + confmKey
				+ "&resultType=" + resultType;

		HttpsURLConnection conn = null;
		InputStream is = null;
		BufferedReader br = null;
		StringBuffer sb = null;
		
		try {
			conn = (HttpsURLConnection) new URL(apiUrl).openConnection();
	
			is = conn.getInputStream();
	
			//URL url = new URL(apiUrl);
			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			sb = new StringBuffer();
			String tempStr = null;
	
			while (true) {
				tempStr = br.readLine();
				if (tempStr == null)
					break;
				sb.append(tempStr); // 응답결과 JSON 저장
			}
			
		} catch (Exception e) {
			log.debug(e.getMessage());
			
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					log.debug(e.getMessage());
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					log.debug(e.getMessage());
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		//response.setCharacterEncoding("UTF-8");
		//response.setContentType("text/xml");
		//response.getWriter().write(sb.toString()); // 응답결과 반환
		
		JusoResults s = new Gson().fromJson(sb.toString(), JusoResults.class);
		

		/*
		String examplejson = // example json
				"{ " + " \"results\": " + "  {\"common\": " + " { " + " \"totalCount\":\"1\" "
						+ " ,\"currentPage\":\"1\" " + " ,\"countPerPage\":\"10\" " + " ,\"errorCode\":\"0\" "
						+ " ,\"errorMessage\":\"정상\" " + " } " + "  , \"juso\": " + "  [{ "
						+ "  \"roadAddr\":\"서울특별시 마포구 성암로 301 (상암동)\" " + "  ,\"roadAddrPart1\":\"서울특별시 마포구 성암로 301\" "
						+ "  ,\"roadAddrPart2\":\"(상암동)\" "
						+ "  ,\"jibunAddr\":\"서울특별시 마포구 상암동 1595 한국지역정보개발원(KLID Tower)\" "
						+ "  ,\"engAddr\":\"301, Seongam-ro, Mapo-gu, Seoul\" " + "  ,\"zipNo\":\"03923\" "
						+ "  ,\"admCd\":\"1144012700\" " + "  ,\"rnMgtSn\":\"114403113012\" "
						+ "  ,\"bdMgtSn\":\"1144012700115950000000001\" " + "  ,\"detBdNmList\":\"\" "
						+ "  ,\"bdNm\":\"한국지역정보개발원(KLID Tower)\" " + "  ,\"bdKdcd\":\"0\" " + "  ,\"siNm\":\"서울특별시\" "
						+ "  ,\"sggNm\":\"마포구\" " + "  ,\"emdNm\":\"상암동\" " + "  ,\"liNm\":\"\" " + "  ,\"rn\":\"성암로\" "
						+ "  ,\"udrtYn\":\"0\" " + "  ,\"buldMnnm\":\"301\" " + "  ,\"buldSlno\":\"0\" "
						+ "  ,\"mtYn\":\"0\" " + "  ,\"lnbrMnnm\":\"1595\" " + "  ,\"lnbrSlno\":\"0\" "
						+ "  ,\"emdNo\":\"03\" " + "  }] " + "  }  " + " }";
		
		JusoResults s = new Gson().fromJson(examplejson, JusoResults.class);
		*/
		
		//s.Print();

		Map<String, String> dmPage = new HashMap<>();
		dmPage.put("totalCount", s.getResults().getCommon().getTotalCount());
		dmPage.put("pageRowCount", s.getResults().getCommon().getCountPerPage());
		dmPage.put("pageNo", s.getResults().getCommon().getCurrentPage());
		
		dataRequest.setResponse("jusoDataset", s.getResults().getJuso());
		Map<String, String> commonMap = new HashMap<>();
		Common common = s.getResults().getCommon();
		commonMap.put("countPerPage", common.getCountPerPage());
		commonMap.put("currentPage", common.getCurrentPage());
		commonMap.put("errorCode", common.getErrorCode());
		commonMap.put("errorMessage", common.getErrorMessage());
		commonMap.put("totalCount", common.getTotalCount());
		
		//dataRequest.setResponse("dmJusoCommon", s.getResults().getCommon());
		dataRequest.setResponse("dmJusoCommon", commonMap);
		dataRequest.setResponse("dmPage", dmPage);
		
		return new JSONDataView();
	}
}
