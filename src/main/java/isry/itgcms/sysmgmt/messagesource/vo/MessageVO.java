package isry.itgcms.sysmgmt.messagesource.vo;

public class MessageVO {

	private String value;
	private String mssageId;
	private String mssageKlangCn;
	private String mssageEnlCn;
	private String mssageChnlngCn;
	private String mssageJplngCn;
	private String useYn;
	private String frstRgtrId;
	private String frstRegDt;
	private String lastMdfrId;
	private String lastMdfcnDt;
	
	public String getMssageId() {
		return mssageId;
	}
	public void setMssageId(String mssageId) {
		this.mssageId = mssageId;
	}
	public String getMssageKlangCn() {
		return mssageKlangCn;
	}
	public void setMssageKlangCn(String mssageKlangCn) {
		this.mssageKlangCn = mssageKlangCn;
	}
	public String getMssageEnlCn() {
		return mssageEnlCn;
	}
	public void setMssageEnlCn(String mssageEnlCn) {
		this.mssageEnlCn = mssageEnlCn;
	}
	public String getMssageChnlngCn() {
		return mssageChnlngCn;
	}
	public void setMssageChnlngCn(String mssageChnlngCn) {
		this.mssageChnlngCn = mssageChnlngCn;
	}
	public String getMssageJplngCn() {
		return mssageJplngCn;
	}
	public void setMssageJplngCn(String mssageJplngCn) {
		this.mssageJplngCn = mssageJplngCn;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getId() {
		return getMssageId();
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public String getFrstRgtrId() {
		return frstRgtrId;
	}
	public void setFrstRgtrId(String frstRgtrId) {
		this.frstRgtrId = frstRgtrId;
	}
	public String getFrstRegDt() {
		return frstRegDt;
	}
	public void setFrstRegDt(String frstRegDt) {
		this.frstRegDt = frstRegDt;
	}
	public String getLastMdfrId() {
		return lastMdfrId;
	}
	public void setLastMdfrId(String lastMdfrId) {
		this.lastMdfrId = lastMdfrId;
	}
	public String getLastMdfcnDt() {
		return lastMdfcnDt;
	}
	public void setLastMdfcnDt(String lastMdfcnDt) {
		this.lastMdfcnDt = lastMdfcnDt;
	}
}
