package kps.ui.listener;

import kps.data.Node;
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
	public void onPackageFormSubmitted(Day day, Node from, Node to, double weight, double volume, Priority priority);

}
