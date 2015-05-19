package kps.ui.listener;

public interface PackageFormListener extends FormSubmitListener{

	/**
	 * Executed when a package form has been submitted
	 * @param day
	 * @param from
	 * @param weight
	 * @param volume
	 * @param priority
	 */
	public void onPackageFormSubmitted(String day, String from, double weight, double volume, String priority);

}
