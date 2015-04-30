package kps.ui;

public class AuthDetails {

	private String un;
	private String pw;

	public AuthDetails(String un, String pw){
		this.un = un;
		this.pw = pw;
	}

	public String getUn() {
		return un;
	}

	public String getPw() {
		return pw;
	}
}
