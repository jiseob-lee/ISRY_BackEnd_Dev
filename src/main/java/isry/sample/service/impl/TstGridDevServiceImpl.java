package isry.sample.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Service;
import com.cleopatra.protocol.builder.TSVResponseBuilder;
import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.DataResponse;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;
import isry.base.IsryBaseServiceImpl;
import isry.sample.mapper.TstGridDevMapper;
import isry.sample.service.TstGridDevService;

/**
 * @Class Name : TstGridDevServiceImpl.java
 * @Description : 응용 샘플(CMN_TMP_REG) Business Implement Class
 * @Modification Information
 * @ @ 수정일 수정자 수정내용 @ --------- --------- -------------------------------
 *
 * @author tomatosystem
 * @since
 * @version
 * @see
 *
 */

@Service
public class TstGridDevServiceImpl extends IsryBaseServiceImpl implements TstGridDevService {
	
	@Resource(name = "tstGridDevMapper")
	private TstGridDevMapper tstGridDevMapper;

	@Override
	public List<Map<String, Object>> selectCmnTmpRegList(Map<String, String> mapParam) throws Exception {

		// TODO Auto-generated method stub
		return tstGridDevMapper.selectCmnTmpRegList(mapParam);

	}

	@Override
	public void selectCmnTmpRegRowHandler(Map<String, String> mapParam, HttpServletResponse response) throws Exception {
		final DataResponse dataResponse = DataResponse.getInstance(TSVResponseBuilder.CONTENT_TYPE, response);

		tstGridDevMapper.selectCmnTmpRegList(mapParam, new ResultHandler<HashMap<String, Object>>() {

			public void handleResult(ResultContext<? extends HashMap<String, Object>> resultContext) {

				HashMap<String, Object> rowData = resultContext.getResultObject();

				try {
					dataResponse.send(rowData);
				} catch (IOException e) {
					throw new AppWorksException("error");
				}
				dataResponse.flush();
			}
		});
	}

	@Override
	public void saveCmnTmpReg(DataRequest dataRequest) {
		
		ParameterGroup dsMessage = dataRequest.getParameterGroup("dsCmnTmpReg");
		Iterator<ParameterRow> insertedRows = dsMessage.getInsertedRows();
		Iterator<ParameterRow> updatedRows = dsMessage.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = dsMessage.getDeletedRows();

		while (deletedRows.hasNext()) {
			tstGridDevMapper.deleteCmnTmpReg(deletedRows.next().toMap());
		}

		while (insertedRows.hasNext()) {
			tstGridDevMapper.insertCmnTmpReg(insertedRows.next().toMap());
		}

		while (updatedRows.hasNext()) {
			tstGridDevMapper.updateCmnTmpReg(updatedRows.next().toMap());
		}
		
	}

	@Override
	public void saveCmnTmpRegTab(DataRequest dataRequest) {
		
		ParameterGroup dsMessage = dataRequest.getParameterGroup("dsCmnTmpReg2");
		
		Iterator<ParameterRow> insertedRows = dsMessage.getInsertedRows();
		
		Iterator<ParameterRow> updatedRows = dsMessage.getUpdatedRows();
		
		Iterator<ParameterRow> deletedRows = dsMessage.getDeletedRows();

		while (deletedRows.hasNext()) {
			tstGridDevMapper.deleteCmnTmpReg(deletedRows.next().toMap());
		}

		while (insertedRows.hasNext()) {
			tstGridDevMapper.insertCmnTmpReg(insertedRows.next().toMap());
		}

		while (updatedRows.hasNext()) {
			tstGridDevMapper.updateCmnTmpReg(updatedRows.next().toMap());
		}
	}

	@Override
	public void saveCmnTmpRegWithFile(DataRequest dataRequest) {
		ParameterGroup dsMessage = dataRequest.getParameterGroup("dsCmnTmpReg");
		Iterator<ParameterRow> insertedRows = dsMessage.getInsertedRows();
		Iterator<ParameterRow> updatedRows = dsMessage.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = dsMessage.getDeletedRows();

		Map<String, String> mapFile = new HashMap<String, String>();
		Map<String, String> param;
		while (deletedRows.hasNext()) {

			param = deletedRows.next().toMap();
			tstGridDevMapper.deleteCmnTmpReg(param);

			// 첨부파일 삭제
			mapFile.clear();
			mapFile.put("FILE_SERIAL_NO", param.get("FILE_SERIAL_NO"));
//			cmnFileService.deleteCmnFileByAttcFileNo(mapFile);

		}

		while (insertedRows.hasNext()) {
			param = deletedRows.next().toMap();
			tstGridDevMapper.insertCmnTmpReg(param);
//			cmnFileService.commitCmnFile(param.get("FILE_SERIAL_NO"), param.get("ORI_FILE_SERIAL_NO"));
		}

		while (updatedRows.hasNext()) {
			param = updatedRows.next().toMap();
			tstGridDevMapper.updateCmnTmpReg(param);
//			cmnFileService.commitCmnFile(param.get("FILE_SERIAL_NO"), param.get("ORI_FILE_SERIAL_NO"));
		}
	}
}
