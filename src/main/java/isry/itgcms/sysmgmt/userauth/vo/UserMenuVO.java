package isry.itgcms.sysmgmt.userauth.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserMenuVO {
	@JsonProperty("MENU_NO")
	private Integer menuNo;				/* 메뉴번호 */
	@JsonProperty("MENU_NM")
	private String menuNm;             /* 메뉴명 */
	@JsonProperty("UP_MENU_NO")
	private String upMenuNo;           /* 부모 메뉴번호 */
	@JsonProperty("MENU_LEVELA_NO")
	private String menuLevelaNo;       /* 메뉴 레벨번호 */
	@JsonProperty("PROGRM_NM")
	private String progrmNm;           /* 프로그램명 */
	@JsonProperty("OUTSD_PROGRM_YN")
	private String outsdProgrmYn;      /* 외부프로그램여부 */
	@JsonProperty("URL_ADDR")
	private String urlAddr;            /* 메뉴URL */
	@JsonProperty("PARA_CN")
	private String paraCn;             /* 파라미터내용 */ 
	@JsonProperty("INQ_BUTTON_USE_YN")
	private String inqButtonUseYn;     /* 조회버튼사용여부 */
	@JsonProperty("DTL_INQ_BUTTON_USE_YN")
	private String dtlInqButtonUseYn;  /* 상세조회버튼사용여부 */
	@JsonProperty("REG_BUTTON_USE_YN")
	private String regButtonUseYn;     /* 등록버튼사용여부 */
	@JsonProperty("MDFCN_BUTTON_USE_YN")
	private String mdfcnButtonUseYn;   /* 수정버튼사용여부 */
	@JsonProperty("DEL_BUTTON_USE_YN")
	private String delButtonUseYn;     /* 삭제버튼사용여부 */
	
}
