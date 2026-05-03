package com.tomatosystem.exbuilder6.core.aspect;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tomatosystem.exbuilder6.core.constants.PageConst;
import com.tomatosystem.exbuilder6.core.paging.CommonPagination;

/**
 * 
 * MapperBeforeTransferAspect.java
 * 
 * @Description Mapper(SqlMapperImpl) AOP - Mapper 실행 전처리
 *                    필요에 따라 포인트컷 arg에 시스템 로그 및 공통 및 사용자 정보 추가
 * @author You Minsang
 * @since 2022. 12. 13.
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *    수정일                           수정자                         수정내용
 *    -------       --------      ---------------------------
 *   2022. 12. 13.  You Minsang   최초 생성
 *
 * </pre>
 */
public class MapperBeforeTransferAspect {
	
	public void before(JoinPoint joinPoint) throws Exception {
		/*
		Object[] args = joinPoint.getArgs(); 
		String methodName = joinPoint.getSignature().getName();

		if (RequestContextHolder.getRequestAttributes() == null || 
				!(RequestContextHolder.currentRequestAttributes() instanceof ServletRequestAttributes)) {
			return;
		}
				
		for (Object arg : args) {
			
			if (arg == null) {
				continue;
			}

			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
						
			//조회일 경우 페이징 처리	
			if(methodName.startsWith("select")) {				
				if (arg instanceof java.util.Map) { 
					Map param = (Map) arg; 					
					if(request.getAttribute(PageConst.PAGINATION_INFO) != null) {
						CommonPagination pg = (CommonPagination)request.getAttribute(PageConst.PAGINATION_INFO);
						param.putAll(pg.getMapInitPage());
					}
				}
			}
		}
		*/
	}
}
