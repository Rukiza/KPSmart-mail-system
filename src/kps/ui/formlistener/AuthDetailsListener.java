package kps.ui.formlistener;

public interface AuthDetailsListener extends FormListener{

	/**
	 * Executed when authentication details are received.
	 * @param un
	 * @param pw
	 * @return whether authentication was successful
	 */
	public boolean onReceivedAuthDetails(String un, String pw);
}
