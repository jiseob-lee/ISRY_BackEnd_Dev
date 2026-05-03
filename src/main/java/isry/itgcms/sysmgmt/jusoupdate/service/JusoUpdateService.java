/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부 및 위기 청소년 통합 지원 시스템 관리팀의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/

package isry.itgcms.sysmgmt.jusoupdate.service;

import java.util.List;
import java.util.Map;

/**
 * 
 * @파일명        : JusoUpdateService.java
 * @프로그램 설명 : 도로명 주소 업데이트
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 29. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 29.
 * @수정내용      : 
 * -                
 * -
 */
public interface JusoUpdateService {

	public boolean jusoUpdateInit1() throws Exception;
	public boolean jusoUpdateInit2() throws Exception;
	public boolean jusoUpdateInit3() throws Exception;
	public boolean jusoUpdateInit4() throws Exception;

	public boolean jusoUpdate(String dateFrom, String dateTo) throws Exception;
	
	public boolean jusoProcessAddrData1() throws Exception;
	public boolean jusoProcessAddrData2() throws Exception;
	public boolean jusoProcessAddrData3() throws Exception;
	
	public boolean jusoProcessAddrData4(String dateFrom, String dateTo) throws Exception;
	
	public void dropIndex() throws Exception;
	public boolean createIndex() throws Exception;
	
	public List<Map<String, String>> jusoGetJusoUpdateResults() throws Exception;
	public void jusoSetUpdateCount(String currentDate) throws Exception;
	
	public void jusoProcessSetEmdTruncate() throws Exception;
	public void jusoProcessSetEmd() throws Exception;
	public void jusoProcessSetEmdRegional(String region) throws Exception;

	public void jusoUpdateComment() throws Exception;
	
}
