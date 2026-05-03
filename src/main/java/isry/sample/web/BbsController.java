package isry.sample.web;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
//import isry.sample.service.BbsService;
//import isry.sample.service.BbsVO;
//import isry.sample.service.DefaultVO;
import lombok.extern.log4j.Log4j2;

/**  
 * @Class Name : BbsController.java
 * @Description : 게시판 테스트 컨트롤러 Class
 * @Modification Information  
 * @
 * @  수정일      	    수정자          		수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2022.04.28    HAN      	최초생성
 * 
 * @author 공통팀
 * @since 2022.04.28
 * @version 1.0
 */

@Controller
@RequestMapping(value = "/bbs/*")
public class BbsController 
{
	/** Log Check */
//	protected Log log = LogFactory.getLog(this.getClass());
	
	/** BbsService */
	//@Resource(name="bbsService")
	//private BbsService bbsService;

	/** EgovPropertyService */
//	@Resource(name = "sysPropService")
//	protected EgovPropertyService sysPropService;
	
	/**
	 * 게시물 등록화면으로 이동한다.
	 * @param BbsVO 	BbsVO
	 * @param cmmnSearchVO 	DefaultVO
	 * @param model 		ModelMap
	 * @return 게시물 등록화면
	 * @throws Exception
	 */
	//@RequestMapping(value="bbsInsert.do")
	//public String fileBbsRegist(@ModelAttribute("BbsVO") BbsVO BbsVO,
								//@ModelAttribute("cmmnSearchVO") DefaultVO cmmnSearchVO,
								//ModelMap model ) throws Exception 
	//{ 
		//model.addAttribute("pageIndex", 		cmmnSearchVO.getPageIndex());
		//model.addAttribute("searchCondition", 	cmmnSearchVO.getSearchCondition());
		//model.addAttribute("searchKeyword", 	cmmnSearchVO.getSearchKeyword());

		//return "/bbs/FileBbsRegist";
	//}

	/**
	 * 게시물 수정화면으로 이동한다.
	 * @param BbsVO BbsVO
	 * @param cmmnSearchVO DefaultVO
	 * @param model ModelMap
	 * @return 게시물 수정화면
	 * @throws Exception
	 */
//	@RequestMapping(value="bbsUpdate.do")
//	public String fileBbsUpdt(@ModelAttribute("BbsVO") BbsVO BbsVO, 
//							  @ModelAttribute("cmmnSearchVO") DefaultVO cmmnSearchVO,
//							  ModelMap model ) throws Exception 
//	{
//		BbsVO bbsVO = bbsService.selectDetailBbs(BbsVO, "modify");
//		
//		model.addAttribute("bbsVO", 			bbsVO);
//		model.addAttribute("pageIndex", 		cmmnSearchVO.getPageIndex());
//		model.addAttribute("searchCondition", 	cmmnSearchVO.getSearchCondition());
//		model.addAttribute("searchKeyword", 	cmmnSearchVO.getSearchKeyword());
//
//		return "/bbs/FileBbsUpdt";
//	}


	/**
	 * 게시물 목록
	 * @param cmmnSearchVO DefaultVO
	 * @param model ModelMap
	 * @return 게시물 목록화면
	 * @throws Exception
	 */
//	@RequestMapping(value="selectBbsList.do")
//	public String selectFileBbsList(@ModelAttribute("cmmnSearchVO") DefaultVO cmmnSearchVO, 
//									ModelMap model ) throws Exception 
//	{
//		cmmnSearchVO.setSearchKeyword(cmmnSearchVO.getSearchKeyword());
//		cmmnSearchVO.setSearchCondition(cmmnSearchVO.getSearchCondition());
//		cmmnSearchVO.setPageUnit(sysPropService.getInt("system.paging.default.pageunit"));
//		cmmnSearchVO.setPageSize(sysPropService.getInt("system.paging.default.pagesize"));
//		PaginationInfo paginationInfo = new PaginationInfo();
//		paginationInfo.setCurrentPageNo(cmmnSearchVO.getPageIndex());
//		paginationInfo.setRecordCountPerPage(cmmnSearchVO.getPageUnit());
//		paginationInfo.setPageSize(cmmnSearchVO.getPageSize());
//		cmmnSearchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
//		cmmnSearchVO.setLastIndex(paginationInfo.getLastRecordIndex());
//		cmmnSearchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
//		
//		List bbsList = bbsService.selectListBbs(cmmnSearchVO);
//		model.addAttribute("bbsList", bbsList);
//
//		int totCnt = bbsService.selectTotBbs(cmmnSearchVO);
//		paginationInfo.setTotalRecordCount(totCnt);
//
//		model.addAttribute("paginationInfo", 	paginationInfo);
//		model.addAttribute("pageIndex", 		cmmnSearchVO.getPageIndex());
//		model.addAttribute("searchCondition",	cmmnSearchVO.getSearchCondition());
//		model.addAttribute("searchKeyword", 	cmmnSearchVO.getSearchKeyword());
//
//		return "/bbs/FileBbsList";
//	}

