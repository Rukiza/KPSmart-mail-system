package kps.ui;

public interface AuthDetailsListener {

	public void onReceivedAuthDetails(String un, String pw);
	public void onReceivedCancel();

}
