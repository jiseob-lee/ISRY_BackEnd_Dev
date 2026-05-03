/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package egovframework.com.cmm;

import egovframework.rte.fdl.cmmn.exception.handler.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @Class  Name   : EgovComExcepHndlr.java
 * @Description   :
 *  
 * @ 클래스 작성에 대한 상세 내용을 이곳에 작성 바랍니다.  
 *  
 * @ 수정일           수정자              수정내용
 * @ -----------   ------------------ -----------------
 * @ 2021.11.03       Song.Young.Il           최초생성                      
 * 
 * @team/@author  : 인프라팀 Song.Young.Il
 * @since         : 2021. 11. 3. 
 * @version       : 1.0
 * @see           : 2021. 11. 3. 
 * 
 *
 */
public class EgovComExcepHndlr implements ExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovComExcepHndlr.class);

	/**
	* @param ex
	* @param packageName
	* @see 개발프레임웍크 실행환경 개발팀
	*/
	@Override
	public void occur(Exception ex, String packageName) {
		LOGGER.debug(" EgovServiceExceptionHandler run...............");
	}
}
