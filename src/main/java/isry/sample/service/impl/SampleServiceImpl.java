/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 *****************************************************************************************/

package isry.sample.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;
import isry.base.IsryBaseServiceImpl;
import isry.sample.mapper.SampleMapper;
import isry.sample.service.SampleService;
import isry.sample.service.Sample2Service;

/**
 * 
 * @파일명 : SampleServiceImpl.java
 * @프로그램 설명 : - Sample서비스를 위한 Service Impl 입니다. 
 * @작성자 : Song.Young.Il
 * @작성일 : 2021. 11. 11.
 * @수정자 : Song.Young.Il
 * @수정일 : 2021. 11. 11.
 * @수정내용 : - -
 */
@Service("sampleService")
public class SampleServiceImpl extends IsryBaseServiceImpl implements SampleService {

	@Resource(name = "sampleMapper")
	private SampleMapper sampleMapper;

	@Resource(name = "sample2Service")
	private Sample2Service sample2Service;

	/**
	 * 
	 * @Method명 : selectSample
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자 : Song.Young.Il
	 * @작성일 : 2021. 11. 11.
	 * @Method설명 : 샘플 리스트를 조회 하면 메세드
	 */
	@Override
	public List<Map<String, Object>> selectSample(Map<String, String> mapParam) throws Exception {
		return sampleMapper.selectSample(mapParam);
	}
	
	
	
	/**
	 * 
	 * @Method명 : selectSample
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자 : Song.Young.Il
	 * @작성일 : 2021. 11. 11.
	 * @Method설명 : 샘플 리스트 의 입력,수정,삭제 항목을 전체적으로 저장 처리 한다. 
	 */
	@Override
	public void saveSample(DataRequest dataRequest) {

		/* dsCmnTmpReg로 넘겨진 exBuiler6의 데이터셋을  클라이언트로 받은 데이터셋 또는 데이터맵을 갖는 데이터그룹으로
		 * 값을 전달 하여  입력,수정 삭제를 동시에 처리합니다.  
		*/
		
		ParameterGroup dsMessage = dataRequest.getParameterGroup("dsCmnTmpReg");
		
		//화면에서 입력 처리된 레코드의 데이터 값을 일괄 전달 받는다.
		Iterator<ParameterRow> insertedRows = dsMessage.getInsertedRows();

		//화면에서 수정 처리된 레코드의 데이터 값을 일괄 전달 받는다.
		Iterator<ParameterRow> updatedRows = dsMessage.getUpdatedRows();

		//화면에서 삭제 처리된 레코드의 데이터 값을 일괄 전달 받는다.
		Iterator<ParameterRow> deletedRows = dsMessage.getDeletedRows();

		//삭제 처리 
		while (deletedRows.hasNext()) {

			sampleMapper.deleteSample(deletedRows.next().toMap());
		}
		
		// 입력 처리 
		while (insertedRows.hasNext()) {

			sampleMapper.insertSample(insertedRows.next().toMap());
		}

		// 수정 처리 
		while (updatedRows.hasNext()) {
			sampleMapper.updateSample(updatedRows.next().toMap());
		}
		
	}

	
	//#################################################################################################################
	
	@Override
	public void saveSampleTab(DataRequest dataRequest) {

		ParameterGroup dsMessage = dataRequest.getParameterGroup("dsCmnTmpReg2");

		Iterator<ParameterRow> insertedRows = dsMessage.getInsertedRows();

		Iterator<ParameterRow> updatedRows = dsMessage.getUpdatedRows();

		Iterator<ParameterRow> deletedRows = dsMessage.getDeletedRows();

		while (deletedRows.hasNext()) {
			sampleMapper.deleteSample(deletedRows.next().toMap());
		}

		while (insertedRows.hasNext()) {
			sampleMapper.deleteSample(insertedRows.next().toMap());
		}

		while (updatedRows.hasNext()) {
			sampleMapper.deleteSample(updatedRows.next().toMap());
		}

	}

	@Override
	public void saveSampleWithFile(DataRequest dataRequest) {
		
		ParameterGroup dsMessage = dataRequest.getParameterGroup("dsCmnTmpReg");
		
		Iterator<ParameterRow> insertedRows = dsMessage.getInsertedRows();
		
		Iterator<ParameterRow> updatedRows = dsMessage.getUpdatedRows();
		
		Iterator<ParameterRow> deletedRows = dsMessage.getDeletedRows();

		Map<String, String> mapFile = new HashMap<String, String>();
		
		Map<String, String> param;
		
		while (deletedRows.hasNext()) {

			param = deletedRows.next().toMap();
			sampleMapper.deleteSample(param);

			// 첨부파일 삭제
			mapFile.clear();
			mapFile.put("FILE_SERIAL_NO", param.get("FILE_SERIAL_NO"));
//			cmnFileService.deleteCmnFileByAttcFileNo(mapFile);

		}

		while (insertedRows.hasNext()) {
			param = deletedRows.next().toMap();
			sampleMapper.insertSample(param);
//			cmnFileService.commitCmnFile(param.get("FILE_SERIAL_NO"), param.get("ORI_FILE_SERIAL_NO"));
		}

		while (updatedRows.hasNext()) {
			param = updatedRows.next().toMap();
			sampleMapper.updateSample(param);
//			cmnFileService.commitCmnFile(param.get("FILE_SERIAL_NO"), param.get("ORI_FILE_SERIAL_NO"));
		}
	}
	
	@Override
	public void updateService() throws Exception {
		sample2Service.update2Service();
	}
}
