package com.tomatosystem.exbuilder6.core.filter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.parser.RequestFilter;
import com.tomatosystem.exbuilder6.core.paging.CommonPagination;
import com.tomatosystem.exbuilder6.core.paging.PaginationInfo;
import com.tomatosystem.exbuilder6.core.util.StringUtil;

/**
 * 
 * XBDataRequestFilter.java
 * 
 * @Description  DataMode Parameter filter
 *                      DataRequest resover 이전 커스텀 필터 (주로 dataRequest에 공통 파라미터 정보를 추가함)
 * @author tomatosystem
 * @since 2022. 12. 14.
 * @version 1.0
 * @see
 *
 * << 개정이력(Modification Information) >>
 *   
 * 수정일                           수정자                         수정내용
 * -------       --------      ---------------------------
 * 2022. 12. 14.  You Minsang   최초 생성
 *
 * </pre>
 */
public class XBDataRequestFilter implements RequestFilter {
	
	private PaginationInfo paginationInfo = null;
	
	@Override
	public void filter(DataRequest dataRequest, HttpServletRequest request) {
						
		ParameterGroup pg = this.getParameter(dataRequest, "dmPageInfo");
		
		if(pg != null) {
			String strPaging = StringUtil.fixNull(pg.getValue("pagingYn"));
			
			if (!StringUtil.isEmpty(strPaging) &&  "Y".equals(strPaging)) {
				
				Map commonParam = new HashMap();
				commonParam.put("totalCount"          	, StringUtil.fixNull(pg.getValue("totalCount")));				
				commonParam.put("pageRowCount"   		, StringUtil.fixNull(pg.getValue("pageRowCount")));
				commonParam.put("pageNo"                , StringUtil.fixNull(pg.getValue("pageNo")));
				commonParam.put("pagingYn"				, "Y");								
				
				this.paginationInfo = CommonPagination.getInstance();
				
				Map mapParam = this.paginationInfo.init(commonParam);
				
				CommonPagination paging = (CommonPagination) this.paginationInfo; // 전체
				
				request.setAttribute("_PAGE_INFO_", paging);
			}
		}
		
	}
	
	public ParameterGroup getParameter(DataRequest dataRequest, String name) {
		
		List<String> groupName = dataRequest.getParameterGroupNames();

		for (String key : groupName) {
			if (key.equals(name)) {
				return dataRequest.getParameterGroup(name);
			}
		}
		
		return null;
	}
}
