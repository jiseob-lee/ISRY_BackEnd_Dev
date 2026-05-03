package isry.sample.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;
import com.cleopatra.spring.TSVDataView;
import isry.base.IsryBaseController;
import isry.sample.service.CmnCodeService;
import isry.sample.service.TstGridDevService;


@Controller
@RequestMapping("/TstGrid")
public class OnlyUISample extends IsryBaseController {

	@Resource
	private TstGridDevService tstGridDevService;

	@Resource
	private CmnCodeService cmnCodeService;

	@RequestMapping("/list.do")
	public View list(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		ParameterGroup param = dataRequest.getParameterGroup("dmParam");
		Map<String, String> mapParam = new HashMap<>();

		mapParam.put("STUD_NO", param.getValue("strStudNo"));
		List<Map<String, Object>> listCmnTmpReg = tstGridDevService.selectCmnTmpRegList(mapParam);
		dataRequest.setResponse("dsCmnTmpReg", listCmnTmpReg);

		return new JSONDataView();

	}

	@RequestMapping("/onLoad.do")
	public View onLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		// 성별코드([TMP001])
		dataRequest.setResponse("dsGenderRcd", cmnCodeService.selectCmnCodeList("TMP001"));

		// 학생구분코드([TMP002])
		dataRequest.setResponse("dsStudDivRcd", cmnCodeService.selectCmnCodeList("TMP002"));

		// 주야간코드([TMP003])
		dataRequest.setResponse("dsDayNightDivRcd", cmnCodeService.selectCmnCodeList("TMP003"));

		// 국가코드([TMP004])
		dataRequest.setResponse("dsNatRcd", cmnCodeService.selectCmnCodeList("TMP004"));

		// 은행코드([TMP005])
		dataRequest.setResponse("dsBankRcd", cmnCodeService.selectCmnCodeList("TMP005"));

		return new JSONDataView();

	}

	/**
	 * 
	 * <pre>
	 * 메소드명	: listTsvRh
	 * 설	 명	: TSV + mybatis  ResultHandler(row)
	 * </pre>
	 *
	 * 이력사항 2021. 8. 12. Park. ju wan 최초작성
	 *
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @throws Exception
	 */
	@RequestMapping("/listTsvRh.do")
	public void listTsvRh(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmParam");

		Map<String, String> mapParam = new HashMap<>();

		mapParam.put("STUD_NO", param.getValue("strStudNo"));

		tstGridDevService.selectCmnTmpRegRowHandler(mapParam, response);

	}

	@RequestMapping("/listTsv.do")
	public View listTsv(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmParam");
		Map<String, String> mapParam = new HashMap<>();
		mapParam.put("STUD_NO", param.getValue("strStudNo"));
		List<Map<String, Object>> listCmnTmpReg = tstGridDevService.selectCmnTmpRegList(mapParam);
		dataRequest.setResponse("dsCmnTmpReg", listCmnTmpReg);
		return new TSVDataView();
	}

	@RequestMapping("/listTab.do")
	public View listTab(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmParam");

		Map<String, String> mapParam = new HashMap<>();
		mapParam.put("STUD_NO", param.getValue("strStudNo"));
		List<Map<String, Object>> listCmnTmpReg = tstGridDevService.selectCmnTmpRegList(mapParam);
		dataRequest.setResponse("dsCmnTmpReg2", listCmnTmpReg);
		return new JSONDataView();

	}

	@RequestMapping("/save.do")
	public View save(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		tstGridDevService.saveCmnTmpReg(dataRequest);
		return new JSONDataView();

	}

	@RequestMapping("/saveTab.do")
	public View saveTab(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		tstGridDevService.saveCmnTmpRegTab(dataRequest);

		return new JSONDataView();

	}

	@RequestMapping("/saveFile.do")
	public View saveFile(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		tstGridDevService.saveCmnTmpRegWithFile(dataRequest);
		return new JSONDataView();

	}
}
