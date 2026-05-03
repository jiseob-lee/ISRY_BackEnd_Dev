/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.quartz;

import java.io.IOException;
import java.sql.SQLException;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import isry.itgcms.sysmgmt.quartz.service.SystemLogService;

/**
 * @파일명        : SystemLogJob.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2022. 11. 11. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2022. 11. 11.
 * @수정내용      : 
 * -                
 * -                
 */
public class SystemLogJob extends QuartzJobBean {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private ApplicationContext appCtx;
	
	@Override
	public void executeInternal(JobExecutionContext context) throws JobExecutionException {

		log.info("#### SERVER : " + System.getProperty("SERVER"));
		
		// 개발 서버와 행정망 운영 WAS 1 에서만 실행함.
		if (!"devserver".equals(System.getProperty("SERVER")) 
				&& !"grybwas11".equals(System.getProperty("SERVER"))) {
			return;
		}
		
		log.info("#### execute SystemLogJob.");
		
		//appCtx = (ApplicationContext)context.getJobDetail().getJobDataMap().get("applicationContext");
		try {
			appCtx = (ApplicationContext)context.getScheduler().getContext().get("applicationContext");
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

		SystemLogService systemLogService = (SystemLogService)appCtx.getBean(SystemLogService.class);
		
		//ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
		//SystemLogService systemLogService = (SystemLogService)applicationContext.getBean("systemLogService");

		
		try {
			systemLogService.clearSystemLogOlderThan1Months();
		} catch (IOException e) {
			log.info("#### " + e.getMessage());
			//e.printStackTrace();
		} catch (SQLException e) {
			log.info("#### " + e.getMessage());
			//e.printStackTrace();
		} catch (Exception e) {
			log.info("#### " + e.getMessage());
			//e.printStackTrace();
		}
		
		log.info("#### done SystemLogJob.");
	}
	
}
