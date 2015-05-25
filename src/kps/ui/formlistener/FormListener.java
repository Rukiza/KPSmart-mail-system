package kps.ui.formlistener;

/**
 * @author will
 * Listens on forms, notifies if a form has been cancelled
 */
public interface FormListener {

	/**
	 * The form has been cancelled
	 */
	public void onCancel();

}
