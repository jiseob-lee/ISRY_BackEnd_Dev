package isry.itgcms.sysmgmt.quartz;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.time.format.DateTimeFormatter;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import egovframework.com.cmm.service.EgovProperties;

import org.springframework.scheduling.quartz.QuartzJobBean;

import isry.itgcms.sysmgmt.jusoupdate.service.JusoUpdateService;
import isry.itgcms.util.DirectoryDelete;
import isry.itgcms.util.IP;

public class JusoUpdateJob extends QuartzJobBean {

	//@Resource(name = "jusoUpdateMapper")
	//@Autowired
	//private JusoUpdateService jusoUpdateService;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	//private ApplicationContext appCtx;
	private ApplicationContext applicationContext;
	
	private static int currentTimeInt = 0;
	private static int runCount = 0;
	
	private LocalDate startDate = null;
	
	public void setApplicationContext(ApplicationContext appContext) {
		applicationContext = appContext;
	}
	
	@Override
	public void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		//appCtx = (ApplicationContext)context.getJobDetail().getJobDataMap().get("applicationContext");
		
		//try {
			//appCtx = (ApplicationContext)context.getScheduler().getContext().get("applicationContext");
		//} catch (SchedulerException e) {
			//e.printStackTrace();
		//}

		String hostName = IP.getHostName();
		
		log.info("hostName : " + hostName);
		log.info("obj_name : " + System.getProperty("obj_name"));
		log.info("host Ip : " + IP.getServerIP());
		log.info("Container.Name : " + System.getProperty("Container.Name"));
		log.info("SERVER : " + System.getProperty("SERVER"));
		
		
		//if (!"devserver".equals(hostName) || "devserver".equals(hostName)) {
			//return;
		//}
		
		//if ("localhost".equals(hostName)) {
			//return;
		//}
		
