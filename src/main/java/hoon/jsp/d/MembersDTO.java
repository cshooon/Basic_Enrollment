package hoon.jsp.d;

import java.sql.Timestamp;

public class MembersDTO {
	private String id;
	private String pw;
	private String name;
	private int credit = 0;

	public MembersDTO() {}
	public MembersDTO(String id, String pw, String name) {
		super();
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.credit = 0;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCredit() { return credit;	}

	public void setCredit(int credit) {
		this.credit = credit;
	}
	
}
