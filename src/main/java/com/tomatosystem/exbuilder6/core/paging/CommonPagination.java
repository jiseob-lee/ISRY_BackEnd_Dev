package com.tomatosystem.exbuilder6.core.paging;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.tomatosystem.exbuilder6.core.constants.PageConst;
import com.tomatosystem.exbuilder6.core.util.StringUtil;


public class CommonPagination extends PaginationInfo {

	private static CommonPagination pagination = new CommonPagination();

	public static CommonPagination getInstance() {
		return CommonPagination.pagination;
	}

	// sql에 파라미터로 넘길 페이지 정보
	private Map<String, Object> mapInitPage;

	public Map<String, Object> getMapInitPage() {
		return mapInitPage;
	}

	private String pageResultKey;

	public String getPageResultKey() {
		return this.pageResultKey;
	}

	public void setPageResultKey(String psPgeResultKey) {
		this.pageResultKey = psPgeResultKey;
	}

	private int pageIndexerCount; // 보여지는 페이지 수

	public int getPageIndexerCount() {
		return this.pageIndexerCount;
	}

	public void setPageIndexerCount(int psPageIndexerCount) {
		this.pageIndexerCount = psPageIndexerCount;
	}

	// 페이지 정보 초기화
	@SuppressWarnings("rawtypes")
	public Map init(Map<String, Object> param) {
				
		if (StringUtil.isNullEmpty(param.get(PageConst.KEY_RECORD_CNT_PER_PAGE))) {
			super.setRecordCountPerPage(PageConst.RECORD_CNT_PER_PAGE);
		} else {
			super.setRecordCountPerPage(
					Integer.parseInt(StringUtil.getString(param.get(PageConst.KEY_RECORD_CNT_PER_PAGE))));
		}

		// 현재 페이지
		String currentPageNo = StringUtil.getString(param.get(PageConst.KEY_PAGE_NO));
		super.setCurrentPageNo(StringUtils.isBlank(currentPageNo) ? 1 : Integer.parseInt(currentPageNo));

		String totalRecords = StringUtil.getString(param.get(PageConst.KEY_RECORD_TOTAL_CNT));
		super.setTotalRecordCount(StringUtils.isBlank(totalRecords) ? 0 : Integer.parseInt(totalRecords));

		// 페이징 result key
		this.setPageResultKey(StringUtil.getString(param.get(PageConst.KEY_PAGE_DATA_MAP_KEY)));

		param.put(PageConst.KEY_FIRST_RECODE_IDX, super.getFirstRecordIndex());
		param.put(PageConst.KEY_RECORD_CNT_PER_PAGE, super.getRecordCountPerPage());
		param.put(PageConst.KEY_LAST_RECODE_IDX, super.getLastRecordIndex());
		param.put(PageConst.KEY_PAGE_ROW_COUNT, super.getRecordCountPerPage());
		mapInitPage = param;
		return param;
	}

	@SuppressWarnings("rawtypes")
	public void setTotalRecordCount(List<Map> resultList) {
		int totalRecordCount = 0;
		int realTotalRecordCount = 0;
		if (resultList != null && resultList.size() > 0) {

			Map dataMap = resultList.get(0);
			totalRecordCount = Integer.parseInt(StringUtil.getString(dataMap.get(PageConst.TOTAL_COUNT), "0"));
			
			if(resultList.get(0).get(PageConst.REAL_TOTAL_COUNT) != null){
				realTotalRecordCount = Integer.parseInt(StringUtil.getString(dataMap.get(PageConst.REAL_TOTAL_COUNT), "0"));
				
				super.setRealTotalRecordCount(realTotalRecordCount);
			}
		}

		super.setTotalRecordCount(totalRecordCount);
	}	
}