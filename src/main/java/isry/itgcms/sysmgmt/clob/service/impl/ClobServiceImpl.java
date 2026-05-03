package isry.itgcms.sysmgmt.clob.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;

import isry.itgcms.sysmgmt.clob.mapper.ClobMapper;
import isry.itgcms.sysmgmt.clob.service.ClobService;


@Service("clobService")
public class ClobServiceImpl implements ClobService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name="clobMapper")
    private ClobMapper clobMapper;

	public List<Map<String, Object>> selectClob(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		log.debug("test");
		Map<String, Object> map  = new HashMap<>();
		return clobMapper.selectClob(map);
	}
}
