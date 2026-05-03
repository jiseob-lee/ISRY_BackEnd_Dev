package isry.itgcms.sysmgmt.personalinfo.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("personalInfoMapper")
public interface PersonalInfoMapper {
	
	public String isPersonalInfo(Map<String, Object> map) throws Exception;
	
	public String isPersonalInfoUrl(Map<String, Object> map) throws Exception;
	
	public Integer isExcelDownloadRegistered(Map<String, Object> map) throws Exception;
	
	public Integer isExcelDownloadRegisteredUrl(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> selectExcelDownload() throws Exception;

	public List<Map<String, Object>> selectExcelDownloadList() throws Exception;

	public List<Map<String, Object>> selectExcelDownloadList(Map<String, Object> dmSearchMap) throws Exception;
	
	public Integer selectExcelDownloadListCount(Map<String, Object> dmSearchMap) throws Exception;
	
	public List<Map<String, String>> selectMenuNm(Map<String, String> map) throws Exception;
	
	public void deleteExcelDownload(Map<String, String> map) throws Exception;
	
	public void insertExcelDownload(Map<String, String> map) throws Exception;
	
	public void updateExcelDownload(Map<String, String> map) throws Exception;
	
	public void saveExcelDownloadReason(Map<String, Object> map) throws Exception;
	
	public Integer checkReconfirmPassword(Map<String, String> map) throws Exception;
	
	public void saveLongTermNotConnect(Map<String, Object> map) throws Exception;
	
	public void deleteSystemEnv() throws Exception;
	
	public void saveSystemEnv(Map<String, Object> map) throws Exception;
	
	public void saveSystemEnvLog(Map<String, Object> map) throws Exception;

	
	public Map<String, String> selectUserInfo(Map<String, Object> map) throws Exception;
	
	public Map<String, String> selectPersonalInfo(Map<String, String> map) throws Exception;
	
	public Map<String, String> selectWorkerInfo(Map<String, String> map) throws Exception;
	
	public List<Map<String, String>> selectUnitSystemList(Map<String, String> map) throws Exception;
	
	public Map<String, String> selectInstituteInfo(Map<String, String> map) throws Exception;
	
	public Map<String, String> selectYouthInfo(Map<String, String> map) throws Exception;
	
	public Map<String, String> selectGuardianInfo(Map<String, String> map) throws Exception;
	
	public void updateWorkerInfo(Map<String, Object> map) throws Exception;
	
	public void updateWorkerInfoHistory(Map<String, Object> map) throws Exception;
	
	public void updateUserInfo(Map<String, Object> map) throws Exception;
	
	public int updatePersonalInfo(Map<String, Object> map) throws Exception;
	
	public void deleteWorkerUnitSystem(Map<String, Object> map) throws Exception;
	
	public void insertWorkerUnitSystem(Map<String, String> map) throws Exception;
	
	public int getUserAuthCnt(Map<String, Object> map) throws Exception;
	
	public int updateUserInstAuth(Map<String, Object> map) throws Exception;
	
	public String selectUserAuthSeCd(Map<String, Object> map) throws Exception;
	
	public Map<String, String> selectSystemEnv() throws Exception;
	
	public List<Map<String, String>> selectLongTermNotConnect() throws Exception;

	public Map<String, String> selectUserInfoWorker(Map<String, String> noMap) throws Exception;
	
	public Map<String, String> selectUserInfoInstitute(Map<String, String> noMap) throws Exception;
	
	public Map<String, String> selectUserInfoYouthGuardian(Map<String, String> noMap) throws Exception;

	
	public int selectPersonalInfoIsExists(Map<String, String> map) throws Exception;
	
	public String selectPersonalInfoId(Map<String, String> map) throws Exception;
	
	public Integer insertPersonalInfo(Map<String, Object> map) throws Exception;
	
	public void insertPersonalInfoHistory(Map<String, Object> map) throws Exception;
	
	// 학력 정보 초기화
	public void deleteEducation(String enfsnNo) throws Exception;
	
	// 학력 정보 입력
	public void insertEducation(Map<String, String> map) throws Exception;


	// 학력 정보 조회
	public List<Map<String, Object>> selectEducation(String enfsnNo) throws Exception;
	
	// 자격 정보 조회
	public List<Map<String, Object>> selectQualification(String qlfcInfoMngNo) throws Exception;
	
	// 근무 이력 조회
	public List<Map<String, Object>> selectWork(String enfsnNo) throws Exception;

	
	
	// 학력 정보 초기화
	public void deleteWork(String enfsnNo) throws Exception;
	
	// 학력 정보 입력
	public void insertWork(Map<String, String> map) throws Exception;

	
	// 자격정보관리번호 채번
	public String selectQualificationNo(Map<String, String> map) throws Exception;
	
	// 자격정보관리번호 입력
	public void insertQualificationNo(Map<String, String> map) throws Exception;
	
	// 자격 정보 입력
	public void insertQualification(Map<String, String> map) throws Exception;
	
	// 자격 정보 삭제
	public void deleteQualification(String qlfcInfoMngNo) throws Exception;
	
	// 종사자정보
	public List<Map<String, Object>> selectEnfsnInfo(String enfsnNo) throws Exception;
	
	// 종사자자격증
	public List<Map<String, Object>> selectEnfsnCerti(String enfsnNo) throws Exception;
	
	// 종사자전문인력양성교육
	public List<Map<String, Object>> selectEnfsnTrnngEdu(String enfsnNo) throws Exception;
	
	// 종사자청소년관련민간자격증
	public List<Map<String, Object>> selectEnfsnYngbgsPrvateCerti(String enfsnNo) throws Exception;
	
	// 종사자정보 삭제
	public void delEnfsnInfo(String enfsnNo) throws Exception;
	
	// 종사자정보 저장
	public void insEnfsnInfo(Map<String, String> map) throws Exception;
	
	// 종사자자격증 저장
	public void insEnfsnCerti(Map<String, String> map) throws Exception;
	
	// 종사자자격증 수정
	public void updEnfsnCerti(Map<String, String> map) throws Exception;
	
	// 종사자자격증 삭제
	public void delEnfsnCerti(Map<String, String> map) throws Exception;
	
	// 종사자전문인력양성교육 저장
	public void insEnfsnTrnngEdu(Map<String, String> map) throws Exception;
	
	// 종사자전문인력양성교육 수정
	public void updEnfsnTrnngEdu(Map<String, String> map) throws Exception;
	
	// 종사자전문인력양성교육 삭제
	public void delEnfsnTrnngEdu(Map<String, String> map) throws Exception;
	
	// 종사자청소년관련민간자격증 저장
	public void insEnfsnPrvateCerti(Map<String, String> map) throws Exception;
	
	// 종사자청소년관련민간자격증 수정
	public void updEnfsnPrvateCerti(Map<String, String> map) throws Exception;
	
	// 종사자청소년관련민간자격증 삭제
	public void delEnfsnPrvateCerti(Map<String, String> map) throws Exception;	
	
	// 기관 타입
	public Map<String, String> selectInstType(int instNo) throws Exception;
	
	
	// 개인 정보 조회
	public List<Map<String, Object>> searchPersonalInfo(Map<String, String> map) throws Exception;

	// 개인정보 수정에서 종사자 정보를 업데이트 함.
	public int updateWorkerInfoPersonal(Map<String, Object> map) throws Exception;
	public void updateWorkerInfoPersonalHistory(Map<String, Object> map) throws Exception;
	
	// 개인정보 수정에서 내담자 정보를 업데이트 함.
	public int updateClientInfoPersonal(Map<String, Object> map) throws Exception;
	public void updateClientInfoPersonalHistory(Map<String, Object> map) throws Exception;

	// 개인식별번호에 해당하는 종사자 번호를 구함.
	public List<String> selectWorkerPersonal(String no) throws Exception;
	// 개인식별번호에 해당하는 내담자 번호를 구함.
	public List<String> selectClientPersonal(String no) throws Exception;

	// 회원 탈퇴
	public void saveWithdrawal(Map<String, String> map) throws Exception;
	
	// 회원 탈퇴 이력
	public void saveWithdrawalHistory(Map<String, String> map) throws Exception;

	
	// 사용자 이력
	public void insertUserInfoHistory(Map<String, Object> map) throws Exception;
	
	// 사용자 데이터 존재 여부 체크
	public Integer selectUserInfoExists(String userId) throws Exception;

	
	// 종사자 정보가 존재하는지 체크
	public Integer selectWorkerInfoExists(Map<String, String> map) throws Exception;
	
	// 종사자 정보를 삭제 처리함.
	public void deleteWorkerInfo(Map<String, String> map) throws Exception;
	

	// 청소년, 보호자 정보가 존재하는지 체크
	public Integer selectYouthGuardianInfoExists(Map<String, String> map) throws Exception;
	
	// 정소년, 보호자 정보를 삭제 처리함.
	public Integer deleteYouthGuardianInfo(Map<String, String> map) throws Exception;
	

	// 개인 정보가 존재하는지 체크
	public Integer selectPersonalInfoExists(Map<String, String> map) throws Exception;
	
	// 개인 정보를 삭제 처리함.
	public Integer deletePersonalInfo(Map<String, String> map) throws Exception;
	
	// 코드 사용 단위업무 조회
	public List<Map<String, Object>> selectCommonuUseUnit(String no) throws Exception;
	
}
