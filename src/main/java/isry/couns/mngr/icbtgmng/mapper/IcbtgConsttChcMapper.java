package isry.couns.mngr.icbtgmng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("icbtgConsttChcMapper")
public interface IcbtgConsttChcMapper{
	
	List<Map<String, Object>> selectIcbtgConsttChcList(Map<String, Object> mapParam) throws Exception;
	
	int insertIcbtgConsttChc(Map<String, Object> mapParam) throws Exception;
	
}
