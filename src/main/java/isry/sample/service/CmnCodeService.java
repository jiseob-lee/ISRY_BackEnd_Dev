 package isry.sample.service;

import java.util.List;
import java.util.Map;

public interface CmnCodeService{	
	public List<Map<String, Object>> selectCmnCodeList(String strCdCls );
	
	
//	public List<Map<String, Object>> selectCmnCodeList(String strCdCls, String strUseYn ) throws Exception {
//		Map<String, Object> mapParam = new HashMap<String, Object>();
//		mapParam.put("CD_CLS", strCdCls);
//		if(strUseYn != null && !"".equals(StringUtil.fixNull(strUseYn))){
//			mapParam.put("USE_YN", "Y");
//		}
//		
//		return selectCmnCodeList(mapParam);
//	}
//	
	
//	public List<Map<String, Object>> selectCmnCodeList(Map<String, Object> mapParam ) throws Exception {
//		return dao.selectList("cmn-base01.selectTstComCdForCombo", mapParam);
//	}
	
}