		if (!"devserver".equals(System.getProperty("SERVER")) 
				&& !"grybwas11".equals(System.getProperty("SERVER"))) {
			// 개발 서버 JBOSS 와 행정망 운영 WAS 1 에서만 실행함.
			return;
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHH", Locale.KOREAN);
		Date date = new Date();
		String currentTime = formatter.format(date);
		
		if (currentTimeInt < Integer.parseInt(currentTime)) {
			currentTimeInt = Integer.parseInt(currentTime);
			runCount = 1;
		}
		
		//if (currentTimeInt != Integer.parseInt(currentTime)) {
			//return;
		//}

		if (runCount > 1) {
			return;
		}
		
		//currentTimeInt++;
		runCount++;
		
		log.info("JusoUpdateJob is runing");
		
		
		//JusoUpdateService jusoUpdateService = (JusoUpdateService)appCtx.getBean(JusoUpdateService.class);
		JusoUpdateService jusoUpdateService = (JusoUpdateService)applicationContext.getBean("jusoUpdateService");
		
		//ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
		//JusoUpdateService jusoUpdateService = (JusoUpdateService)applicationContext.getBean("jusoUpdateService");
		

		//EgovProperties prop = (EgovProperties)appCtx.getBean("prop");
		
		String path = EgovProperties.getProperty("globals", "juso.update.folder");
		
		log.info("path : " + path);
		
		List<Map<String, String>> updateList = null;
		try {
			updateList = jusoUpdateService.jusoGetJusoUpdateResults();
		} catch (IOException e1) {
			log.info(e1.getMessage());
		} catch (Exception e1) {
			log.info(e1.getMessage());
		}
		
        LocalDate startDate1 = LocalDate.parse("2022-11-01");
        LocalDate endDate = LocalDate.now().minusDays(1);
        LocalDate startDate2 = LocalDate.now().minusDays(30);
        if (startDate1.isBefore(startDate2)) {
        	startDate = LocalDate.now().minusDays(30);
        } else {
        	startDate = LocalDate.parse("2022-11-01");
        }
        
        //startDate = LocalDate.parse("2022-09-01");
        
        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        List<String> dateList = IntStream.iterate(0, i -> i + 1)
          .limit(numOfDaysBetween)
          .mapToObj(i -> startDate.plusDays(i).format(DateTimeFormatter.ofPattern("yyyyMMdd")))
          .collect(Collectors.toList());
        //for (String lDate : dateList) {
        	//log.info(lDate);
        //}
        
        log.info("dateList.size : " + dateList.size());
        
        String fileSeparator = FileSystems.getDefault().getSeparator();
        
		if (updateList != null) {
			for (int i=0; i < updateList.size(); i++) {
				
				dateList.remove(updateList.get(i).get("UPDATA_YMD"));
				
				try {
					
					File dir = new File(path + fileSeparator + updateList.get(i).get("UPDATA_YMD").substring(2));
					if (dir.exists()) {
						DirectoryDelete.deleteDirectoryJava8(path + fileSeparator 
								+ updateList.get(i).get("UPDATA_YMD").substring(2));
					}
					
					dir = new File(path + fileSeparator + updateList.get(i).get("UPDATA_YMD"));
					if (dir.exists()) {
						DirectoryDelete.deleteDirectoryJava8(path + fileSeparator 
								+ updateList.get(i).get("UPDATA_YMD"));
					}
					
				} catch (IOException e) {
					log.info(e.getMessage());
				}
			}
		}
		
		String dateFrom = "";
		if (dateList != null && dateList.size() > 0) {
			dateFrom = dateList.get(0);
		}
		String dateTo = "";
		
		if (dateList != null) {
			for (int i=0; i < dateList.size() && i < 10; i++) {
				//if (Integer.parseInt(dateFrom) + i == Integer.parseInt(dateList.get(i))) {
				log.info(i + " : " + dateList.get(i)
					+ ", " + LocalDate.parse(dateFrom, DateTimeFormatter.ofPattern("yyyyMMdd")).plusDays(i).toString()
					+ ", " + LocalDate.parse(dateList.get(i), DateTimeFormatter.ofPattern("yyyyMMdd")).toString());
				if (LocalDate.parse(dateFrom, DateTimeFormatter.ofPattern("yyyyMMdd")).plusDays(i).toString().equals(
						LocalDate.parse(dateList.get(i), DateTimeFormatter.ofPattern("yyyyMMdd")).toString())) {
					dateTo = dateList.get(i);
					try {
						jusoUpdateService.jusoSetUpdateCount(dateList.get(i));
					} catch (IOException e) {
						//e.printStackTrace();
						log.info(e.getMessage());
					} catch (Exception e) {
						//e.printStackTrace();
						log.info(e.getMessage());
					}
					continue;
				} else {
					break;
				}
			}
		}
		
		//dateFrom = "20211210";
		//dateTo = "20211218";
		
		String param = "dateFrom=" + dateFrom + "&dateTo=" + dateTo;
		
		log.info("param : " + param);
		
		if ("".equals(dateTo)) {
			return;
		}
		

		Instant start = Instant.now();
		
		//boolean result0 = false;
		//boolean result0_1 = false;
		//boolean result0_2 = false;
		//boolean result0_3 = false;
		//boolean result0_4 = false;
		boolean result1 = false;
		boolean result2 = false;
		boolean result3 = false;
		boolean result4 = false;
		boolean result5 = false;
		boolean result6 = false;

		//try {
			//result0_1 = jusoUpdateService.jusoUpdateInit1();
			//log.info("#### result0_1 : " + result0_1);
			//result0_2 = jusoUpdateService.jusoUpdateInit2();
			//log.info("#### result0_2 : " + result0_2);
			//result0_3 = jusoUpdateService.jusoUpdateInit3();
			//log.info("#### result0_3 : " + result0_3);
			//result0_4 = jusoUpdateService.jusoUpdateInit4();
			//log.info("#### result0_4 : " + result0_4);
		//} catch (IOException e) {
			//log.info("#### jusoUpdateInit : " + e.getMessage());
		//} catch (Exception e) {
			//log.info("#### jusoUpdateInit : " + e.getMessage());
		//}
		
		
		//if (result0_1 && result0_2 && result0_3 && result0_4) {
			try {
				result1 = jusoUpdateService.jusoUpdate(dateFrom, dateTo);
				//result0 = true;
			} catch (IOException e) {
				log.info("#### jusoUpdate : " + e.getMessage());
			} catch (Exception e) {
				log.info("#### jusoUpdate : " + e.getMessage());
			}
		//}
		//log.info("#### result0 : " + result0);
		log.info("#### result1 : " + result1);
		
		if (result1) {
			//try {
				//result2 = jusoUpdateService.processAddrData1();
				result2 = true;
			//} catch (Exception e) {
				//log.info("#### processAddrData1 : " + e.getMessage());
			//}
		}
		log.info("#### result2 : " + result2);
		
		if (result1 && result2) {
			//try {
				//jusoUpdateService.dropIndex();
			//} catch (Exception e) {
				//e.printStackTrace();
			//}
			//try {
				result3 = true; //jusoUpdateService.jusoProcessAddrData3();
			//} catch (IOException e) {
				//log.info("#### jusoProcessAddrData3 : " + e.getMessage());
			//} catch (Exception e) {
				//log.info("#### jusoProcessAddrData3 : " + e.getMessage());
			//}
		}
		log.info("#### result3 : " + result3);
		
		if (result1 && result2 && result3) {
			//try {
				//result4 = jusoUpdateService.createIndex();
				result4 = true;
			//} catch (Exception e) {
				//log.info("#### createIndex : " + e.getMessage());
			//}
		}
		log.info("#### result4 : " + result4);
		
		if (result1 && result2 && result3 && result4) {
			try {
				result5 = jusoUpdateService.jusoProcessAddrData4(dateFrom, dateTo);
			} catch (IOException e) {
				log.info("#### jusoProcessAddrData4 : " + e.getMessage());
			} catch (Exception e) {
				log.info("#### jusoProcessAddrData4 : " + e.getMessage());
			}
		}
		log.info("#### result5 : " + result5);
		
		if (result1 && result2 && result3 && result4 && result5) {
			//try {
				result6 = true; //jusoUpdateService.jusoProcessAddrData2();
			//} catch (IOException e) {
				//log.info("#### jusoProcessAddrData2 : " + e.getMessage());
			//} catch (Exception e) {
				//log.info("#### jusoProcessAddrData2 : " + e.getMessage());
			//}
		}
		log.info("#### result6 : " + result6);
		
		if (result1 && result2 && result3 && result4 && result5 && result6) {
			//try {
				//jusoUpdateService.jusoProcessSetEmd();
			//} catch (IOException e) {
				//log.info("#### jusoProcessSetEmd : " + e.getMessage());
			//} catch (Exception e) {
				//log.info("#### jusoProcessSetEmd : " + e.getMessage());
			//}
			try {
				jusoUpdateService.jusoProcessSetEmdTruncate();
			} catch (IOException e) {
				log.info("#### jusoProcessSetEmdTruncate : " + e.getMessage());
			} catch (Exception e) {
				log.info("#### jusoProcessSetEmdTruncate : " + e.getMessage());
			}
			try {
				jusoUpdateService.jusoProcessSetEmdRegional("울산광역시");
			} catch (IOException e) {
				log.info("#### jusoProcessSetEmd 울산광역시 : " + e.getMessage());
			} catch (Exception e) {
				log.info("#### jusoProcessSetEmd 울산광역시 : " + e.getMessage());
			}
			try {
				jusoUpdateService.jusoProcessSetEmdRegional("대전광역시");
			} catch (IOException e) {
				log.info("#### jusoProcessSetEmd 대전광역시 : " + e.getMessage());
			} catch (Exception e) {
				log.info("#### jusoProcessSetEmd 대전광역시 : " + e.getMessage());
			}
			try {
				jusoUpdateService.jusoProcessSetEmdRegional("광주광역시");
			} catch (IOException e) {
				log.info("#### jusoProcessSetEmd 광주광역시 : " + e.getMessage());
			} catch (Exception e) {
				log.info("#### jusoProcessSetEmd 광주광역시 : " + e.getMessage());
			}
			try {
				jusoUpdateService.jusoProcessSetEmdRegional("경상북도");
			} catch (IOException e) {
				log.info("#### jusoProcessSetEmd 경상북도 : " + e.getMessage());
			} catch (Exception e) {
				log.info("#### jusoProcessSetEmd 경상북도 : " + e.getMessage());
			}
			try {
				jusoUpdateService.jusoProcessSetEmdRegional("충청북도");
			} catch (IOException e) {
				log.info("#### jusoProcessSetEmd 충청북도 : " + e.getMessage());
			} catch (Exception e) {
				log.info("#### jusoProcessSetEmd 충청북도 : " + e.getMessage());
			}
			try {
				jusoUpdateService.jusoProcessSetEmdRegional("경기도");
			} catch (IOException e) {
				log.info("#### jusoProcessSetEmd 경기도 : " + e.getMessage());
			} catch (Exception e) {
				log.info("#### jusoProcessSetEmd 경기도 : " + e.getMessage());
			}
			try {
				jusoUpdateService.jusoProcessSetEmdRegional("인천광역시");
			} catch (IOException e) {
				log.info("#### jusoProcessSetEmd 인천광역시 : " + e.getMessage());
			} catch (Exception e) {
				log.info("#### jusoProcessSetEmd 인천광역시 : " + e.getMessage());
			}
			try {
				jusoUpdateService.jusoProcessSetEmdRegional("부산광역시");
			} catch (IOException e) {
				log.info("#### jusoProcessSetEmd 부산광역시 : " + e.getMessage());
			} catch (Exception e) {
				log.info("#### jusoProcessSetEmd 부산광역시 : " + e.getMessage());
			}
			try {
				jusoUpdateService.jusoProcessSetEmdRegional("경상남도");
			} catch (IOException e) {
				log.info("#### jusoProcessSetEmd 경상남도 : " + e.getMessage());
			} catch (Exception e) {
				log.info("#### jusoProcessSetEmd 경상남도 : " + e.getMessage());
			}
			try {
				jusoUpdateService.jusoProcessSetEmdRegional("전라남도");
			} catch (IOException e) {
				log.info("#### jusoProcessSetEmd 전라남도 : " + e.getMessage());
			} catch (Exception e) {
				log.info("#### jusoProcessSetEmd 전라남도 : " + e.getMessage());
			}
			try {
				jusoUpdateService.jusoProcessSetEmdRegional("강원도");
			} catch (IOException e) {
				log.info("#### jusoProcessSetEmd 강원도 : " + e.getMessage());
			} catch (Exception e) {
				log.info("#### jusoProcessSetEmd 강원도 : " + e.getMessage());
			}
			try {
				jusoUpdateService.jusoProcessSetEmdRegional("제주특별자치도");
			} catch (IOException e) {
				log.info("#### jusoProcessSetEmd 제주특별자치도 : " + e.getMessage());
			} catch (Exception e) {
				log.info("#### jusoProcessSetEmd 제주특별자치도 : " + e.getMessage());
			}
			try {
				jusoUpdateService.jusoProcessSetEmdRegional("세종특별자치시");
			} catch (IOException e) {
				log.info("#### jusoProcessSetEmd 세종특별자치시 : " + e.getMessage());
			} catch (Exception e) {
				log.info("#### jusoProcessSetEmd 세종특별자치시 : " + e.getMessage());
			}
			try {
				jusoUpdateService.jusoProcessSetEmdRegional("충청남도");
			} catch (IOException e) {
				log.info("#### jusoProcessSetEmd 충청남도 : " + e.getMessage());
			} catch (Exception e) {
				log.info("#### jusoProcessSetEmd 충청남도 : " + e.getMessage());
			}
			try {
				jusoUpdateService.jusoProcessSetEmdRegional("대구광역시");
			} catch (IOException e) {
				log.info("#### jusoProcessSetEmd 대구광역시 : " + e.getMessage());
			} catch (Exception e) {
				log.info("#### jusoProcessSetEmd 대구광역시 : " + e.getMessage());
			}
			try {
				jusoUpdateService.jusoProcessSetEmdRegional("전라북도");
			} catch (IOException e) {
				log.info("#### jusoProcessSetEmd 전라북도 : " + e.getMessage());
			} catch (Exception e) {
				log.info("#### jusoProcessSetEmd 전라북도 : " + e.getMessage());
			}
			try {
				jusoUpdateService.jusoProcessSetEmdRegional("서울특별시");
			} catch (IOException e) {
				log.info("#### jusoProcessSetEmd 서울특별시 : " + e.getMessage());
			} catch (Exception e) {
				log.info("#### jusoProcessSetEmd 서울특별시 : " + e.getMessage());
			}
		}

		//if (result0) {
			//try {
				//jusoUpdateService.jusoUpdateComment();
			//} catch (IOException e3) {
				//log.info("#### " + e3.getMessage());
			//} catch (Exception e2) {
				//log.info("#### " + e2.getMessage());
			//}
		//}
		
		Instant finish = Instant.now();
		
		long timeElapsed = Duration.between(start, finish).toMillis();
		log.info("timeElapsed : " + timeElapsed);
		
		
		//String totalUrl = "http://localhost:8080/isry/itgcm/sysmgmt/jusoupdate/jusoUpdate.do?" + param;
		
		// http 통신을 하기위한 객체 선언 실시
		//URL url = null;
		//HttpURLConnection conn = null;
	    
		// http 통신 요청 후 응답 받은 데이터를 담기 위한 변수
		//String responseData = "";
		//BufferedReader br = null;
		//StringBuffer sb = null;
	    
		// 메소드 호출 결과값을 반환하기 위한 변수
		//String returnData = "";
	 
		//try {
			//url = new URL(totalUrl);
			//conn = (HttpURLConnection) url.openConnection();
	        
			// http 요청에 필요한 타입 정의 실시
			//conn.setRequestProperty("Accept", "application/json");
			//conn.setRequestMethod("GET");
	        
			// http 요청 실시
			//conn.connect();
			//log.info("http 요청 방식 : "+"GET");
			//log.info("http 요청 타입 : "+"application/json");
			//log.info("http 요청 주소 : "+UrlData);
			//log.info("http 요청 데이터 : "+ParamData);
			//log.info("");
	        
			//http 요청 후 응답 받은 데이터를 버퍼에 쌓는다
			//br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			//sb = new StringBuffer();
			//while ((responseData = br.readLine()) != null) {
				//sb.append(responseData); //StringBuffer에 응답받은 데이터 순차적으로 저장 실시
			//}
	 
			//메소드 호출 완료 시 반환하는 변수에 버퍼 데이터 삽입 실시
			//returnData = sb.toString();
			
			// http 요청 응답 코드 확인 실시
			//String responseCode = String.valueOf(conn.getResponseCode());
			//log.info("http 응답 코드 : "+responseCode);
			//log.info("http 응답 데이터 : "+returnData);

		//} catch (IOException e) {
			//e.printStackTrace();
		//} finally {
			//http 요청 및 응답 완료 후 BufferedReader를 닫아줍니다
			//try {
				//if (conn != null) {
					//conn.disconnect();
				//}
			//} catch (IOException e) {
				//e.printStackTrace();
			//}
		//}
	}

}
