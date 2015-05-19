package kps.ui.listener;

public interface AuthDetailsListener extends FormSubmitListener{

	/**
	 * Executed when authentication details are recieved.
	 * @param un
	 * @param pw
	 */
	public void onReceivedAuthDetails(String un, String pw);
}
