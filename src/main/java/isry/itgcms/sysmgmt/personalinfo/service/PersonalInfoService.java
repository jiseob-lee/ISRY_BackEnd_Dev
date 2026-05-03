package isry.itgcms.sysmgmt.personalinfo.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

public interface PersonalInfoService {
	
	public Integer isExcelDownloadRegistered(Integer menuNo, String fileName, String menuUrl) throws Exception;
	
	public boolean isPersonalInfo(String exportTitle, Integer menuNo, String menuUrl) throws Exception;
	
	public void recordPersonalInfoDownloadReason(Map<String, String> map) throws Exception;
	
	public String getMenuNm(String menuUrl) throws Exception;

	public List<Map<String, Object>> selectExcelDownload(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public List<Map<String, Object>> selectExcelDownloadList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public List<Map<String, Object>> selectExcelDownloadList(Map<String, Object> dmSearchMap) throws Exception;
	
	public Integer selectExcelDownloadListCount(Map<String, Object> dmSearchMap) throws Exception;
	
	public List<Map<String, String>> selectMenuNm(DataRequest dataRequest) throws Exception;
	
	public void saveExcelDownload(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public boolean saveExcelDownloadReason(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public List<Map<String, Object>> selectLongTermNotConnect(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public boolean checkReconfirmPassword(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public void saveSystemEnv(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, String> selectWorkerInfo(Map<String, Object> loginMap) throws Exception;
	
	public List<Map<String, String>> selectUnitSystemList(Map<String, String> workerMap) throws Exception;

	public Map<String, String> saveWorkerInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, String> deleteWorkerInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, String> selectSystemEnv() throws Exception;
	
	public Map<String, String> selectInstituteInfo(Map<String, Object> loginMap) throws Exception;
	
	public Map<String, String> selectYouthInfo(Map<String, Object> loginMap) throws Exception;
	
	public Map<String, String> selectGuardianInfo(Map<String, Object> loginMap) throws Exception;
	
	public Map<String, String> selectWorkerInfoNo(Map<String, String> noMap) throws Exception;

	public Map<String, String> selectInstituteInfoNo(Map<String, String> noMap) throws Exception;
	
	public Map<String, String> selectYouthInfoNo(Map<String, String> noMap) throws Exception;
	
	public Map<String, String> selectGuardianInfoNo(Map<String, String> noMap) throws Exception;
	
	public String selectPersonalInfoId(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public String savePersonalInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public String savePersonalInfo(HttpServletRequest request, Map<String, Object> map) throws Exception;

	// 개인정보 삭제
	public Map<String, String> deletePersonalInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 학력 정보 조회
	public List<Map<String, Object>> selectEducation(String enfsnNo) throws Exception;

	// 자격 정보 조회
	public List<Map<String, Object>> selectQualification(String qlfcInfoMngNo) throws Exception;
	
	// 근무 이력 조회
	public List<Map<String, Object>> selectWork(String enfsnNo) throws Exception;
	
	// 자격정보관리번호 채번
	public String selectQualificationNo(Map<String, String> map) throws Exception;
	
	// 종사자정보
	public List<Map<String, Object>> selectEnfsnInfo(String enfsnNo) throws Exception;
	
	// 종사자자격증
	public List<Map<String, Object>> selectEnfsnCerti(String enfsnNo) throws Exception;
	
	// 종사자전문인력양성교육
	public List<Map<String, Object>> selectEnfsnTrnngEdu(String enfsnNo) throws Exception;
	
	// 종사자청소년관련민간자격증
	public List<Map<String, Object>> selectEnfsnYngbgsPrvateCerti(String enfsnNo) throws Exception;
	
	// 기관 유형
	public Map<String, String> selectInstType(int instNo) throws Exception;
	

	// 개인 정보 조회
	public List<Map<String, Object>> selectPersonalInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	// 회원 탈퇴 처리
	public void saveWithdrawal(HttpServletRequest request) throws Exception;
	
	// 종사자자격증
	public List<Map<String, Object>> selectCommonuUseUnit(String codeId) throws Exception;
	
	// 사용자기관정보
	public List<Map<String, Object>> selectAplyInstList(String userId) throws Exception;
	
	/**
	 * @Method명   : processAuthrtReset
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 5. 19. 
	 * @Method설명 : 권한 초기화 처리
	 */
	public void processAuthrtReset(HttpServletRequest request, DataRequest dataRequest) throws Exception;
}
