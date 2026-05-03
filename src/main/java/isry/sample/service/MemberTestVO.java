package isry.sample.service;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MemberTestVO {

	@NotNull(message="아이디를 입력해 주세요.")
	@Size(min=5, max=30, message="아이디는 5자 이상, 30자 이하로 입력해주세요.")
	private String id;
	
    @NotNull(message="패스워드를 입력해 주세요.")
	@Size(min=5, max=30, message="패스워드는 5자 이상, 30자 이하로 입력해주세요.")
	private String pw;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

}