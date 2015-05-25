package kps.ui.form;

import kps.data.Node;
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
	public void onPackageFormSubmitted(Day day, Node from, Node to, double weight, double volume, Priority priority);
	
	/**
	 * When a route has been chosen by the user
	 * @param from
	 * @param to
	 */
	public void onRouteChosen(Node from, Node to);

}
