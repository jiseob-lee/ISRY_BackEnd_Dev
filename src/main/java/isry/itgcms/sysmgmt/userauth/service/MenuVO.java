/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부 및 위기 청소년 통합 지원 시스템 관리팀의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/

package isry.itgcms.sysmgmt.userauth.service;

public class MenuVO {
	private int level;
	private String menuId;
	private String menuNm;
	private String callPage;
	private String upMenuId;
	private String icon;
	private int menuLvl;
	private String desc;
	private String useYn;
	private String topMenuId;
	private String rightView;
	private String rightInit;
	private String rightCreate;
	private String rightUpdate;
	private String rightDelete;
	private String rightManage;
	private String rightExists;
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getMenuNm() {
		return menuNm;
	}
	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}
	public String getCallPage() {
		return callPage;
	}
	public void setCallPage(String callPage) {
		this.callPage = callPage;
	}
	public String getUpMenuId() {
		return upMenuId;
	}
	public void setUpMenuId(String upMenuId) {
		this.upMenuId = upMenuId;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public int getMenuLvl() {
		return menuLvl;
	}
	public void setMenuLvl(int menuLvl) {
		this.menuLvl = menuLvl;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getTopMenuId() {
		return topMenuId;
	}
	public void setTopMenuId(String topMenuId) {
		this.topMenuId = topMenuId;
	}
	public String getRightView() {
		return rightView;
	}
	public void setRightView(String rightView) {
		this.rightView = rightView;
	}
	public String getRightInit() {
		return rightInit;
	}
	public void setRightInit(String rightInit) {
		this.rightInit = rightInit;
	}
	public String getRightCreate() {
		return rightCreate;
	}
	public void setRightCreate(String rightCreate) {
		this.rightCreate = rightCreate;
	}
	public String getRightUpdate() {
		return rightUpdate;
	}
	public void setRightUpdate(String rightUpdate) {
		this.rightUpdate = rightUpdate;
	}
	public String getRightDelete() {
		return rightDelete;
	}
	public void setRightDelete(String rightDelete) {
		this.rightDelete = rightDelete;
	}
	public String getRightManage() {
		return rightManage;
	}
	public void setRightManage(String rightManage) {
		this.rightManage = rightManage;
	}
	public String getRightExists() {
		return rightExists;
	}
	public void setRightExists(String rightExists) {
		this.rightExists = rightExists;
	}
}
