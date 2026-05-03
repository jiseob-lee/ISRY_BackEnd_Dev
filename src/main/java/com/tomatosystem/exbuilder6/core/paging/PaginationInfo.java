package com.tomatosystem.exbuilder6.core.paging;

import java.util.List;
import java.util.Map;

public abstract class PaginationInfo {
	private int pageNo; //현재 페이지
	private int pageRowCount; //페이지 당 데이터 수
	private int totalCnt; //총 데이터 수
	private int realTotalCnt; //총 데이터 수(게시판 형식에서 예외적으로 사용하는 실제 총 데이터수)
	private int firstRecordIndex; //페이지의 첫 번째 데이터
	private int lastRecordIndex; //페이지의 마지막 데이터

	public int getRecordCountPerPage() {
		return this.pageRowCount;
	}

	public void setRecordCountPerPage(int recordCountPerPage) {
		this.pageRowCount = recordCountPerPage;
	}

	public int getCurrentPageNo() {
		return this.pageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.pageNo = currentPageNo;
	}

	public void setTotalRecordCount(int totalRecordCount) {
		this.totalCnt = totalRecordCount;
	}
		
	public int getTotalRecordCount() {
		return this.totalCnt;
	}
	
	public void setRealTotalRecordCount(int realTotalRecordCount) {
		this.realTotalCnt = realTotalRecordCount;
	}
	
	public int getRealTotalRecordCount() {
		return this.realTotalCnt;
	}
	
	public int getFirstRecordIndex() {
		this.firstRecordIndex = (this.getCurrentPageNo() - 1) * this.getRecordCountPerPage();
		return this.firstRecordIndex;
	}

	public int getLastRecordIndex() {
		this.lastRecordIndex = this.getCurrentPageNo() * this.getRecordCountPerPage();
		return this.lastRecordIndex;
	}

	@SuppressWarnings("rawtypes")
	public abstract Map init(Map<String, Object> arg0);

	@SuppressWarnings("rawtypes")
	public abstract void setTotalRecordCount(List<Map> arg0);
}