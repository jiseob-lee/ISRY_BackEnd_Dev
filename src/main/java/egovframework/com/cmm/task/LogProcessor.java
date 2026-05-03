package egovframework.com.cmm.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

public class LogProcessor {
	
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	//@Scheduled(fixedRate = 10000)
	//public void handle() {
		//log.info("LogProcessor 실행: " + new Date());
	//}
}