	/**
	 * 게시물 등록한다.
	 * @param multiRequest MultipartHttpServletRequest
	 * @param BbsVO BbsVO
	 * @param cmmnSearchVO DefaultVO
	 * @param model ModelMap
	 * @return 게시물 등록
	 * @throws Exception
	 */
//	@RequestMapping(value="insertBbs.do")
//	public String registerFileBbs(final MultipartHttpServletRequest multiRequest,
//								  @ModelAttribute("BbsVO") BbsVO BbsVO,
//								  @ModelAttribute("cmmnSearchVO") DefaultVO cmmnSearchVO,
//								  ModelMap model) throws Exception
//	{
//		bbsService.insertBbs(multiRequest,BbsVO);	
//
//		model.addAttribute("pageIndex", 		cmmnSearchVO.getPageIndex());
//		model.addAttribute("searchCondition", 	cmmnSearchVO.getSearchCondition());
//		model.addAttribute("resultType", 		"biz.info.com.save");
//
//		return "forward:/bbs/selectBbsList.do";
//	}


	/**
	 * 게시물 상세항목 조회한다.
	 * @param BbsVO BbsVO
	 * @param cmmnSearchVO DefaultVO
	 * @param model ModelMap
	 * @return 게시물 상세항목 조회
	 * @throws Exception
	 */
//	@RequestMapping(value="selectBbsDetail.do")
//	public String selectFileBbsDetail(@ModelAttribute("BbsVO") BbsVO BbsVO,
//									  @ModelAttribute("cmmnSearchVO") DefaultVO cmmnSearchVO,
//									  ModelMap model ) throws Exception 
//	{
//		BbsVO bbsVO = bbsService.selectDetailBbs(BbsVO, "detail");
//		model.addAttribute("bbsVO", 			bbsVO);
//		model.addAttribute("pageIndex", 		cmmnSearchVO.getPageIndex());
//		model.addAttribute("searchCondition",	cmmnSearchVO.getSearchCondition());
//		model.addAttribute("searchKeyword", 	cmmnSearchVO.getSearchKeyword());
//
//		return "/bbs/FileBbsDetail";
//	}

	/**
	 * 게시물 수정한다.
	 * @param multiRequest MultipartHttpServletRequest
	 * @param BbsVO BbsVO
	 * @param cmmnSearchVO DefaultVO
	 * @param model ModelMap
	 * @return 게시물 수정
	 * @throws Exception
	 */
//	@RequestMapping(value="updateBbs.do")  
//	public String modifyFileBbs(final MultipartHttpServletRequest multiRequest,
//								@ModelAttribute("BbsVO") BbsVO BbsVO,
//								@ModelAttribute("cmmnSearchVO")  DefaultVO cmmnSearchVO,
//								ModelMap model) throws Exception
//	{
//		bbsService.updateBbs(multiRequest,BbsVO);
//		model.addAttribute("pageIndex", cmmnSearchVO.getPageIndex());
//		
//		return "forward:/bbs/selectBbsList.do";
//	}

	/**
	 * 게시물 삭제한다.
//	 * @param BbsVO BbsVO
	 * @param model ModelMap
	 * @return 게시물 삭제
	 * @throws Exception
	 */
//	@RequestMapping(value="deleteBbs.do")
//	public String deleteBbs(@ModelAttribute("BbsVO") BbsVO BbsVO,
//								ModelMap model) throws Exception
//	{
//		bbsService.deleteBbs(BbsVO);
//		
//		return "forward:/bbs/selectBbsList.do";
//	}
	
	/**
	 * 파일 다운로드
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws IOException
	 */
//	@RequestMapping(value = "FileDownLoad.do")
//	public void download(
//			HttpServletRequest request,
//			HttpServletResponse response) throws Exception{
//			String streFileNm 	= request.getParameter("strFileNm");
//			String orginlFileNm = request.getParameter("orgFileNm");
//
//		    // 첨부파일 다운 공통서비스 호출
//			bbsService.FileDownLoad(response, streFileNm, orginlFileNm);
//	}
}
