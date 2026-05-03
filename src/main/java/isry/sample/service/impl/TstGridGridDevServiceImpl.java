package isry.sample.service.impl;

import java.util.Iterator;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import isry.sample.service.TstGridGridDevService;
import isry.sample.mapper.*;

/**
 * @Class Name : TstGridGridDevServiceImpl.java
 * @Description : 응용 샘플(CMN_TMP_REG_FEE) Business Implement Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 *
 * @author tomatosystem
 * @since 
 * @version 
 * @see
 *
 */
@Service
public class TstGridGridDevServiceImpl extends EgovAbstractServiceImpl implements TstGridGridDevService {
	
	 @Resource(name="tstGridDevMapper")
	private TstGridDevMapper tstGridDevMapper;

	@Override
	public List<Map<String, Object>> selectCmnTmpRegFeeList(Map<String, String> mapParam) throws Exception {
		
		// TODO Auto-generated method stub
		return tstGridDevMapper.selectCmnTmpRegFeeList(mapParam);
		
	}

	@Override
	public void saveCmnTmpReg(DataRequest dataRequest) {
		
		ParameterGroup dsMessage            = dataRequest.getParameterGroup("dsMst");
		
		Iterator<ParameterRow> insertedRows = dsMessage.getInsertedRows();
		
		Iterator<ParameterRow> updatedRows  = dsMessage.getUpdatedRows();
		
		Iterator<ParameterRow> deletedRows  = dsMessage.getDeletedRows();
		
		
		while(deletedRows.hasNext()){
			
			tstGridDevMapper.deleteCmnTmpReg(deletedRows.next().toMap());
			
		}
		
		while(insertedRows.hasNext()){
			
			tstGridDevMapper.insertCmnTmpReg(insertedRows.next().toMap());
			
		}
		
		while(updatedRows.hasNext()){
			
			tstGridDevMapper.updateCmnTmpReg(updatedRows.next().toMap());
			
		}
	}

	@Override
	public void saveCmnTmpRegFee(DataRequest dataRequest) {
		
		
		ParameterGroup dsMessage = dataRequest.getParameterGroup("dsDetail");
		Iterator<ParameterRow> insertedRows = dsMessage.getInsertedRows();
		Iterator<ParameterRow> updatedRows = dsMessage.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = dsMessage.getDeletedRows();
		
		while(deletedRows.hasNext()){
			tstGridDevMapper.deleteCmnTmpRegFee(deletedRows.next().toMap());
		}
		
		while(insertedRows.hasNext()){
			tstGridDevMapper.insertCmnTmpRegFee(insertedRows.next().toMap());
		}
		
		while(updatedRows.hasNext()){
			tstGridDevMapper.updateCmnTmpRegFee(updatedRows.next().toMap());
		}
	}
	
	
}
