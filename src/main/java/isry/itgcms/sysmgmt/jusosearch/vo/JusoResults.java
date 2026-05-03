package isry.itgcms.sysmgmt.jusosearch.vo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JusoResults {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private Results results;
	
	public Results getResults() {
		return results;
	}
	
	public void Print() {
		log.debug("totalCount = " + results.common.totalCount);
		log.debug("currentPage = " + results.common.currentPage);
		log.debug("countPerPage = " + results.common.countPerPage);
		log.debug("errorCode = " + results.common.errorCode);
		log.debug("errorMessage = " + results.common.errorMessage);
		if (results != null && results.juso != null) {
			for (int i=0; i < results.juso.size(); i++) {
				log.debug(String.valueOf(i));
				log.debug("roadAddr = " + results.juso.get(i).roadAddr);
				log.debug("roadAddrPart1 = " + results.juso.get(i).roadAddrPart1);
				log.debug("roadAddrPart2 = " + results.juso.get(i).roadAddrPart2);
				log.debug("jibunAddr = " + results.juso.get(i).jibunAddr);
				log.debug("engAddr = " + results.juso.get(i).engAddr);
				log.debug("zipNo = " + results.juso.get(i).zipNo);
				log.debug("admCd = " + results.juso.get(i).admCd);
				log.debug("rnMgtSn = " + results.juso.get(i).rnMgtSn);
				log.debug("bdMgtSn = " + results.juso.get(i).bdMgtSn);
				log.debug("detBdNmList = " + results.juso.get(i).detBdNmList);
				log.debug("bdNm = " + results.juso.get(i).bdNm);
				log.debug("bdKdcd = " + results.juso.get(i).bdKdcd);
				log.debug("siNm = " + results.juso.get(i).siNm);
				log.debug("sggNm = " + results.juso.get(i).sggNm);
				log.debug("emdNm = " + results.juso.get(i).emdNm);
				log.debug("liNm = " + results.juso.get(i).liNm);
				log.debug("rn = " + results.juso.get(i).rn);
				log.debug("udrtYn = " + results.juso.get(i).udrtYn);
				log.debug("buldMnnm = " + results.juso.get(i).buldMnnm);
				log.debug("buldSlno = " + results.juso.get(i).buldSlno);
				log.debug("mtYn = " + results.juso.get(i).mtYn);
				log.debug("lnbrMnnm = " + results.juso.get(i).lnbrMnnm);
				log.debug("lnbrSlno = " + results.juso.get(i).lnbrSlno);
				log.debug("emdNo = " + results.juso.get(i).emdNo);
			}
		}
	}
	
	public class Results {
		private Common common;
		private List<Juso> juso;
		
		public Common getCommon() {
			return common;
		}
		public List<Juso> getJuso() {
			//return juso;
			return juso == null ? null : new ArrayList<Juso>(juso);
		}
	}
	
	public class Common {
		private String totalCount;
		private String currentPage;
		private String countPerPage;
		private String errorCode;
		private String errorMessage;
		

		public String getTotalCount() {
			return totalCount;
		}
		public String getCurrentPage() {
			return currentPage;
		}
		public String getCountPerPage() {
			return countPerPage;
		}
		public String getErrorCode() {
			return errorCode;
		}
		public String getErrorMessage() {
			return errorMessage;
		}
	}
	
	public class Juso {
		private String roadAddr;
		private String roadAddrPart1;
		private String roadAddrPart2;
		private String jibunAddr;
		private String engAddr;
		private String zipNo;
		private String admCd;
		private String rnMgtSn;
		private String bdMgtSn;
		private String detBdNmList;
		private String bdNm;
		private String bdKdcd;
		private String siNm;
		private String sggNm;
		private String emdNm;
		private String liNm;
		private String rn;
		private String udrtYn;
		private String buldMnnm;
		private String buldSlno;
		private String mtYn;
		private String lnbrMnnm;
		private String lnbrSlno;
		private String emdNo;
		
		

		public String getRoadAddr() {
			return roadAddr;
		}
		public String getRoadAddrPart1() {
			return roadAddrPart1;
		}
		public String getRoadAddrPart2() {
			return roadAddrPart2;
		}
		public String getJibunAddr() {
			return jibunAddr;
		}
		public String getEngAddr() {
			return engAddr;
		}
		public String getZipNo() {
			return zipNo;
		}
		public String getAdmCd() {
			return admCd;
		}
		public String getRnMgtSn() {
			return rnMgtSn;
		}
		public String getBdMgtSn() {
			return bdMgtSn;
		}
		public String getDetBdNmList() {
			return detBdNmList;
		}
		public String getBdNm() {
			return bdNm;
		}
		public String getBdKdcd() {
			return bdKdcd;
		}
		public String getSiNm() {
			return siNm;
		}
		public String getSggNm() {
			return sggNm;
		}
		public String getEmdNm() {
			return emdNm;
		}
		public String getLiNm() {
			return liNm;
		}
		public String getRn() {
			return rn;
		}
		public String getUdrtYn() {
			return udrtYn;
		}
		public String getBuldMnnm() {
			return buldMnnm;
		}
		public String getBuldSlno() {
			return buldSlno;
		}
		public String getMtYn() {
			return mtYn;
		}
		public String getLnbrMnnm() {
			return lnbrMnnm;
		}
		public String getLnbrSlno() {
			return lnbrSlno;
		}
		public String getEmdNo() {
			return emdNo;
		}
	}
}