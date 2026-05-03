/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.medscsnntabrd.mapper;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : BbsonmMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Song.Young.Il
 * @작성일        : 2022. 5. 13. 
 * @수정자        : Song.Young.Il
 * @수정일        : 2022. 5. 13.
 * @수정내용      : 
 * -                
 * -                
 */

@Mapper("bbseumListMapper")
public interface BbseumListMapper {
	List<Map<String , Object>> nonRepSelectBbseumList(Map<String, Object> mapParam);
	
	List<Map<String , Object>> repSelectBbseumList(Map<String, Object> mapParam);
	
	List<Map<String , Object>> selectBbseumList(Map<String, Object> mapParam);
	
	List<Map<String, Object>> selectBbseumDetail(Map<String, Object> mapParam);
	
	List<Map<String, Object>> selectRespodDetail(Map<String, Object> mapParam);
	
	List<Map<String, Object>> insertCounselor(Map<String, Object> mapParam);
	
	int selectCrisBrdCnt(Map<String, Object> mapParam);
	
	int selectCrisPrsCnt(Map<String, Object> mapParam);

	int selectBrdMaxCnt();

	void insertBbseum(Map<String, String> mapIns);
	
	void updateBbseum(Map<String, String> mapUpd);
	
	void deleteBbseum(Map<String, String> mapDel);

	void insertRespod(Map<String, String> mapIns);
	
	void updateRespod(Map<String, String> mapUpd);
	
	void deleteRespod(Map<String, String> mapDel);

	void bbseumDtlCnt(Map<String, Object> mapParam);
	
	void respodDtlCnt(Map<String, Object> mapParam);
	
	void updateBbseumRespod(Map<String, Object> mapAns);
	
	void insertCrisis(Map<String, Object> mapParam);
	
	void insertCrisisBoard(Map<String, Object> mapParam);
	
	void insertCrisisPerson(Map<String, Object> mapParam);
	
	void updateCrisisBoard(Map<String, Object> mapParam);
	
	void updateCrisisPerson(Map<String, Object> mapParam);
	
	int getTotalCount(Map<String, Object> mapParam);
	
//	-----------------------------------------------------
	void insertMemo(Map<String, Object> mapParam);

	List<Map<String, Object>> selectMemo(Map<String, Object> mapParam);
	
	void updateMemo(Map<String, Object> mapParam);
	
	List<Map<String, Object>> selectCounselor(Map<String, Object> mapParam);
	
	void insertCounselor2(Map<String, Object> mapParam);
	
	void updateCounselor(Map<String, Object> mapParam);
	
	List<Map<String, String>> eumContentList(Map<String, String> mapParam);
	
	void insertAYE230(Map<String, String> mapParam);
	
	void updateAYE200(Map<String, String> mapParam);

	void updateMail(Map<String, String> mapParam);

}
