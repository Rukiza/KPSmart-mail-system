package kps.ui.form;

public interface AuthDetailsListener extends FormListener{

	/**
	 * Executed when authentication details are recieved.
	 * @param un
	 * @param pw
	 */
	public void onReceivedAuthDetails(String un, String pw);
}
