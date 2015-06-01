package kps.ui.formlistener;

import kps.enums.Day;
import kps.enums.Priority;

public interface PackageFormListener extends FormListener{

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
	 * @param priority
	 * @param weight
	 * @param volume
	 */
	public void onCompletedFormUpdate(Day day, String from, String to, Priority priority, int weight, int volume);
}
