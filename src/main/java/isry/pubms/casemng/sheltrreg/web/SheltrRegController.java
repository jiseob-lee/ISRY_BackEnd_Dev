/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.pubms.casemng.sheltrreg.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.cysns.casemng.casereg.service.CysnsRegService;
import isry.itgcms.sysmgmt.userauth.service.InqOrgListService;
import isry.itgcms.sysmgmt.userjoin.service.ReqUserJoinService;
import isry.pubms.casemng.sheltrreg.service.SheltrRegService;
import isry.pubmt.casemng.slfrlreg.service.SlfrlRegService;
import isry.uneartmng.policelinkaply.service.PicMngService;

/**
 * @파일명        : SheltrRegController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Myeong.Sang
 * @작성일        : 2022. 6. 3. 
 * @수정자        : Lee.Myeong.Sang
 * @수정일        : 2022. 6. 3.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping("/isry/pubms/casemng/sheltrreg")
public class SheltrRegController {
	
	//private Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "sheltrRegService")
	private SheltrRegService sheltrRegService;

	@Resource(name = "slfrlRegService")
	private SlfrlRegService slfrlRegService;

	@Resource(name = "cysnsRegService")
	private CysnsRegService cysnsRegService;
		
	@Resource(name = "picMngService")
	private PicMngService picMngService;
	
	@Resource(name = "reqUserJoinService")
	private ReqUserJoinService reqUserJoinService;

	@Resource(name = "inqOrgListService")
	private InqOrgListService inqOrgListService;

	@RequestMapping(value = "/selectMainList.do")
	public View selectMainList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		//사례 목록 조회
//		List<Map<String, String>> list = sheltrRegService.selectMainList(request, dataRequest);
//		dataRequest.setResponse("dsCaseInqList", list);
		
		Map<String, Object> result = sheltrRegService.selectMainPagingList(request, dataRequest);

		dataRequest.setResponse("dsCaseInqList", result.get("list"));
		dataRequest.setResponse("dmPageInfo", result.get("dmPageInfo"));

		return new JSONDataView();
	}

	@RequestMapping("/selectReqById.do")
	public View selectReqById(DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsList", sheltrRegService.selectReqById(dataRequest));
		dataRequest.setResponse("dsAsessRcordList", sheltrRegService.selectAsessRcordById(dataRequest));
		dataRequest.setResponse("dsSprtPensnList", sheltrRegService.selectSprtPensnById(dataRequest));
		dataRequest.setResponse("dsRthousSprtList", sheltrRegService.selectRthousSprtById(dataRequest));

		dataRequest.setResponse("dsTrlEmtList", slfrlRegService.selectTrlEmtById(dataRequest));

		dataRequest.setResponse("dsCrisisScoreList", cysnsRegService.selectReqById(dataRequest));
		dataRequest.setResponse("dsCrisisResultList", cysnsRegService.selectReqById2(dataRequest));
		
		dataRequest.setResponse("dsOrgRegionSido", picMngService.selectRegion());
		dataRequest.setResponse("dsOrgRegionSgg", picMngService.selectRegion2());
		dataRequest.setResponse("dsOrganization", inqOrgListService.selectOrg(dataRequest));
		dataRequest.setResponse("dsOrgRegion", reqUserJoinService.selectOrgRegion(dataRequest));
		
		
		return new JSONDataView();
	}

	@RequestMapping("/saveData.do")
	public View saveData(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		Map<String, String> dmParam = sheltrRegService.saveData(request, dataRequest);

		dataRequest.setResponse("dmParam", dmParam);

		return new JSONDataView();
	}

	@RequestMapping("/deleteData.do")
	public View deleteData(DataRequest dataRequest) throws Exception {
		
		sheltrRegService.deleteData(dataRequest);
		
		return new JSONDataView();
	}
}
