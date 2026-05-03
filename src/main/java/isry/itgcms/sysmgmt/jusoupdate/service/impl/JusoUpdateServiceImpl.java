/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부 및 위기 청소년 통합 지원 시스템 관리팀의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/

package isry.itgcms.sysmgmt.jusoupdate.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseServiceImpl;
import isry.itgcms.sysmgmt.jusoupdate.mapper.JusoUpdateMapper;
import isry.itgcms.sysmgmt.jusoupdate.service.JusoUpdateService;
import isry.itgcms.util.UnZip;
import kr.go.ads.client.ADSReceiver;
import kr.go.ads.client.ADSUtils;
import kr.go.ads.client.ReceiveDatas;

/**
 * 
 * @파일명 : JusoUpdateServiceImpl.java
 * @프로그램 설명 : 도로명 주소 업데이트 - -
 * @작성자 : Lee.Ji.Seob
 * @작성일 : 2021. 12. 29.
 * @수정자 : Lee.Ji.Seob
 * @수정일 : 2021. 12. 29.
 * @수정내용 : - -
 */
@Service("jusoUpdateService")
public class JusoUpdateServiceImpl extends IsryBaseServiceImpl implements JusoUpdateService {
	
	//@Autowired 
	//private PlatformTransactionManager transactionManager;
	
	@Resource(name = "jusoUpdateMapper")
	private JusoUpdateMapper jusoUpdateMapper;

	private final String path = EgovProperties.getProperty("globals", "juso.update.folder");  //"D:\\work\\adsReceiver";
	
	private final String appKey = EgovProperties.getProperty("globals", "juso.update.appKey"); // 발급받은 승인키
	
	//private static int currentTimeInt = 0;

