package isry.itgcms.sysmgmt.clob.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("clobMapper")
public interface ClobMapper {
	public List<Map<String, Object>> selectClob(Map<String, Object> map) throws Exception;
}
