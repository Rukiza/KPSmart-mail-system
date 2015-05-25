package kps.ui.listener;

import kps.enums.Day;
import kps.enums.Priority;

public interface PackageFormListener extends FormSubmitListener{

	/**
	 * Executed when a package form has been submitted
	 * @param day
	 * @param from
	 * @param to TODO
	 * @param weight
	 * @param volume
	 * @param priority
	 */
	public void onPackageFormSubmitted(Day day, String from, String to, int weight, int volume, Priority priority);
	
	/**
	 * Executed when the form has been completed and whenever the completed for has been updated
	 * @param day
	 * @param from
	 * @param to
	 * @param weight
	 * @param volume
	 * @param priority
	 */
	public void onCompletedFormUpdate(Day day, String from, String to, int weight, int volume, Priority priority);
}
