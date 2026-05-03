package isry.sample.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import com.tomatosystem.exbuilder6.core.util.ExcelImporter;
import com.tomatosystem.exbuilder6.core.util.StringUtil;
import com.tomatosystem.exbuilder6.core.vo.ExcelVO;

@Controller
@RequestMapping("/CmnExcelImport")
public class CmnExcelImportContoller {
	/**
	 * 엑셀을 읽어들여 그리드에 세팅할 값으로 변경하여 json 데이타를 보내준다.
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("import.do")
	public View excelReadJsonSet(HttpServletRequest request, HttpServletResponse response,DataRequest requestData) throws Exception {
		ParameterGroup dmParam = requestData.getParameterGroup("dmParam");
		String strStartRowIndex = StringUtil.fixNull(dmParam.getValue("startRowIndex"));
		String strStartCellIndex = StringUtil.fixNull(dmParam.getValue("startCellIndex"));
		if("".equals(strStartRowIndex)){
			strStartRowIndex = "1";
		}
		if("".equals(strStartCellIndex)){
			strStartCellIndex = "0";
		}
		
		ExcelImporter excelImporter = new ExcelImporter();
		
		List<ExcelVO> dataList = excelImporter.getCellDataList(request, response, requestData, Integer.parseInt(strStartRowIndex), Integer.parseInt(strStartCellIndex));
		
		requestData.setResponse("dsExcel", dataList);
		
		return new JSONDataView();
	}
}
