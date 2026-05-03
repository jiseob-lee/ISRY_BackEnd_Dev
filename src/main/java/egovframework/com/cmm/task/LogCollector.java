package egovframework.com.cmm.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogCollector {
	
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	public void collect() {
		log.info("LogCollector 실행: " + new Date());
	}
}
