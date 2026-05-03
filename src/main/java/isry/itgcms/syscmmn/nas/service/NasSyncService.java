package isry.itgcms.syscmmn.nas.service;

import isry.itgcms.syscmmn.nas.vo.NasSyncResultVO;

public interface NasSyncService {
	public NasSyncResultVO uploadFile(String fileName, String filePath) throws Exception;
	public NasSyncResultVO deleteFile(String fileName, String filePath) throws Exception;
}