	@Override
	public boolean jusoUpdateInit1() throws Exception {
		
		try {
			
			// 임시 테이블 초기화
			jusoUpdateMapper.truncateSccoMvmn();
			jusoUpdateMapper.truncateSpbdBuld();
			//jusoUpdateMapper.truncateSprdStret();
			
			//jusoUpdateMapper.copySccoMvmn();
			//jusoUpdateMapper.copySpbdBuld();
			//jusoUpdateMapper.copySprdStret();

			//jusoUpdateMapper.createIndexBuilding3();
			//jusoUpdateMapper.createIndexJibeon3();
			
		} catch (SQLException e) {
			log.info("#### SQLException : " + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			log.info("#### Exception : " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		
		return true;
	}

	@Override
	public boolean jusoUpdateInit2() throws Exception {
		
		try {
			
			// 임시 테이블 초기화
			//jusoUpdateMapper.truncateSccoMvmn();
			//jusoUpdateMapper.truncateSpbdBuld();
			//jusoUpdateMapper.truncateSprdStret();
			
			jusoUpdateMapper.copySccoMvmn();
			//jusoUpdateMapper.copySpbdBuld();
			//jusoUpdateMapper.copySprdStret();

			//jusoUpdateMapper.createIndexBuilding3();
			//jusoUpdateMapper.createIndexJibeon3();
			
		} catch (SQLException e) {
			log.info("#### SQLException : " + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			log.info("#### Exception : " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		
		return true;
	}

	@Override
	public boolean jusoUpdateInit3() throws Exception {
		
		try {
			
			// 임시 테이블 초기화
			//jusoUpdateMapper.truncateSccoMvmn();
			//jusoUpdateMapper.truncateSpbdBuld();
			//jusoUpdateMapper.truncateSprdStret();
			
			//jusoUpdateMapper.copySccoMvmn();
			jusoUpdateMapper.copySpbdBuld();
			//jusoUpdateMapper.copySprdStret();

			//jusoUpdateMapper.createIndexBuilding3();
			//jusoUpdateMapper.createIndexJibeon3();
			
		} catch (SQLException e) {
			log.info("#### SQLException : " + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			log.info("#### Exception : " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		
		return true;
	}

	@Override
	public boolean jusoUpdateInit4() throws Exception {
		
		try {
			
			// 임시 테이블 초기화
			//jusoUpdateMapper.truncateSccoMvmn();
			//jusoUpdateMapper.truncateSpbdBuld();
			//jusoUpdateMapper.truncateSprdStret();
			
			//jusoUpdateMapper.copySccoMvmn();
			//jusoUpdateMapper.copySpbdBuld();
			//jusoUpdateMapper.copySprdStret();

			jusoUpdateMapper.createIndexBuilding3();
			jusoUpdateMapper.createIndexJibeon3();
			
		} catch (SQLException e) {
			log.info("#### SQLException : " + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			log.info("#### Exception : " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		
		return true;
	}
	
	@Override
	public boolean jusoUpdate(String dateFrom, String dateTo) throws Exception {
		
		//SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHH");
		//Date date = new Date();
		//String currentTime = formatter.format(date);
		
		//if (currentTimeInt < Integer.parseInt(currentTime)) {
			//currentTimeInt = Integer.parseInt(currentTime);
		//}
		
		//if (currentTimeInt != Integer.parseInt(currentTime)) {
			//return false;
		//}
		
		//currentTimeInt++;
		
		//TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        //TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
        try {
			String app_key = appKey; // 발급받은 승인키
			String date_gb = "D"; // 날짜구분 코드(D:일변동, M:월변동)
			String cntc_cd = "009000"; // 자료요청 구분 코드 (건물정보 + 관련지번)
			String retry_in = "Y"; // 재반영 데이터 포함여부(Y:재반영 데이터 포함 제공, N:재반영 데이터 미제공)
			String req_date_from = ""; // 요청일자 From(요청일자 설정시, 해당 일에 대한 정보만 제공 받음) : 일반적으로 NULL 이여야한다.
			String req_date_to = ""; // 요청일자 To(요청기간동안의 데이터를 요청시 설정, 단 From~To 사이의 기간이 10일을 초과할 수 없음)
	
			String strDateFrom = dateFrom;
			String strDateTo = dateTo;
			
			if (strDateFrom != null) {
				req_date_from = strDateFrom;
			}
			if (strDateTo != null) {
				req_date_to = strDateTo;
			}
			// String path = "D:\\work\\adsReceiver";
	
			String returnStr1 = receiveAddr(app_key, date_gb, cntc_cd, retry_in, req_date_from, req_date_to);
			String[] tmpArr = returnStr1.split("\\|");
			String returnCode1 = tmpArr[0];
			String returnMsg1 = tmpArr[1];
			
			/*
			cntc_cd = "009003"; // 도로명코드
	
			String returnStr2 = receiveAddr(app_key, date_gb, cntc_cd, retry_in, req_date_from, req_date_to);
			tmpArr = returnStr2.split("\\|");
			String returnCode2 = tmpArr[0];
			String returnMsg2 = tmpArr[1];
			*/
			
			// DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
			LocalDateTime now = LocalDateTime.now();
			log.info(dtf.format(now));
			String currentDate = dtf.format(now);
	
			if (strDateFrom == null) {
				strDateFrom = currentDate;
			}
			if (strDateTo == null) {
				strDateTo = currentDate;
			}
	
			log.info("#### strDateFrom : " + strDateFrom);
			log.info("#### strDateTo : " + strDateTo);
	
			Map<String, String> map = new HashMap<>();
			map.put("returnCode1", returnCode1);
			map.put("returnMsg1", returnMsg1);
			//map.put("returnCode2", returnCode2);
			//map.put("returnMsg2", returnMsg2);
			
			log.info("#### returnCode1 : " + returnCode1);
			
			if ("P0000".equals(returnCode1) || "P1000".equals(returnCode1)) {
				//if ("P0000".equals(returnCode2) || "P1000".equals(returnCode2)) {
	
				log.info("#### jusoProcessAddr start.");
				
					// 데이터 입력 처리
					jusoProcessAddr(map, strDateFrom, strDateTo);
					
					//transactionManager.commit(transactionStatus);
					
					// 데이터 변경분과 동기화 처리
					//processAddrData(strDateFrom, strDateTo);
					
					
					// 테이블 비우기
					//jusoUpdateMapper.truncateSymAddr2I();
					// 조인 데이터 생성
					//jusoUpdateMapper.initSymAddr2I();
					
					
					//transactionManager.commit(transactionStatus);
					
					
					// 변경분 데이터 적용
					//jusoUpdateMapper.truncateBuilding();
					//jusoUpdateMapper.truncateJibeon();
					//jusoUpdateMapper.truncateDoroCode();
					//jusoUpdateMapper.copyBuilding3();
					//jusoUpdateMapper.copyJibeon3();
					//jusoUpdateMapper.copyDoroCode3();
					
					//transactionManager.commit(transactionStatus);
	
					// 테이블 비우기
					//jusoUpdateMapper.truncateSymAddrI();
					// 차이분 입력
					//jusoUpdateMapper.insertSymAddrI();
					
					
					//int dateFromInt = Integer.parseInt(dateFrom);
					//int dateToInt = Integer.parseInt(dateTo);
					
					//Map<String, String> map1 = new HashMap<>();
					
					//for (int i = dateFromInt; i <= dateToInt; i++) {
						//currentDate = String.valueOf(i);
						//map1.put("currentDate", currentDate);
						//jusoUpdateMapper.setJusoUpdateDataResult(map1);
					//}
	
					
				//}
			}
			
			return true;

        } catch (IOException e) {
        	return false;
        } catch (Exception e) {
        	return false;
        }
	}

	public String receiveAddr(String app_key, String date_gb, String cntc_cd, String retry_in, String req_date_from,
			String req_date_to) throws Exception {

		ADSReceiver adsReceiver = new ADSReceiver();

		// 읷변동 자료를 저장핛 파읷경로를 설정합니다.
		adsReceiver.setFilePath(path);
		adsReceiver.setCreateDateDirectory(ADSUtils.YYMMDD);

		try {
			// 변동자료 연계서비스 요청 및 응답데이터 확읶
			ReceiveDatas receiveDatas = adsReceiver.receiveAddr(app_key, date_gb, cntc_cd, retry_in, req_date_from,
					req_date_to);
			/*
			 * --------------------------------- 응답 결과 확읶 ---------------------------------
			 */
			if (receiveDatas.getResult() != 0) {
				if (receiveDatas.getResult() == -1) {
					// 서버 접속 실패 : 잠시후 재 시도 하시기 바랍니다.
					log.info("서버 접속 실패");
				}
				// 서버 페이지 오류 사항 확읶
				log.info("Result code : " + receiveDatas.getResult());
				log.info("Response code : " + receiveDatas.getResCode());
				log.info("Response Msg : " + receiveDatas.getResMsg());
				return receiveDatas.getResCode() + "|" + receiveDatas.getResMsg();
			}
			// 서버 응답 확읶
			log.info("Response code : " + receiveDatas.getResCode()); // 응답코드
			log.info("Response Msg : " + receiveDatas.getResMsg()); // 응답메시지
			// if (!"P0000".equals(receiveDatas.getResCode())) {
			return receiveDatas.getResCode() + "|" + receiveDatas.getResMsg();
			// }
			/*
			 * --------------------------------- 응답 결과 완료 ---------------------------------
			 */
			// 결과 데이터 정렬
			/*
			 * ArrayList<ReceiveData> result =
			 * receiveDatas.getReceiveDatas(ADSUtils.UPDATE_ASC); Iterator<ReceiveData> itr
			 * = result.iterator(); while (itr.hasNext()) { // 결과 데이터 건별 정보 확읶 //ReceiveData
			 * receiveData = (ReceiveData) itr.next(); ReceiveData receiveData = itr.next();
			 * System.out.print(" CNTC : "); System.out.print(receiveData.getCntcCode());
			 * System.out.print(" RES_CODE : ");
			 * log.info(receiveData.getResCode()); if
			 * (!"P0000".equals(receiveData.getResCode())) { // 해당 파읷응답 에러. 특히 E1001 읶경우, 해당
			 * 파읷을 아직 생성하지 못핚 응답으로 추후 재시도 필요.
			 * log.info("해당파읷에 대핚 응답이 정상이 아니기에 재 요청 필요"); } }
			 */
		} catch (IOException e) {
			log.info(e.getMessage());
			return "E0000|Error";
		} catch (Exception e) {
			log.info(e.getMessage());
			return "E0000|Error";
		}
	}

	private void jusoProcessAddr(Map<String, String> map, String dateFrom, String dateTo) throws Exception {
		
		int dateFromInt = Integer.parseInt(dateFrom);
		int dateToInt = Integer.parseInt(dateTo);
		
		for (int i = dateFromInt; i <= dateToInt; i++) {
			
			String currentDate = String.valueOf(i);
			
			log.info("#### currentDate : " + currentDate);
			
			map.put("currentDate", currentDate);
			
			Map<String, String> map2 = jusoUpdateMapper.jusoGetJusoUpdateResult(map);
			
			if (map2 != null && "SUCCS".equals(map2.get("RETURN_CODE3"))
					&& "SUCCS".equals(map2.get("DATA_PROCESS"))
					&& Integer.parseInt(map2.get("UPDATE_COUNT")) <= 3) {
				log.info("#### continue.");
				continue;
			}
			
			String sccoMvmn = "AlterD.JUSUBM." + currentDate + ".TI_SCCO_MVMN.TXT"; // 관련지번
			String spbdBuld = "AlterD.JUSUBM." + currentDate + ".TI_SPBD_BULD.TXT"; // 건물정보
			//String sprdStret = "AlterD.JUSUZC." + currentDate + ".TI_SPRD_STRET.TXT"; // 도로명코드

			try {
				
				String fileSeparator = FileSystems.getDefault().getSeparator();
				
				String dirStr = "";
				File dir = new File(path + fileSeparator + currentDate.substring(2));
				if (dir.exists()) {
					dirStr = path + fileSeparator + currentDate.substring(2) + fileSeparator;
					log.info("#### dirStr1 : " + dirStr);
				}
				if ("".equals(dirStr)) {
					dir = new File(path + fileSeparator + currentDate);
					if (dir.exists()) {
						dirStr = path + fileSeparator + currentDate + fileSeparator;
						log.info("#### dirStr2 : " + dirStr);
					}
				}
				
				log.info("#### dirStr3 : " + dirStr);
				
				if ("".equals(dirStr)) {
					return;
				}

				// 압축 파일 위치와 압축된 파일
				String zipPath = dirStr;
				String zipFile = "AlterD.JUSUBM." + currentDate + ".ZIP";
				
				// 압축을 해제할 위치, 압축할 파일이름
				String unZipPath = dirStr;
				
				log.info("--------- 압축 해제 ---------");
				
				UnZip unZip = new UnZip();
				
				log.info("#### zipPath : " + zipPath);
				log.info("#### zipFile : " + zipFile);
				log.info("#### unZipPath : " + unZipPath);
				
				// 압축 해제
				if (!unZip.unZip(zipPath, zipFile, unZipPath)) {
					log.info("압축 해제 실패");
					return;
				}
				
				
				Map<String, String> map1 = new HashMap<>();
				
				// 관련 지번
				//BufferedReader reader = new BufferedReader(new FileReader(dirStr + sccoMvmn));
				InputStreamReader is = null;
				BufferedReader reader = null;

				String str = "";
				String[] tmpArr = null;
				
				try {
					is = new InputStreamReader(new FileInputStream(dirStr + sccoMvmn), "euc-kr");
					reader = new BufferedReader(is);
					
					log.info("sccoMvmn---------------------------");
					// law_dong_code|sido_name|sigungu_name|law_emd_name|law_ri_name|mountain_yn|jibeon_bonbeon|jibeon_bubeon|doro_name_code|jiha_yn|build_bonbeon|build_bubeon|jibeon_serial_no|move_reason_code
					while ((str = reader.readLine()) != null) {
						log.info(str);
						if ("No Data".equals(str) || str.indexOf("|") == -1) {
							continue;
						}
						tmpArr = str.split("\\|", -1);
						map1.clear();
						map1.put("STDG_CD", tmpArr[0]);
						map1.put("CTPV_NM", tmpArr[1]);
						map1.put("SGG_NM", tmpArr[2]);
						map1.put("STTY_EMD_NM", tmpArr[3]);
						map1.put("STLI_NM", tmpArr[4]);
						map1.put("MTN_YN", tmpArr[5]);
						map1.put("LOTNO_MNO_NO", tmpArr[6]);
						map1.put("LOTNO_SNO_NO", tmpArr[7]);
						map1.put("ROAD_NM_CD", tmpArr[8]);
						map1.put("UDGD_YN", tmpArr[9]);
						map1.put("BMNO_NO", tmpArr[10]);
						map1.put("BSNO_NO", tmpArr[11]);
						map1.put("LOTNO_SN", tmpArr[12]);
						map1.put("MVMN_CS_CD", tmpArr[13]);
	
						switch (map1.get("MVMN_CS_CD")) {
						case "31" :
							jusoUpdateMapper.insertSccoMvmn(map1);
							break;
						case "34" :
							jusoUpdateMapper.updateSccoMvmn(map1);
							break;
						case "63" :
							jusoUpdateMapper.deleteSccoMvmn(map1);
							break;
						default:
							break;
						}
					}

				} catch (IOException e) {
					log.info(e.getMessage());

				} catch (Exception e) {
					log.info(e.getMessage());
					
				} finally {
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException e) {
							log.info(e.getMessage());
						}
					}
					if (is != null) {
						try {
							is.close();
						} catch (IOException e) {
							log.info(e.getMessage());
						}
					}
				}
				// 출처: https://hianna.tistory.com/587 [어제 오늘 내일]
		
				// 건물
				//reader = new BufferedReader(new FileReader(dirStr + spbdBuld));
				
				try {
					is = new InputStreamReader(new FileInputStream(dirStr + spbdBuld), "euc-kr");
					reader = new BufferedReader(is);
					
					log.info("spbdBuld---------------------------");
					// law_dong_code|sido_name|sigungu_name|law_emd_name|law_ri_name|mountain_yn|jibeon_bonbeon|jibeon_bubeon|doro_name_code|doro_name|jiha_yn|build_bonbeon|build_bubeon|gcmdj_build_name|detail_build_name|gm_manage_no|emd_sirial_no|hjd_code|hjd_name|post_zip|post_serial_no|mass_bdc_name|move_reason_code|gosi_day|bdj_doro_juso|sigungu_build_name|union_house_yn|base_area_no|detail_addr_yn|etc1|etc2
					while ((str = reader.readLine()) != null) {
						log.info(str);
						if ("No Data".equals(str) || str.indexOf("|") == -1) {
							continue;
						}
						tmpArr = str.split("\\|", -1);
						map1.clear();
						map1.put("STDG_CD", tmpArr[0]);
						map1.put("CTPV_NM", tmpArr[1]);
						map1.put("SGG_NM", tmpArr[2]);
						map1.put("STTY_EMD_NM", tmpArr[3]);
						map1.put("STLI_NM", tmpArr[4]);
						map1.put("MTN_YN", tmpArr[5]);
						map1.put("LOTNO_MNO_NO", tmpArr[6]);
						map1.put("LOTNO_SNO_NO", tmpArr[7]);
						map1.put("ROAD_NM_CD", tmpArr[8]);
						map1.put("ROAD_NM", tmpArr[9]);
						map1.put("UDGD_YN", tmpArr[10]);
						map1.put("BMNO_NO", tmpArr[11]);
						map1.put("BSNO_NO", tmpArr[12]);
						map1.put("BDRG_BLDG_NM", tmpArr[13]);
						map1.put("DTL_BLDG_NM", tmpArr[14]);
						map1.put("BLDG_MNG_NO", tmpArr[15]);
						map1.put("EMD_SN", tmpArr[16]);
						map1.put("DONG_CD", tmpArr[17]);
						map1.put("DONG_NM", tmpArr[18]);
						map1.put("ZIP", tmpArr[19]);
						map1.put("PST_SN", tmpArr[20]);
						map1.put("MUCH_THDSTN_NM", tmpArr[21]);
						map1.put("MVMN_CS_CD", tmpArr[22]);
						map1.put("ANCMNT_YMD", tmpArr[23]);
						map1.put("BFFLTT_ROAD_NM_ADDR", tmpArr[24]);
						map1.put("SGG_BLDG_NM", tmpArr[25]);
						map1.put("APTCPX_YN", tmpArr[26]);
						map1.put("BSIS_ZONE_NO", tmpArr[27]);
						map1.put("DADDR_YN", tmpArr[28]);
						map1.put("RM_CN1", tmpArr[29]);
						map1.put("RM_CN2", tmpArr[30]);
						
						switch (map1.get("MVMN_CS_CD")) {
						case "31" :
						case "73" :
							jusoUpdateMapper.insertSpbdBuld(map1);
							break;
						case "34" :
							jusoUpdateMapper.updateSpbdBuld(map1);
							break;
						case "63" :
						case "72" :
							jusoUpdateMapper.deleteSpbdBuld(map1);
							break;
						default:
							break;
						}
					}

				} catch (IOException e) {
					log.info(e.getMessage());

				} catch (Exception e) {
					log.info(e.getMessage());
					
				} finally {
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException e) {
							log.info(e.getMessage());
						}
					}
					if (is != null) {
						try {
							is.close();
						} catch (IOException e) {
							log.info(e.getMessage());
						}
					}
				}
		
				/*
				// 도로명 코드
				//reader = new BufferedReader(new FileReader(dirStr + sprdStret));
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(dirStr + sprdStret), "euc-kr"));
				
				log.info("sprdStret---------------------------");
				// sigungu_code|doro_name_no|doro_name|en_doro_name|emd_serial|sido_name|sigungu_name|emd_gubun|emd_code|emd_name|up_doro_no|up_doro_name|use_yn|change_history_reason|change_history_info|en_sido_name|en_sigungu_name|en_emd_name|gosi_date|erasure_date
				while ((str = reader.readLine()) != null) {
					log.info(str);
					if ("No Data".equals(str) || str.indexOf("|") == -1) {
						continue;
					}
					tmpArr = str.split("\\|", -1);
					map1.clear();
					map1.put("SGG_CD", tmpArr[0]);
					map1.put("ROAD_NM_NO", tmpArr[1]);
					map1.put("ROAD_NM", tmpArr[2]);
					map1.put("ENG_ROAD_NM", tmpArr[3]);
					map1.put("EMD_SN", tmpArr[4]);
					map1.put("CTPV_NM", tmpArr[5]);
					map1.put("SGG_NM", tmpArr[6]);
					map1.put("EMD_SE_CD", tmpArr[7]);
					map1.put("EMD_CD", tmpArr[8]);
					map1.put("EMD_NM", tmpArr[9]);
					map1.put("UP_ROAD_NM_NO", tmpArr[10]);
					map1.put("UP_ROAD_NM", tmpArr[11]);
					map1.put("USE_YN", tmpArr[12]);
					map1.put("CHG_HSTR_CS_SE_CD", tmpArr[13]);
					map1.put("CHG_HSTR_INFO_CN", tmpArr[14]);
					map1.put("ENG_CTPV_NM", tmpArr[15]);
					map1.put("ENG_SGG_NM", tmpArr[16]);
					map1.put("ENG_EMD_NM", tmpArr[17]);
					map1.put("ANCMNT_YMD", tmpArr[18]);
					map1.put("ERSR_YMD", tmpArr[19]);
					map1.put("ROAD_NM_CD", tmpArr[0] + tmpArr[1]);

					switch (map1.get("USE_YN")) {
					case "1" :
						jusoUpdateMapper.deleteSprdStret(map1);
						break;
					default :
						jusoUpdateMapper.mergeSprdStret(map1);
						break;
					}
				}
				
				reader.close();
				*/
		

				// 처리 결과 DB 등록
				map.put("returnCode3", "SUCCS");
				jusoUpdateMapper.jusoSetJusoUpdateResult(map);
	
				
			} catch (IOException e) {
				log.info(e.getMessage());
				
				// 처리 결과 DB 등록
				map.put("returnCode3", "FAIL");
				jusoUpdateMapper.jusoSetJusoUpdateResult(map);
				
				throw e;	
			
			} catch (Exception e) {
				log.info(e.getMessage());
				
				// 처리 결과 DB 등록
				map.put("returnCode3", "FAIL");
				jusoUpdateMapper.jusoSetJusoUpdateResult(map);
				
				throw e;
			}
		}
	}
	

	@Override
	public boolean jusoProcessAddrData1() throws Exception {

		try {
			// 테이블 비우기
			jusoUpdateMapper.truncateSymAddr2I();
			// 조인 데이터 생성
			jusoUpdateMapper.initSymAddr2I();
			
			return true;

		} catch (IOException e) {
			log.info(e.getMessage());
			return false;
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean jusoProcessAddrData2() throws Exception {
	
		try {
			// 변경분 데이터 적용
			jusoUpdateMapper.truncateBuilding();
			jusoUpdateMapper.truncateJibeon();
			//jusoUpdateMapper.truncateDoroCode();
			jusoUpdateMapper.copyBuilding3();
			jusoUpdateMapper.copyJibeon3();
			//jusoUpdateMapper.copyDoroCode3();
			
			//jusoUpdateMapper.createIndexBuilding();
			//jusoUpdateMapper.createIndexJibeon();
			
			return true;

		} catch (IOException e) {
			log.info(e.getMessage());
			return false;
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}	

	@Override
	public boolean jusoProcessAddrData3() throws Exception {

		//try {
			
			// 테이블 비우기
			//jusoUpdateMapper.truncateSymAddrI();
			// 삭제할 데이터 선택
			//List<Map<String, String>> list = jusoUpdateMapper.selectDeleteSymAddrIData();
			// 차이분 삭제
			//for (int i=0; i < list.size(); i++) {
				//jusoUpdateMapper.deleteSymAddrIData(list.get(i));
			//}
			// 차이분 입력
			//jusoUpdateMapper.insertSymAddrI();
			
			return true;

		//} catch (Exception e) {
			//log.info(e.getMessage());
			//return false;
		//}
	}	

	@Override
	public boolean jusoProcessAddrData4(String dateFrom, String dateTo) throws Exception {

		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
			LocalDateTime now = LocalDateTime.now();
			//log.info(dtf.format(now));
			String currentDate = dtf.format(now);
	
			int dateFromInt = Integer.parseInt(currentDate);
			if (dateFrom != null) {
				dateFromInt = Integer.parseInt(dateFrom);
			}
			int dateToInt = Integer.parseInt(currentDate);
			if (dateTo != null) {
				dateToInt = Integer.parseInt(dateTo);
			}
			
			Map<String, String> map1 = new HashMap<>();
			
			for (int i = dateFromInt; i <= dateToInt; i++) {
				currentDate = String.valueOf(i);
				map1.put("currentDate", currentDate);
				jusoUpdateMapper.jusoSetJusoUpdateDataResult(map1);
			}

		} catch (IOException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}	

	@Override
	public void dropIndex() throws Exception {

		// 인덱스 삭제
		//try {
			jusoUpdateMapper.deleteIdx1();
		//} catch (Exception e) {
			//log.info(e.getMessage());
		//}
		//try {
			jusoUpdateMapper.deleteIdx2();
		//} catch (Exception e) {
			//log.info(e.getMessage());
		//}
		//try {
			jusoUpdateMapper.deleteIdx3();
		//} catch (Exception e) {
			//log.info(e.getMessage());
		//}
		//try {
			jusoUpdateMapper.deleteIdx4();
		//} catch (Exception e) {
			//log.info(e.getMessage());
		//}
		//try {
			jusoUpdateMapper.deleteIdx5();
		//} catch (Exception e) {
			//log.info(e.getMessage());
		//}
		//try {
			jusoUpdateMapper.deleteIdx6();
		//} catch (Exception e) {
			//log.info(e.getMessage());
		//}
	}
	
	@Override
	public boolean createIndex() throws Exception {

		try {
			// 인덱스 생성
			jusoUpdateMapper.createIdx1App();
			jusoUpdateMapper.createIdx2App();
			jusoUpdateMapper.createIdx3App();
			jusoUpdateMapper.createIdx4App();
			jusoUpdateMapper.createIdx5App();
			jusoUpdateMapper.createIdx6App();
			
			return true;

		} catch (IOException e) {
			log.info(e.getMessage());
			return false;
		} catch (Exception e) {
			log.info(e.getMessage());
			return false;
		}
	}
	
	@Override
	public List<Map<String, String>> jusoGetJusoUpdateResults() throws Exception {
		return jusoUpdateMapper.jusoGetJusoUpdateResults();
	}
	
	@Override
	public void jusoSetUpdateCount(String currentDate) throws Exception {
		jusoUpdateMapper.jusoSetUpdateCount(currentDate);
	}

	@Override
	public void jusoProcessSetEmdTruncate() throws Exception {
		jusoUpdateMapper.jusoProcessSetEmdTruncate();
	}
	
	@Override
	public void jusoProcessSetEmd() throws Exception {
		jusoUpdateMapper.jusoProcessSetEmdTruncate();
		jusoUpdateMapper.jusoProcessSetEmd();
	}
	
	@Override
	public void jusoProcessSetEmdRegional(String region) throws Exception {
		jusoUpdateMapper.jusoProcessSetEmdRegional(region);
	}
	
	@Override
	public void jusoUpdateComment() throws Exception {
		//jusoUpdateMapper.updateAttachment();

		jusoUpdateMapper.commentSAD600_0();
		jusoUpdateMapper.commentSAD600_1();
		jusoUpdateMapper.commentSAD600_2();
		jusoUpdateMapper.commentSAD600_3();
		jusoUpdateMapper.commentSAD600_4();
		jusoUpdateMapper.commentSAD600_5();
		jusoUpdateMapper.commentSAD600_6();
		jusoUpdateMapper.commentSAD600_7();
		jusoUpdateMapper.commentSAD600_8();
		jusoUpdateMapper.commentSAD600_9();
		jusoUpdateMapper.commentSAD600_10();
		jusoUpdateMapper.commentSAD600_11();
		jusoUpdateMapper.commentSAD600_12();
		jusoUpdateMapper.commentSAD600_13();
		jusoUpdateMapper.commentSAD600_14();
		jusoUpdateMapper.commentSAD600_15();
		jusoUpdateMapper.commentSAD600_16();
		jusoUpdateMapper.commentSAD600_17();
		jusoUpdateMapper.commentSAD600_18();
		jusoUpdateMapper.commentSAD600_19();

		jusoUpdateMapper.commentSAD200_0();
		jusoUpdateMapper.commentSAD200_1();
		jusoUpdateMapper.commentSAD200_2();
		jusoUpdateMapper.commentSAD200_3();
		jusoUpdateMapper.commentSAD200_4();
		jusoUpdateMapper.commentSAD200_5();
		jusoUpdateMapper.commentSAD200_6();
		jusoUpdateMapper.commentSAD200_7();
		jusoUpdateMapper.commentSAD200_8();
		jusoUpdateMapper.commentSAD200_9();
		jusoUpdateMapper.commentSAD200_10();
		jusoUpdateMapper.commentSAD200_11();
		jusoUpdateMapper.commentSAD200_12();
		jusoUpdateMapper.commentSAD200_13();
		jusoUpdateMapper.commentSAD200_14();
		jusoUpdateMapper.commentSAD200_15();
		jusoUpdateMapper.commentSAD200_16();
		jusoUpdateMapper.commentSAD200_17();
		jusoUpdateMapper.commentSAD200_18();
		jusoUpdateMapper.commentSAD200_19();

		jusoUpdateMapper.commentSAD500_0();
		jusoUpdateMapper.commentSAD500_1();
		jusoUpdateMapper.commentSAD500_2();
		jusoUpdateMapper.commentSAD500_3();
		jusoUpdateMapper.commentSAD500_4();
		jusoUpdateMapper.commentSAD500_5();
		jusoUpdateMapper.commentSAD500_6();
		jusoUpdateMapper.commentSAD500_7();
		jusoUpdateMapper.commentSAD500_8();
		jusoUpdateMapper.commentSAD500_9();
		jusoUpdateMapper.commentSAD500_10();
		jusoUpdateMapper.commentSAD500_11();
		jusoUpdateMapper.commentSAD500_12();
		jusoUpdateMapper.commentSAD500_13();
		jusoUpdateMapper.commentSAD500_14();
		jusoUpdateMapper.commentSAD500_15();
		jusoUpdateMapper.commentSAD500_16();
		jusoUpdateMapper.commentSAD500_17();
		jusoUpdateMapper.commentSAD500_18();
		jusoUpdateMapper.commentSAD500_19();
		jusoUpdateMapper.commentSAD500_20();
		jusoUpdateMapper.commentSAD500_21();
		jusoUpdateMapper.commentSAD500_22();
		jusoUpdateMapper.commentSAD500_23();
		jusoUpdateMapper.commentSAD500_24();
		jusoUpdateMapper.commentSAD500_25();
		jusoUpdateMapper.commentSAD500_26();
		jusoUpdateMapper.commentSAD500_27();
		jusoUpdateMapper.commentSAD500_28();
		jusoUpdateMapper.commentSAD500_29();
		jusoUpdateMapper.commentSAD500_30();
		jusoUpdateMapper.commentSAD500_31();
		jusoUpdateMapper.commentSAD500_32();
		jusoUpdateMapper.commentSAD500_33();
		jusoUpdateMapper.commentSAD500_34();
		jusoUpdateMapper.commentSAD500_35();
		jusoUpdateMapper.commentSAD500_36();
		
		jusoUpdateMapper.commentSAD100_0();
		jusoUpdateMapper.commentSAD100_1();
		jusoUpdateMapper.commentSAD100_2();
		jusoUpdateMapper.commentSAD100_3();
		jusoUpdateMapper.commentSAD100_4();
		jusoUpdateMapper.commentSAD100_5();
		jusoUpdateMapper.commentSAD100_6();
		jusoUpdateMapper.commentSAD100_7();
		jusoUpdateMapper.commentSAD100_8();
		jusoUpdateMapper.commentSAD100_9();
		jusoUpdateMapper.commentSAD100_10();
		jusoUpdateMapper.commentSAD100_11();
		jusoUpdateMapper.commentSAD100_12();
		jusoUpdateMapper.commentSAD100_13();
		jusoUpdateMapper.commentSAD100_14();
		jusoUpdateMapper.commentSAD100_15();
		jusoUpdateMapper.commentSAD100_16();
		jusoUpdateMapper.commentSAD100_17();
		jusoUpdateMapper.commentSAD100_18();
		jusoUpdateMapper.commentSAD100_19();
		jusoUpdateMapper.commentSAD100_20();
		jusoUpdateMapper.commentSAD100_21();
		jusoUpdateMapper.commentSAD100_22();
		jusoUpdateMapper.commentSAD100_23();
		jusoUpdateMapper.commentSAD100_24();
		jusoUpdateMapper.commentSAD100_25();
		jusoUpdateMapper.commentSAD100_26();
		jusoUpdateMapper.commentSAD100_27();
		jusoUpdateMapper.commentSAD100_28();
		jusoUpdateMapper.commentSAD100_29();
		jusoUpdateMapper.commentSAD100_30();
		jusoUpdateMapper.commentSAD100_31();
		jusoUpdateMapper.commentSAD100_32();
		jusoUpdateMapper.commentSAD100_33();
		jusoUpdateMapper.commentSAD100_34();
		jusoUpdateMapper.commentSAD100_35();
		jusoUpdateMapper.commentSAD100_36();
		
	}
	
}
