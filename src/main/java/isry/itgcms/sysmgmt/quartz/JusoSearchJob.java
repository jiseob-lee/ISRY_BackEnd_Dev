package isry.itgcms.sysmgmt.quartz;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
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
import isry.itgcms.sysmgmt.userjoin.service.SrchAddrService;
import isry.itgcms.util.DirectoryDelete;
import isry.itgcms.util.IP;

public class JusoSearchJob extends QuartzJobBean {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	//@Resource(name = "jusoUpdateMapper")
	//@Autowired
	//private JusoUpdateService jusoUpdateService;
	
	private ApplicationContext appCtx;
	
	private static int currentTimeInt = 0;
	private static int runCount = 0;
	
	@Override
	public void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		appCtx = (ApplicationContext)context.getJobDetail().getJobDataMap().get("applicationContext");

        callService();
	}
	
	
	private void callService() {

		String hostName = IP.getHostName();
		
		log.debug("hostName : " + hostName);
		log.debug("obj_name : " + System.getProperty("obj_name"));
		log.debug("host Ip : " + IP.getServerIP());
		
		//if (!"devserver".equals(hostName) || "devserver".equals(hostName)) {
			//return;
		//}
		
		if (!"localhost".equals(hostName)) {
			return;
		}
		
		if (!"ISRY-JBOSS-WAS".equals(System.getProperty("obj_name"))) {
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
		
		log.debug("JusoSearchJob is runing");
		
		SrchAddrService srchAddrService = (SrchAddrService)appCtx.getBean("srchAddrService");
		
		try {
			String[] search = {"가", "나", "다", "라", "마", "바", "사", "아", "자", "차", "카", "타", "파", "하"};
			for (int i=0; i < search.length; i++) {
				log.debug("#### search : " + search[i]);
				srchAddrService.selectAddr(search[i]);
			}
		} catch (IOException e1) {
			log.debug(e1.getMessage());
		} catch (Exception e1) {
			log.debug(e1.getMessage());
		}
		
	}

}
