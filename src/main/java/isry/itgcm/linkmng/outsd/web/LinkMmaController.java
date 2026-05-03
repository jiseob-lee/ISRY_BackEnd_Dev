/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.linkmng.outsd.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.base.IsryBaseController;
import isry.itgcm.linkmng.outsd.service.LinkMmaService;

/**
 * @파일명 : LinkMmaController.java
 * @프로그램 설명 : - -
 * @작성자 : Choi.Doo.Il
 * @작성일 : 2022. 10. 4.
 * @수정자 : Choi.Doo.Il
 * @수정일 : 2022. 10. 4.
 * @수정내용 : - -
 */
@Controller
@RequestMapping("/isry/itgcm/linkmng/outsd")
public class LinkMmaController extends IsryBaseController {

	@Resource(name = "linkMmaService")
	private LinkMmaService linkMmaService;

	// 병무청 연계접수 목록
	@RequestMapping(value = "/selectLinkMmaList.do")
	public View selectLinkMmaList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		List<Map<String, Object>> result = linkMmaService.selectLinkMmaList(request, dataRequest);
		dataRequest.setResponse("dsList", result);
		return new JSONDataView();
	}

	// 병무청 의뢰접수처리
	@RequestMapping(value = "/saveLinkMma.do")
	@ResponseBody
	public View saveLinkMma(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, String> retMap = linkMmaService.saveLinkMma(request, dataRequest);
		log.debug("saveLinkMma retMap ==>> " + retMap);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectMmaMltsRspnbeDscsnSprtRqstList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Yoo.Chi.Hoon
	 * @작성일 : 2023. 3. 27.
	 * @Method설명 : 병무청 병역의무자 상담의뢰지원 목록
	 */
	@RequestMapping(value = "/selectMmaRqstList.do")
	public View selectMmaMltsRspnbeDscsnSprtRqstList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		List<Map<String, Object>> result = linkMmaService.selectMmaRqstList(request, dataRequest);
		dataRequest.setResponse("dsCAB130", result);
		return new JSONDataView();
	}

	/**
	 * @Method명 : selectMmaRqstInfo
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Yoo.Chi.Hoon
	 * @작성일 : 2023. 4. 19.
	 * @Method설명 : 병무청 병역의무자 상담의뢰지원 정보 조회
	 */
	@RequestMapping(value = "/selectMmaRqstInfo.do")
	public View selectMmaRqstInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> result = linkMmaService.selectMmaRqstInfo(request, dataRequest);

		dataRequest.setResponse("dsCAA130", result);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectMmaRqstInfoResult
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Yoo.Chi.Hoon
	 * @작성일 : 2023. 4. 21.
	 * @Method설명 : 병역의무자 상담지원의뢰 접수결과정보 조회
	 */
	@RequestMapping(value = "/selectMmaRqstInfoResult.do")
	public View selectMmaRqstInfoResult(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> result = linkMmaService.selectMmaRqstInfoResult(request, dataRequest);

		dataRequest.setResponse("dsCAA100", result);

		return new JSONDataView();
	}

	/**
	 * @Method명 : processMmaMltsRspnbeDscsnSprtRqstResult
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Yoo.Chi.Hoon
	 * @작성일 : 2023. 3. 28.
	 * @Method설명 : 병역의무자 상담지원의뢰 접수 결과 수정, 저장 삭제
	 */
	@RequestMapping(value = "/processMmaRqstResult.do")
	public View processMmaMltsRspnbeDscsnSprtRqstResult(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		Map<String, Object> result = linkMmaService.processMmaRqstResult(request, dataRequest);

		dataRequest.setMetadata(true, result);

		return new JSONDataView();
	}

	@RequestMapping(value = "/linkMmaFilesDown.do")
	public void linkMmaFilesDown(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		log.debug("=====[" + "병무청파일다운로드"+ "]");
		
		linkMmaService.linkMmaFilesDown(request, response, dataRequest);

//		ParameterGroup paramGroup =  dataRequest.getParameterGroup("dmFile");
//		
//		Map<String, Object> map = new HashMap<>();
//		if(paramGroup != null) {
//			String strSrvcRqstAplyNo =  paramGroup.getValue("SRVC_RQST_APLY_NO");
//			String strEsbSeq         =  paramGroup.getValue("ESB_SEQ");
//			String strRqstAtcflNm    =  paramGroup.getValue("RQST_ATCFL_NM");
//			String strRqstAtcflPath  =  paramGroup.getValue("RQST_ATCFL_PATH");
//			String strRqstAtcflSynyN =  paramGroup.getValue("RQST_ATCFL_SYN_YN");
//			String strRqstAtcflYmd   =  paramGroup.getValue("RQST_ATCFL_YMD");
//		}
//		
//		String filePath      = "C:\\filedownload\\";
//		String fileName      = "abc.txt";
//		String fileFullPath  = filePath + fileName;
//		final String resCharset = "UTF-8";
//		
//		log.info("path");
//		log.info(fileFullPath);
//		
////		strWasFileBasePath
//		
//		File downloadFile = null;
//		
//		if(! filePath.isEmpty() && ! fileName.isEmpty()) {
//			
//			downloadFile = new File(fileFullPath);
//			
//			if(! downloadFile.exists()) {
//				throw new FileNotFoundException();
//			}
//		}
//		
//		
//		try {
//			FileInputStream in = new FileInputStream(downloadFile);
//			
//			fileName = HttpWebUtil.getUrlEncodedFileName(request, fileName);
//			
//			// res
//			response.setContentType("application/x-msdownload" + ";charset=" + resCharset);
//			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");		
//			
//			OutputStream out = response.getOutputStream();
//			// res
//			int data;
//			
//			while ((data = in.read()) != -1) {   
//				out.write(data);
//			}		
//			
//			out.flush();
//			out.close();
//			in.close();
//			
//		} catch(IOException ex) {
//			ex.printStackTrace();
//			
//		}
		
	}

}